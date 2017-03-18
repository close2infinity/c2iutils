package eu.close2infinity.util.lang;

import java.util.Optional;

public interface Either<L, R> {

    Optional<L> left();

    Optional<R> right();

    static <L, R> Either<L, R> ofLeft(L left) {
        return new Left<>(left);
    }

    static <L, R> Either<L, R> ofRight(R right) {
        return new Right<>(right);
    }

    class Left<L, R> implements Either<L, R> {
        private final L left;

        private Left(L left) {
            this.left = left;
        }

        @Override
        public Optional<L> left() {
            return Optional.of(left);
        }

        @Override
        public Optional<R> right() {
            return Optional.empty();
        }
    }

     class Right<L, R> implements Either<L, R> {
        private final R right;

        private Right(R left) {
            this.right = left;
        }

        @Override
        public Optional<L> left() {
            return Optional.empty();
        }

        @Override
        public Optional<R> right() {
            return Optional.of(right);
        }
    }
}
