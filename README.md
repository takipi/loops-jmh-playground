# loops-jmh-playground

Blog post about JMH

# Run complete. Total time: 00:03:19
Original from fixed
```
Benchmark                                   Mode  Cnt   Score   Error  Units
LoopBenchmarkMain.forEachLambdaMaxInteger   avgt   10  21.741 ± 0.290  ms/op
LoopBenchmarkMain.forEachLoopMaxInteger     avgt   10  15.762 ± 1.178  ms/op
LoopBenchmarkMain.forMax2Integer            avgt   10  16.613 ± 1.302  ms/op
LoopBenchmarkMain.forMaxInteger             avgt   10  16.471 ± 0.947  ms/op
LoopBenchmarkMain.iteratorMaxInteger        avgt   10  15.761 ± 1.500  ms/op
LoopBenchmarkMain.lambdaMaxInteger          avgt   10  15.717 ± 0.522  ms/op
LoopBenchmarkMain.parallelStreamMaxInteger  avgt   10  10.439 ± 4.901  ms/op
LoopBenchmarkMain.streamMaxInteger          avgt   10  15.860 ± 1.194  ms/op
```

Changes:
Added IntStream(array)
Added iterator.foreach
removed the Wrapper.inner new
increased the loop size to 100000
Used Math.max instead of Integer.max
``` 
Benchmark                                         Mode  Cnt   Score   Error  Units
LoopBenchmarkMain.collectionsMax2Integer          avgt   10  13.801 ± 0.868  ms/op
LoopBenchmarkMain.collectionsMaxInteger           avgt   10  22.274 ± 1.209  ms/op
LoopBenchmarkMain.forEachArrayLoopMaxInteger      avgt   10   8.994 ± 0.206  ms/op
LoopBenchmarkMain.forEachLambdaMaxInteger         avgt   10  15.378 ± 0.716  ms/op
LoopBenchmarkMain.forEachLoopMaxInteger           avgt   10  15.519 ± 1.327  ms/op
LoopBenchmarkMain.forMax2Integer                  avgt   10  16.668 ± 0.723  ms/op
LoopBenchmarkMain.forMaxInteger                   avgt   10  19.364 ± 2.275  ms/op
LoopBenchmarkMain.iteratorMaxInteger              avgt   10  16.693 ± 1.330  ms/op
LoopBenchmarkMain.parallelArrayStreamMax2Integer  avgt   10   2.301 ± 0.197  ms/op
LoopBenchmarkMain.parallelArrayStreamMaxInteger   avgt   10   1.983 ± 0.402  ms/op
LoopBenchmarkMain.parallelStreamMaxInteger        avgt   10   7.731 ± 0.284  ms/op
LoopBenchmarkMain.streamArrayMax2Integer          avgt   10   9.311 ± 0.529  ms/op
LoopBenchmarkMain.streamArrayMaxInteger           avgt   10   9.057 ± 0.325  ms/op
LoopBenchmarkMain.streamMaxInteger                avgt   10  15.897 ± 1.193  ms/op
```