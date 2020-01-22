package com.pisces.platform.rds.provider;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.rds.common.SQLDao;
import com.pisces.platform.rds.config.RdsMessage;
import com.pisces.platform.rds.exception.RdsException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Transmit {
    private static class Data<T extends EntityObject> {
        public SQLDao<T> mapper;
        public Collection<Long> deleteds;
        public ArrayList<T> creates = new ArrayList<>();
        public ArrayList<T> updates = new ArrayList<>();
    }
    private final static int QUEUE_SIZE = 50;
    private final static int PUT_TIMEOUT = 3;
    private final static int SQL_TIMEOUT = 1000;
    private final static int SQL_REDELIVERY_COUNT = 6;
    private final static int SQL_REDELIVERY_FACTORY = 2;
    private BlockingQueue<Data<? extends EntityObject>> datas = new LinkedBlockingQueue<>(QUEUE_SIZE);
    private Thread consumer = null;

    public <T extends EntityObject> boolean add(SQLDao<? extends EntityObject> mapper, Map<Long, T> records, Collection<Long> deleteds) {
        Data data = new Data<>();
        data.mapper = mapper;
        data.deleteds = deleteds;
        for (Map.Entry<Long, T> entry : records.entrySet()) {
            T record = entry.getValue();
            if (record.getCreated()) {
                data.creates.add(record);
            } else if (record.getModified()) {
                data.updates.add(record);
            }
        }
        if (data.deleteds.isEmpty() && data.creates.isEmpty() && data.updates.isEmpty()) {
            return false;
        }

        try {
            if (!datas.offer(data, PUT_TIMEOUT, TimeUnit.SECONDS)) {
                throw new RdsException(RdsMessage.BUFFER_FULL);
            }
        } catch (InterruptedException e) {
        }

        return true;
    }

    public void start() {
        if (consumer == null) {
            synchronized (getClass()) {
                if (consumer == null) {
                    consumer = new Thread(() -> {
                        while (true) {
                            try {
                                Data data = datas.take();

                                // 入库失败，则按设置的时间间隔进行重试。
                                int timeout = SQL_TIMEOUT;
                                int count = 0;
                                while (count < SQL_REDELIVERY_COUNT && !syncSQLImpl(data)) {
                                    Thread.sleep(timeout);
                                    timeout *= SQL_REDELIVERY_FACTORY;
                                    ++count;
                                }

                                if (count >= SQL_REDELIVERY_COUNT) {
                                    // proSyncSQLFailed();
                                    break;
                                }
                            } catch (InterruptedException e) {
                                break;
                            }
                        }
                    });

                    consumer.start();
                }
            }
        }
    }

    public void stop() {
        if (consumer != null) {
            synchronized (getClass()) {
                if (consumer != null) {
                    while (consumer.isAlive()) {
                        if (datas.size() <= 0) {
                            consumer.interrupt();
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            consumer = null;
                            break;
                        }

                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private boolean syncSQLImpl(Data data) {
        if (data == null) {
            return true;
        }

        if (!data.deleteds.isEmpty()) {
            data.mapper.deleteList(data.deleteds);
        }
        if (data.creates != null && !data.creates.isEmpty()) {
            data.mapper.insertList(data.creates);
        }

        if (data.updates != null && !data.updates.isEmpty()) {
            data.mapper.updateList(data.updates);
        }

        //sqlError.set(false);
        return true;
    }

    public static Transmit instance = new Transmit();
}
