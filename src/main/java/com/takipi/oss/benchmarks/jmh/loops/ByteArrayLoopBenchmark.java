package com.takipi.oss.benchmarks.jmh.loops;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(2)
@Measurement(iterations = 5)
@Warmup(iterations = 5)
public class ByteArrayLoopBenchmark {
    int size = 100_000;

    int byteArrLength = 100;

    List<byte[]> arrays;

    @Setup
    public void setup() {
        arrays = new ArrayList<>(size);

        Random random = new Random(123_456_789);
        for (int i = 0; i < size; i++) {
            byte[] arr = new byte[byteArrLength];
            random.nextBytes(arr);
            arrays.add(arr);
        }
    }

    @Benchmark
    public int iteratorMax() {
        int max = Integer.MIN_VALUE;
        for (Iterator<byte[]> it = arrays.iterator(); it.hasNext(); ) {
            max = Integer.max(max, Arrays.hashCode(it.next()));
        }
        return max;
    }

    @Benchmark
    public int forEachLoopMax() {
        int max = Integer.MIN_VALUE;
        for (byte[] n : arrays) {
            max = Integer.max(max, Arrays.hashCode(n));
        }
        return max;
    }

    @Benchmark
    public int forEachLambdaMax() {
        MaxHolder maxHolder = new MaxHolder();
        arrays.forEach(maxHolder::update);
        return maxHolder.max;
    }

    @Benchmark
    public int forMax() {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arrays.size(); i++) {
            max = Integer.max(max, Arrays.hashCode(arrays.get(i)));
        }
        return max;
    }

    @Benchmark
    public int parallelStreamMax() {
        return arrays.parallelStream().mapToInt(Arrays::hashCode).max().getAsInt();
    }

    @Benchmark
    public int streamReduceMax() {
        return arrays.stream().mapToInt(Arrays::hashCode).reduce(Integer::max).getAsInt();
    }

    @Benchmark
    public int streamReduceMaxWithInitial() {
        return arrays.stream().mapToInt(Arrays::hashCode).reduce(Integer.MIN_VALUE, Integer::max);
    }

    @Benchmark
    public int streamMax() {
        return arrays.stream().mapToInt(Arrays::hashCode).max().getAsInt();
    }

    public static class MaxHolder {
        int max = Integer.MIN_VALUE;

        void update(byte[] bytes) {
            max = Math.max(Arrays.hashCode(bytes), max);
        }
    }
}
