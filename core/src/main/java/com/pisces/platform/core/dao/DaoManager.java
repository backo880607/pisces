package com.pisces.platform.core.dao;

import com.pisces.platform.core.dao.impl.DaoImpl;
import com.pisces.platform.core.entity.EntityObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class DaoManager {
    private static Map<String, UserData> userDatas = new ConcurrentHashMap<>();
    private static List<BaseDao<? extends EntityObject>> daoes = new ArrayList<>();

    protected DaoManager() {}

    public static void register(BaseDao<? extends EntityObject> dao) {
        daoes.add(dao);
    }

    public static void sync() {
        for (BaseDao<? extends EntityObject> dao : daoes) {
            dao.sync();
        }
    }

    private static UserData getUserDatas(String username) {
        UserData userData = userDatas.get(username);
        if (userData == null) {
            userData = new UserData();
            userDatas.put(username, userData);
        }
        return userData;
    }

    public static void login(String username) {
        UserData userData = getUserDatas(username);
        if (userData.daoImpls.isEmpty()) {
            for (BaseDao<? extends EntityObject> dao : daoes) {
                userData.daoImpls.put(dao, dao.createDaoImpl());
            }
        }
    }

    public static void logout(String username) {
        userDatas.remove(username);
    }

    public static boolean switchUser(String username) {
        UserData userData = userDatas.get(username);
        if (userData == null) {
            return false;
        }

        for (BaseDao<? extends EntityObject> dao : daoes) {
            DaoImpl impl = userData.daoImpls.get(dao);
            dao.switchDaoImpl(impl);
        }
        if (!userData.bInit) {
            synchronized (DaoManager.class) {
                if (!userData.bInit) {
                    for (Entry<BaseDao<? extends EntityObject>, DaoImpl> entry : userData.daoImpls.entrySet()) {
                        entry.getKey().loadData();
                    }
                }
                userData.bInit = true;
            }
        }
        return true;
    }

    private static class UserData {
        boolean bInit = false;
        Map<BaseDao<? extends EntityObject>, DaoImpl> daoImpls = new HashMap<>();
    }
}
