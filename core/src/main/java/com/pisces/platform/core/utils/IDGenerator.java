package com.pisces.platform.core.utils;

public final class IDGenerator {

    public static final IDGenerator instance = new IDGenerator();
    static SnowflakeIdGen snowflakeIdGen = new SnowflakeIdGen(1, 0);

    private IDGenerator() {
    }

    public long getID() {
        return snowflakeIdGen.nextId();
    }
}
