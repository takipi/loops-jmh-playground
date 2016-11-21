package com.takipi.oss.benchmarks.jmh.loops;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.max;

@State(Scope.Benchmark)
public class LoopBenchmarkMain {
    private static final int size = 10000000;
    private static final int[] array = new int[size];
    private static final List<Integer> integers = setup();
    private static final Wrapper wrapper = new Wrapper();


    public static void main(String[] args) {
        LoopBenchmarkMain benchmark = new LoopBenchmarkMain();
        benchmark.setup();

    }

    public static List<Integer> setup() {
        ArrayList<Integer> list = new ArrayList<>(size);
        populate(list);
        return list;
    }

    public static void populate(List<Integer> list) {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int value = random.nextInt(MAX_VALUE);
            list.add(value);
            array[i] = value;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int iteratorMaxInteger() {
        int max = MIN_VALUE;
        for (Iterator<Integer> it = integers.iterator(); it.hasNext(); ) {
            max = max(max, it.next().intValue());
        }
        return max;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int forEachLoopMaxInteger() {
        int max = MIN_VALUE;
        for (Integer n : integers) {
            max = max(max, n.intValue());
        }
        return max;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int forEachArrayLoopMaxInteger() {
        int max = MIN_VALUE;
        for (int n : array) {
            max = max(max, n);
        }
        return max;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int collectionsMaxInteger() {
        final int[] max = {MIN_VALUE};
        integers.forEach((i) -> {
            int b = max[0];
            max[0] = Math.max(i.intValue(), b);
        });
        return max[0];
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int collectionsMax2Integer() {
        wrapper.inner = MIN_VALUE;
        integers.forEach((i) -> wrapper.inner = Math.max(i.intValue(), wrapper.inner = MIN_VALUE));
        return wrapper.inner;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int forEachLambdaMaxInteger() {
        wrapper.inner = MIN_VALUE;
        integers.forEach(i -> wrapper.inner = max(i.intValue(), wrapper.inner));
        return wrapper.inner;
    }

    private static class Wrapper {
        int inner;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int forMaxInteger() {
        int max = MIN_VALUE;
        for (int i = 0; i < size; i++) {
            max = max(max, integers.get(i).intValue());
        }
        return max;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int forMax2Integer() {
        int max = MIN_VALUE;
        for (int i = 0; i < size; i++) {
            max = max(max, integers.get(i).intValue());
        }
        return max;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int parallelStreamMaxInteger() {
        return integers.parallelStream().mapToInt(Integer::intValue).reduce(MIN_VALUE, Math::max);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int parallelArrayStreamMaxInteger() {
        return IntStream.of(array).parallel().reduce(MIN_VALUE, Math::max);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int parallelArrayStreamMax2Integer() {
        return IntStream.of(array).parallel().max().getAsInt();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int streamMaxInteger() {
        return integers.stream().mapToInt(Integer::intValue).reduce(MIN_VALUE, Math::max);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)
    public int streamArrayMaxInteger() {
        return IntStream.of(array).reduce(MIN_VALUE, Math::max);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @Fork(2)
    @Measurement(iterations = 5)
    @Warmup(iterations = 5)

    public int streamArrayMax2Integer() {
        return IntStream.of(array).max().getAsInt();
    }


}
