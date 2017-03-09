package com.r384ta.android.streamdemo.type;

import android.support.annotation.NonNull;

import com.annimon.stream.Optional;
import com.annimon.stream.function.Consumer;
import com.annimon.stream.function.Function;

public final class Either<Left, Right> {
    private Optional<Left> mLeft;
    private Optional<Right> mRight;

    private Either(Optional<Left> left, Optional<Right> right) {
        mLeft = left;
        mRight = right;
    }

    public static <Left, Right> Either<Left, Right> left(@NonNull Left left) {
        return new Either<>(Optional.of(left), Optional.empty());
    }

    public static <Left, Right> Either<Left, Right> right(@NonNull Right right) {
        return new Either<>(Optional.empty(), Optional.of(right));
    }

    public <T> Either<T, Right> mapLeft(Function<? super Left, ? extends T> mapper) {
        return new Either<>(mLeft.map(mapper), mRight);
    }

    public <T> Either<Left, T> mapRight(Function<? super Right, ? extends T> mapper) {
        return new Either<>(mLeft, mRight.map(mapper));
    }

    public void apply(Consumer<? super Left> leftConsumer, Consumer<? super Right> rightConsumer) {
        mLeft.ifPresent(leftConsumer);
        mRight.ifPresent(rightConsumer);
    }
}
