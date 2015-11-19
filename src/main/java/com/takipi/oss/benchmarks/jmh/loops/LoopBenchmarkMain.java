package com.takipi.oss.benchmarks.jmh.loops;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class LoopBenchmarkMain {
	volatile int arraySize = 100000;
	volatile List<Integer> values = null;
	
	@Setup
	public void setup() {
		values = new ArrayList<Integer>(arraySize);
		populate(values);
	}

	public void populate(List<Integer> list) {
		Random random = new Random();
		for (int i = 0; i < arraySize; i++) {
			list.add(random.nextInt(1000000));
		}
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	public int iteratorMaxInteger() {
		int max = Integer.MIN_VALUE;
		for (Iterator<Integer> it = values.iterator(); it.hasNext(); ) {
			max = Math.max(max, it.next());
		}
		return max;
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	public int forEachMaxInteger() {
		int max = Integer.MIN_VALUE;
		for (Integer value : values) {
			max = Math.max(max, value);
		}
		return max;
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	public int forMaxInteger() {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < arraySize; i++) {
			max = Math.max(max, values.get(i));
		}
		return max;
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	public int parallelStreamMaxInteger() {
		Optional<Integer> max = values.parallelStream().reduce(Integer::max);
		return max.get();
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	public int streamMaxInteger() {
		Optional<Integer> max = values.stream().reduce(Integer::max);
		return max.get();
	}
}
