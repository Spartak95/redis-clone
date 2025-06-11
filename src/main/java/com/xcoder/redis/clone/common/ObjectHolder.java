package com.xcoder.redis.clone.common;

import com.xcoder.redis.clone.io.Reader;
import com.xcoder.redis.clone.io.Writer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ObjectHolder {
    private static volatile ObjectHolder INSTANCE;

    private final Map<Class<?>, Object> instances;

    private ObjectHolder() {
        Writer writer = new Writer();
        this.instances = new HashMap<>();
        instances.put(Writer.class, writer);
        instances.put(Reader.class, new Reader());
    }

    public static ObjectHolder getInstance() {
        if (INSTANCE == null) {
            synchronized (ObjectHolder.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ObjectHolder();
                }
            }
        }

        return INSTANCE;
    }

    @SuppressWarnings("unchecked")
    public <T> T getObject(Class<T> type) {
        Object object = instances.get(Objects.requireNonNull(type));

        if (object == null) {
            throw new IllegalArgumentException("No object found for type " + type.getSimpleName());
        }

        return (T) object;
    }
}
