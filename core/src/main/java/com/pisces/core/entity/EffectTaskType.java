package com.pisces.core.entity;

public class EffectTaskType extends MultiEnum<EffectTaskType.Impl> {
	public enum Impl {
		None,
		PrevSet,
		Manuf,
		PostSet
	}
	
	public EffectTaskType() {
	}
	
	public EffectTaskType(Impl value) {
		super(value);
	}
}