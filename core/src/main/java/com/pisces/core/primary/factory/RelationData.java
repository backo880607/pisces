package com.pisces.core.primary.factory;

import com.pisces.core.relation.Type;
import com.pisces.core.relation.RelationKind;
import com.pisces.core.relation.Sign;

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
	
	public void setOwner(boolean owner) {
		this.owner = owner;
	}
	
	public Sign getSign() {
		return sign;
	}
	
	public void setSign(Sign sign) {
		this.sign = sign;
	}
	
	public EntityFactory getFactory() {
		return factory;
	}
	
	public void setFactory(EntityFactory factory) {
		this.factory = factory;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Sign getReverse() {
		return reverse;
	}
	
	public void setReverse(Sign reverse) {
		this.reverse = reverse;
	}
	
	public RelationKind getKind() {
		return kind;
	}
	
	public void setKind(RelationKind kind) {
		this.kind = kind;
	}
}
