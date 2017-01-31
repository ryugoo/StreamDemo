package com.r384ta.android.streamdemo.viewmodel;

import android.content.Context;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.annimon.stream.LongStream;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.r384ta.android.streamdemo.di.module.ProvideNames;
import com.r384ta.android.streamdemo.dto.BenchmarkDto;
import com.r384ta.android.streamdemo.dto.BenchmarkDtoSpotRepository;
import com.r384ta.android.streamdemo.misc.AndroidDisposable;
import com.r384ta.android.streamdemo.model.Benchmark;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class LwsViewModel extends BaseViewModel {
    private static final String TAG = LwsViewModel.class.getSimpleName();
    private static final int TIMES = 100;

    private final BehaviorSubject<CharSequence> mCollectionProcessing = BehaviorSubject.create();

    @Inject
    @Named(ProvideNames.APPLICATION_CONTEXT)
    Context mContext;

    @Inject
    AndroidDisposable mDisposable;

    public LwsViewModel(Fragment fragment) {
        super(fragment);
        getViewModelComponent().inject(this);

        Optional.of(BenchmarkDtoSpotRepository.getEntity(mContext))
            .flatMap(dao -> Optional.ofNullable(dao.benchmark))
            .map(benchmark -> benchmark.result)
            .filter(text -> !TextUtils.isEmpty(text))
            .ifPresent(mCollectionProcessing::onNext);
    }

    //region Public
    @Override
    public void dispose() {
        mDisposable.dispose();
        super.dispose();
    }

    public void collectionProcessingRequest() {
        mDisposable.add(benchmark()
            .doOnNext(this::saveToCache)
            .map(benchmark -> benchmark.result)
            .subscribeOn(Schedulers.computation())
            .subscribe(mCollectionProcessing::onNext, mCollectionProcessing::onError));
    }

    public Observable<CharSequence> collectionProcessing() {
        return mCollectionProcessing;
    }
    //endregion

    //region Private
    private Observable<Benchmark> benchmark() {
        return Observable.create(emitter -> {
            long begin = SystemClock.elapsedRealtime();
            String log = new StringBuilder("begin\n    ")
                .append(plainProcessor())
                .append(" ms, Plain Java (100 times average)\n    ")
                .append(rx1Processor())
                .append(" ms, RxJava 1 (100 times average)\n    ")
                .append(rx2Processor())
                .append(" ms, RxJava 2 (100 times average)\n    ")
                .append(lwsProcessor())
                .append(" ms, LWS (100 times average)\n")
                .append("end, Total ")
                .append(SystemClock.elapsedRealtime() - begin)
                .append(" ms")
                .toString();

            if (!emitter.isDisposed()) {
                emitter.onNext(new Benchmark(log));
            }
        });
    }

    private long plain() {
        long begin = SystemClock.elapsedRealtime();

        int result = 0;
        for (String str : Benchmark.SOURCE) {
            int num = Integer.parseInt(str);
            if (num % 2 == 0) {
                num = num * 2;
                result += num;
            }
        }

        long end = SystemClock.elapsedRealtime();
        return end - begin;
    }

    private long rx1() {
        long begin = SystemClock.elapsedRealtime();

        rx.Observable.from(Benchmark.SOURCE)
            .map(Integer::parseInt)
            .filter(num -> num % 2 == 0)
            .map(num -> num * 2)
            .reduce((num, num2) -> num + num2)
            .toBlocking()
            .single();

        long end = SystemClock.elapsedRealtime();
        return end - begin;
    }

    private long rx2() {
        long begin = SystemClock.elapsedRealtime();

        Observable.fromIterable(Benchmark.SOURCE)
            .map(Integer::parseInt)
            .filter(num -> num % 2 == 0)
            .map(num -> num * 2)
            .reduce((num, num2) -> num + num2)
            .blockingGet();

        long end = SystemClock.elapsedRealtime();
        return end - begin;
    }

    private long lws() {
        long begin = SystemClock.elapsedRealtime();

        Stream.of(Benchmark.SOURCE)
            .map(Integer::parseInt)
            .filter(num -> num % 2 == 0)
            .map(num -> num * 2)
            .reduce((num, num2) -> num + num2)
            .orElse(0);

        long end = SystemClock.elapsedRealtime();
        return end - begin;
    }

    private double plainProcessor() {
        return Stream.range(0, TIMES)
            .flatMapToLong(i -> LongStream.of(plain()))
            .mapToDouble(num -> num)
            .average()
            .orElse(0);
    }

    private double rx1Processor() {
        return Stream.range(0, TIMES)
            .flatMapToLong(i -> LongStream.of(rx1()))
            .mapToDouble(num -> num)
            .average()
            .orElse(0);
    }

    private double rx2Processor() {
        return Stream.range(0, TIMES)
            .flatMapToLong(i -> LongStream.of(rx2()))
            .mapToDouble(num -> num)
            .average()
            .orElse(0);
    }

    private double lwsProcessor() {
        return Stream.range(0, TIMES)
            .flatMapToLong(i -> LongStream.of(lws()))
            .mapToDouble(num -> num)
            .average()
            .orElse(0);
    }

    private void saveToCache(Benchmark benchmark) {
        BenchmarkDtoSpotRepository.putEntity(mContext, new BenchmarkDto(benchmark));
    }
    //endregion

    public static class PlainBenchmark<T> implements Function<Stream<T>, LongStream> {
        @Override
        public LongStream apply(Stream<T> stream) {
            long begin = SystemClock.elapsedRealtime();

            int result = 0;
            for (String str : Benchmark.SOURCE) {
                int num = Integer.parseInt(str);
                if (num % 2 == 0) {
                    num = num * 2;
                    result += num;
                }
            }

            long end = SystemClock.elapsedRealtime();
            return LongStream.of(end - begin);
        }
    }
}
