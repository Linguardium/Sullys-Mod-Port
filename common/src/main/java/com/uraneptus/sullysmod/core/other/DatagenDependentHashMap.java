package com.uraneptus.sullysmod.core.other;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class DatagenDependentHashMap<K,V> extends HashMap<K,V> {
    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        throw new UnsupportedOperationException("Does not support computing values");
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        throw new UnsupportedOperationException("Does not support computing values");
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        throw new UnsupportedOperationException("Does not support computing values");
    }

    @Override
    public V put(K key, V value) {
        if (isDatagen()) {
            return super.put(key, value);
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (isDatagen()) {
            super.putAll(m);
        }
    }

    @Override
    public V putIfAbsent(K key, V value) {
        if (isDatagen()) {
            return super.putIfAbsent(key, value);
        }
        return null;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        if (isDatagen()) {
            return super.replace(key, oldValue, newValue);
        }
        return false;
    }

    @Override
    public V replace(K key, V value) {
        if (isDatagen()) {
            return super.replace(key, value);
        }
        return null;
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        if (isDatagen()) {
            super.replaceAll(function);
        }
    }

    private static boolean isDatagen() {
        return System.getProperties().containsKey("fabric-api.datagen");
    }
}
