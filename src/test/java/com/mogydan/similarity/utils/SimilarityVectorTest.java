package com.mogydan.similarity.utils;

import org.junit.Test;

import static java.lang.Double.NaN;
import static junit.framework.TestCase.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

public class SimilarityVectorTest {

    private static final SimilarityVector vector1 = new SimilarityVector(new long[]{1, 0, 3, 4, 0, 5});
    private static final SimilarityVector vector2 = new SimilarityVector(new long[]{1, 2, 3, 4, 5, 6});
    private static final SimilarityVector zeroVector = new SimilarityVector(new long[]{0, 0, 0, 0, 0, 0});

    @Test
    public void get() {
        assertThat(vector1.get(0)).isEqualTo(1);
        assertThat(vector1.get(1)).isEqualTo(0);
        assertThat(vector1.get(2)).isEqualTo(3);
        assertThat(vector1.get(5)).isEqualTo(5);
        assertThat(vector1.get(200)).isEqualTo(0);
        assertThat(vector1.get(-200)).isEqualTo(0);
    }

    @Test
    public void size() {
        assertThat(vector1.size()).isEqualTo(6);
        assertThat(vector2.size()).isEqualTo(6);
        assertThat(zeroVector.size()).isEqualTo(6);
    }

    @Test
    public void multiply() {
        assertThat(vector1.multiply(vector2)).isEqualTo(56);
        assertThat(vector2.multiply(vector1)).isEqualTo(56);
        assertThat(vector1.multiply(zeroVector)).isEqualTo(0);
        assertThat(vector2.multiply(zeroVector)).isEqualTo(0);
        assertThat(zeroVector.multiply(zeroVector)).isEqualTo(0);
    }

    @Test
    public void vectorLength() {
        assertEquals(7.1414, vector1.vectorLength(), 0.0001);
        assertEquals(9.5393, vector2.vectorLength(), 0.0001);
        assertEquals(0, zeroVector.vectorLength(), 0.0001);
    }

    @Test
    public void getCosine() {
        assertEquals(0.82201, vector1.getCosine(vector2), 0.0001);
        assertEquals(0.82201, vector2.getCosine(vector1), 0.0001);
        assertEquals(NaN, zeroVector.getCosine(vector1), 0.0001);
        assertEquals(NaN, vector1.getCosine(zeroVector), 0.0001);
    }

    @Test
    public void stream() {
        assertThat(vector1.stream().toArray()).containsExactly(1L, null, 3L, 4L, null, 5L);
    }

    @Test
    public void isZero() {
        assertThat(vector1.isZero()).isFalse();
        assertThat(vector2.isZero()).isFalse();
        assertThat(zeroVector.isZero()).isTrue();
    }
}