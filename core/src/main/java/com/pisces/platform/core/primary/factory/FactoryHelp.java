package com.pisces.platform.core.primary.factory;

import com.pisces.platform.core.annotation.PropertyMeta;
import com.pisces.platform.core.annotation.Relation;
import com.pisces.platform.core.entity.EntityObject;
import com.pisces.platform.core.relation.RelationKind;
import com.pisces.platform.core.relation.RelationMap;
import com.pisces.platform.core.relation.Sign;
import com.pisces.platform.core.relation.Type;
import com.pisces.platform.core.utils.EntityUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class FactoryHelp {

    protected FactoryHelp() {}

    public static void initRelation() {
        Set<Class<? extends EntityObject>> clazzs = EntityUtils.getEntityClasses();
        for (Class<? extends EntityObject> clazz : clazzs) {
            initRelationData(clazz);
        }

        for (Class<? extends EntityObject> clazz : clazzs) {
            initRelation(clazz);
        }
    }

    private static void initRelationData(Class<? extends EntityObject> clazz) {
        EntityFactory factory = FactoryManager.getFactory(clazz);
        if (factory.relations != null) {
            return;
        }
        EntityFactory superFactory = factory.getSuperFactory();
        if (superFactory != null) {
            initRelationData(superFactory.getEntityClass());
        }

        factory.maxSign = superFactory != null ? superFactory.maxSign : 0;
        List<RelationData> datas = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            if (field.getType() != Sign.class) {
                continue;
            }

            Sign sign = new Sign(factory.maxSign++);
            sign.setName(field.getName());

            try {
                modify(field, sign);
            } catch (Exception e) {
                e.printStackTrace();
            }

            RelationData data = new RelationData();
            data.sign = sign;
            PropertyMeta propertyMeta = field.getAnnotation(PropertyMeta.class);
            if (propertyMeta != null) {
                RelationKind kind = propertyMeta.kind();
                if (kind != RelationKind.None) {
                    data.setKind(kind);
                }
            }
            datas.add(data);
            factory.signsName.put(field.getName(), sign);
        }
        factory.relations = new RelationMap<>(factory.maxSign);
        for (RelationData data : datas) {
            factory.relations.put(data.sign, data);
        }
    }

    private static void modify(Field field, Sign sign) throws Exception {
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true); //Field 的 modifiers 是私有的
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, sign);
    }

    private static void initRelation(Class<? extends EntityObject> clazz) {
        EntityFactory factory = FactoryManager.getFactory(clazz);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            if (field.getType() != Sign.class) {
                continue;
            }

            initRelation(factory, field);
        }
    }

    private static void initRelation(EntityFactory factory, Field field) {
        Relation relation = field.getAnnotation(Relation.class);
        if (relation == null) {
            return;
        }

        EntityFactory relaFactory = FactoryManager.getFactory(relation.clazz());
        RelationData data = factory.getSignData(field.getName());
        data.setFactory(relaFactory);
        data.setType(relation.type());
        data.setOwner(relation.owner());
        data.sign.setEntityClass(relaFactory.getEntityClass());
        switch (relation.type()) {
            case OneToOne:
            case MultiToOne:
                data.setKind(RelationKind.Singleton);
                break;
            case OneToMulti:
            case MultiToMulti:
                if (data.getKind() == null || data.getKind() == RelationKind.Singleton) {
                    data.setKind(RelationKind.List);
                }
                break;
        }
        if (!relation.sign().isEmpty()) {
            RelationData relaData = relaFactory.getSignData(relation.sign());
            if (relaData == null) {
                throw new UnsupportedOperationException(relaFactory.getEntityClass().getName() + " has not field " + relation.sign().toString());
            }
            relaData.setFactory(factory);
            relaData.setOwner(false);
            relaData.setReverse(data.sign);
            relaData.sign.setEntityClass(factory.getEntityClass());
            switch (relation.type()) {
                case MultiToMulti:
                    relaData.setType(Type.MultiToMulti);
                    if (relaData.getKind() == null || relaData.getKind() == RelationKind.Singleton) {
                        relaData.setKind(RelationKind.List);
                    }
                    break;
                case MultiToOne:
                    relaData.setType(Type.OneToMulti);
                    if (relaData.getKind() == null || relaData.getKind() == RelationKind.Singleton) {
                        relaData.setKind(RelationKind.List);
                    }
                    break;
                case OneToMulti:
                    relaData.setType(Type.MultiToOne);
                    relaData.setKind(RelationKind.Singleton);
                    break;
                case OneToOne:
                    relaData.setType(Type.OneToOne);
                    relaData.setKind(RelationKind.Singleton);
                    break;
                default:
                    break;
            }

            data.setReverse(relaData.sign);
        }
    }
}
