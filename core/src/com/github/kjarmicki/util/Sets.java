package com.github.kjarmicki.util;

import java.util.ArrayList;
import java.util.List;

public class Sets {
    public static <Type> List<List<Type>> combinations(List<Type> things, int combinationSize) {
        List<List<Type>> result = new ArrayList<>();
        combineUntil(things, result, new ArrayList<>(), 0, things.size() - 1, 0, combinationSize);
        return result;
    }

    private static <Type> void combineUntil(List<Type> things, List<List<Type>> result, List<Type> temp,
                                            int start, int end, int index, int combinationSize) {
        if(index == combinationSize) {
            result.add(new ArrayList<>(temp));
            return;
        }
        for(int i=start; i<=end && end-i+1 >= combinationSize-index; i++) {
            try {
                temp.set(index, things.get(i));
            } catch(IndexOutOfBoundsException ignored) {
                temp.add(index, things.get(i));
            }
            combineUntil(things, result, temp, i + 1, end, index + 1, combinationSize);
        }
    }
}