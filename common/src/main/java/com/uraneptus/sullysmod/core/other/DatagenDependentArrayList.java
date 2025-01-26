package com.uraneptus.sullysmod.core.other;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.UnaryOperator;

public class DatagenDependentArrayList<T> extends ArrayList<T> {
    @Override
    public boolean add(T t) {
        if (isDatagen()) {
            return super.add(t);
        }
        return false;
    }

    @Override
    public void add(int index, T element) {
        if (isDatagen()) {
            super.add(index, element);
        }
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (isDatagen()) {
            return super.addAll(c);
        }
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (isDatagen()) {
            return super.addAll(index, c);
        }
        return false;
    }

    @Override
    public void addFirst(T element) {
        if (isDatagen()) {
            super.addFirst(element);
        }
    }

    @Override
    public void addLast(T element) {
        if (isDatagen()) {
            super.addLast(element);
        }
    }

    public DatagenDependentArrayList(@NotNull Collection<? extends T> c) {
        super(isDatagen()?c: List.of());
    }
    public DatagenDependentArrayList() {
        super();
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        if (isDatagen()) {
            super.replaceAll(operator);
        }
    }

    @Override
    public T set(int index, T element) {
        if (isDatagen()) {
            return super.set(index, element);
        }
        return null;
    }

    private static boolean isDatagen() {
        return System.getProperties().containsKey("fabric-api.datagen");
    }

}
