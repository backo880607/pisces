package com.pisces.platform.core.primary.factory;

import com.pisces.platform.core.relation.RelationKind;
import com.pisces.platform.core.relation.Sign;
import com.pisces.platform.core.relation.Type;

class RelationData {
    EntityFactory factory;
    Sign sign;
    Sign reverse;
    Type type;
    RelationKind kind;
    boolean owner;

    public boolean getOwner() {
        return owner;
    }

    public void setOwner(boolean value) {
        this.owner = value;
    }

    public Sign getSign() {
        return sign;
    }

    public void setSign(Sign value) {
        this.sign = value;
    }

    public EntityFactory getFactory() {
        return factory;
    }

    public void setFactory(EntityFactory value) {
        this.factory = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type value) {
        this.type = value;
    }

    public Sign getReverse() {
        return reverse;
    }

    public void setReverse(Sign value) {
        this.reverse = value;
    }

    public RelationKind getKind() {
        return kind;
    }

    public void setKind(RelationKind value) {
        this.kind = value;
    }
}
