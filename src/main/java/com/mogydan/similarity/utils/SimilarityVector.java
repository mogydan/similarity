package com.mogydan.similarity.utils;

import com.google.common.collect.ImmutableMap;
import org.springframework.data.util.Pair;

import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SimilarityVector {

    private final Map<Integer, Long> elements;
    private final int size;

    public SimilarityVector(long[] elements) {
        this.size = elements.length;
        this.elements = prepareMap(elements);
    }

    public SimilarityVector(Map<String, Long> purchaseStatistic, Set<String> commonIds) {
        long[] elements = commonIds.stream()
                .mapToLong(id -> purchaseStatistic.getOrDefault(id, 0L))
                .toArray();
        this.size = elements.length;
        this.elements = prepareMap(elements);
    }

    private Map<Integer, Long> prepareMap(long[] elements) {
        ImmutableMap.Builder<Integer, Long> mapBuilder = ImmutableMap.builder();
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] != 0) {
                mapBuilder.put(i, elements[i]);
            }
        }
        return mapBuilder.build();
    }

    public Long get(int index) {
        return elements.getOrDefault(index, 0L);
    }


    public int size() {
        return size;
    }

    public boolean isZero() {
        return elements.isEmpty();
    }

    public long multiply(SimilarityVector vector) {
        return streamNonZero()
                .mapToLong(p -> p.getSecond() * vector.get(p.getFirst()))
                .sum();
    }

    public double vectorLength() {
        return Math.sqrt(streamNonZero()
                .mapToLong(Pair::getSecond)
                .map(l -> l * l)
                .sum());
    }

    public double getCosine(SimilarityVector vector) {
        return multiply(vector) / vectorLength() / vector.vectorLength();
    }

    public Stream<Long> stream() {
        return IntStream.range(0, size).mapToObj(elements::get);
    }

    private Stream<Pair<Integer, Long>> streamNonZero() {
        return elements.entrySet().stream()
                .map(e -> Pair.of(e.getKey(), e.getValue()));
    }

}
