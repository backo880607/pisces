package com.pisces.platform.core.primary.propagate;

import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.exception.CycleException;
import com.pisces.platform.core.primary.propagate.graph.Graph;
import com.pisces.platform.core.relation.Ioc;
import com.pisces.platform.core.relation.Sign;
import com.pisces.platform.core.service.EntityService;
import com.pisces.platform.core.utils.Primary;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Predicate;

public class Propagate {
    private static int maxId = 0;
    private static Map<Class<? extends EntityObject>, Map<Sign, Data>> cache = new HashMap<>();

    protected Propagate() {}

    /**
     * 构建cache
     *
     * @param clses
     */
    static void init(List<Class<? extends EntityObject>> clses) {
        cache.clear();
        for (Class<? extends EntityObject> cls : clses) {
            cache.put(cls, new HashMap<>());
        }
    }

    static Data getData(Class<? extends EntityObject> clazz, Sign sign) {
        Map<Sign, Data> datas = cache.get(clazz);
        Data result = datas != null ? datas.get(sign) : null;
        if (result == null) {
            Class<? extends EntityObject> superClass = Primary.get().getSuperClass(clazz);
            if (superClass != null) {
                result = Propagate.getData(superClass, sign);
            }
        }

        return result;
    }

    static void setData(Class<? extends EntityObject> cls, Sign sign, Data data) {
        cache.get(cls).put(sign, data);
    }

    /**
     * 深度优先搜索进行循环检测
     *
     * @param entity
     * @param sign
     * @param args
     * @return
     */
    public static boolean dfsCycleCheck(EntityObject entity, Sign sign, Sign... args) {
        Map<Long, Boolean> visited = new HashMap<>();
        return dfsCycleCheckImpl(entity, visited, sign, args);
    }

    private static boolean dfsCycleCheckImpl(EntityObject entity, Map<Long, Boolean> visited, Sign sign, Sign... args) {
        visited.put(entity.getId(), true);

        Collection<EntityObject> relaEntities = Ioc.getList(entity, sign, args);
        for (EntityObject relaEntity : relaEntities) {
            Boolean value = visited.get(relaEntity.getId());
            if (value == null) {
                if (dfsCycleCheckImpl(relaEntity, visited, sign, args)) {
                    return true;
                }
            } else if (value) {
                return true;
            }
        }

        visited.put(entity.getId(), false);
        return false;
    }

    /**
     * 触发传播
     *
     * @param entity
     * @param sign
     */
    public static void invoke(EntityObject entity, Sign sign) {
        invoke(entity, sign, null);
    }

    public static void invoke(EntityObject entity, Sign sign, Predicate<EntityObject> fun) {
        if (entity == null) {
            return;
        }
        final Data data = Propagate.getData(entity.getClass(), sign);
        if (data == null) {
            return;
        }

        List<VertexPropagate> originPts = new LinkedList<>();
        VertexPropagate originPt = new VertexPropagate();
        originPt.entity = entity;
        originPt.data = data;
        originPts.add(originPt);
        Propagate.propagateImpl(originPts, fun);
    }

    public static void invoke(EntityObject entity) {
        Map<Sign, Data> datas = Propagate.cache.get(entity.getClass());
        if (datas == null) {
            return;
        }

        List<VertexPropagate> originPts = new LinkedList<>();
        for (Entry<Sign, Data> entryData : datas.entrySet()) {
            VertexPropagate originPt = new VertexPropagate();
            originPt.entity = entity;
            originPt.data = entryData.getValue();
            originPts.add(originPt);
        }

        Propagate.propagateImpl(originPts, null);
    }

    public static void invoke(Collection<? extends EntityObject> entities) {
        List<VertexPropagate> originPts = new LinkedList<>();
        for (EntityObject entity : entities) {
            Map<Sign, Data> datas = Propagate.cache.get(entity.getClass());
            if (datas == null) {
                continue;
            }

            for (Entry<Sign, Data> entryData : datas.entrySet()) {
                VertexPropagate originPt = new VertexPropagate();
                originPt.entity = entity;
                originPt.data = entryData.getValue();
                originPts.add(originPt);
            }
        }

        Propagate.propagateImpl(originPts, null);
    }

    public static boolean cycleCheck(EntityObject entity) {
        Map<Sign, Data> datas = Propagate.cache.get(entity.getClass());
        if (datas == null) {
            return false;
        }

        List<VertexPropagate> originPts = new LinkedList<>();
        for (Entry<Sign, Data> entryData : datas.entrySet()) {
            VertexPropagate originPt = new VertexPropagate();
            originPt.entity = entity;
            originPt.data = entryData.getValue();
            originPts.add(originPt);
        }

        try {
            Propagate.buildPropagateGraph(originPts, null);
        } catch (CycleException exc) {
            return true;
        }
        return false;
    }

    /**
     * 传播功能实现。
     *
     * @param originPts
     */
    private static void propagateImpl(List<VertexPropagate> originPts, Predicate<EntityObject> fun) {
        Propagate.buildPropagateGraph(originPts, fun);
        Propagate.buildLevel(originPts);

        TreeSet<VertexPropagate> triggers = new TreeSet<>(new Comparator<VertexPropagate>() {
            @Override
            public int compare(VertexPropagate arg0, VertexPropagate arg1) {
                if (arg0.level == arg1.level) {
                    if (arg0.entity == arg1.entity) {
                        return arg0.data.id - arg1.data.id;
                    }
                    return (arg0.entity.getId() < arg1.entity.getId()) ? -1 : 1;
                }
                return arg0.level - arg1.level;
            }
        });
        triggers.addAll(originPts);

        try {
            while (!triggers.isEmpty()) {
                VertexPropagate trigger = triggers.first();
                if (trigger.data != null) {
                    if (trigger.data.method != null && trigger.data.service != null) {
                        final Object oldVal = trigger.data.getMethod != null ? trigger.data.getMethod.invoke(trigger.entity) : null;
                        trigger.data.method.invoke(trigger.data.service, trigger.entity);
                        //MemoryManager.update(trigger.entity);
                        if (oldVal != null && oldVal.equals(trigger.data.getMethod.invoke(trigger.entity))) {
                            triggers.remove(trigger);
                            continue;
                        }
                    }
                    triggers.addAll(trigger.getList());
                }

                triggers.remove(trigger);
            }
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建传播图。
     *
     * @param originPts
     * @param fun
     */
    private static void buildPropagateGraph(List<VertexPropagate> originPts, Predicate<EntityObject> fun) {
        GraphPropagate graph = new GraphPropagate();

        Iterator<VertexPropagate> iter = originPts.iterator();
        while (iter.hasNext()) {
            VertexPropagate trigger = iter.next();
            if (graph.get(trigger) != null) {
                iter.remove();
                continue;
            }

            graph.getVertexNode(trigger, true);
            graph.add(trigger);

            Stack<VertexPropagate> triggerQueue = new Stack<VertexPropagate>();
            triggerQueue.add(trigger);
            try {
                while (!triggerQueue.isEmpty()) {
                    VertexPropagate curTrigger = triggerQueue.peek();
                    List<VertexPropagate> relaVertexes = Propagate.getPropagatePts(curTrigger, fun);
                    if (relaVertexes.isEmpty()) {
                        curTrigger.visited = 1;
                        triggerQueue.pop();
                    } else {
                        triggerQueue.addAll(relaVertexes);
                    }
                }
            } catch (CycleException exc) {
                LinkedList<EntityObject> entities = new LinkedList<>();
                for (int i = triggerQueue.size() - 1; i >= 0; --i) {
                    VertexPropagate temp = triggerQueue.get(i);
                    if (temp.visited != -1) {
                        continue;
                    }

                    entities.addFirst(temp.entity);
                    if (temp.entity == exc.getEntity()) {
                        break;
                    }
                }
                throw exc;
            }
        }
    }

    /**
     * 构建传播点的级别
     *
     * @param originPts
     */
    private static void buildLevel(List<VertexPropagate> originPts) {
        for (VertexPropagate trigger : originPts) {
            Queue<VertexPropagate> triggerQueue = new LinkedList<VertexPropagate>();
            triggerQueue.add(trigger);
            while (!triggerQueue.isEmpty()) {
                VertexPropagate temp = triggerQueue.poll();
                List<VertexPropagate> targets = temp.getList();
                for (VertexPropagate target : targets) {
                    if (target.level <= temp.level) {
                        target.level = temp.level + 1;
                        triggerQueue.add(target);
                    }
                }
            }
        }
    }

    private static List<VertexPropagate> getPropagatePts(VertexPropagate trigger, Predicate<EntityObject> fun) {
        trigger.visited = -1;
        List<VertexPropagate> propagates = new ArrayList<>();
        if (trigger.data == null) {
            return propagates;
        }

        int index = 0;
        for (Sign[] path : trigger.data.paths) {
            Set<EntityObject> entities = new HashSet<>();
            Propagate.getPropagateObjects(entities, trigger.entity, path, 0);

            Propagate.Data targetData = null;
            for (EntityObject entity : entities) {
                if (targetData == null) {
                    targetData = Propagate.getData(entity.getClass(), trigger.data.signs.get(index));
                }

                VertexPropagate target = trigger.get(entity, targetData);
                if (target == null) {
                    target = trigger.create(entity, targetData);
                    target.path = path;
                    propagates.add(target);
                } else if (target.visited == -1) {    // 出现循环
                    target.path = path;
                    CycleException exc = new CycleException("出现循环，类：" + entity.getClass().getName() + " 对象ID" + entity.getId());
                    throw exc;
                } else {
                    Graph.createEdge(trigger, VertexPropagate.HOLDER, target, null);
                }
            }
            ++index;
        }
        return propagates;
    }

    private static void getPropagateObjects(Set<EntityObject> entities, EntityObject entity, Sign[] args, int index) {
        if (index >= args.length) {
            entities.add(entity);
            return;
        }

        Collection<EntityObject> relaEntities = Ioc.getList(entity, args[index]);
        for (EntityObject relaEntity : relaEntities) {
            getPropagateObjects(entities, relaEntity, args, index + 1);
        }
    }

    public static class Data {
        ArrayList<Sign[]> paths = new ArrayList<>();
        ArrayList<Sign> signs = new ArrayList<>();
        EntityService<? extends EntityObject> service = null;
        Method method = null;
        Method getMethod = null;
        final int id = ++maxId;
    }
}
