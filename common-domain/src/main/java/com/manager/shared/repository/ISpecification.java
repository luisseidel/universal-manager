package com.manager.shared.repository;

public interface ISpecification<T> {
    boolean isSatisfiedBy(T obj);

    default ISpecification<T> and(ISpecification<T> other) {
        return item -> isSatisfiedBy(item) && other.isSatisfiedBy(item);
    }

    default ISpecification<T> or(ISpecification<T> other) {
        return item -> isSatisfiedBy(item) || other.isSatisfiedBy(item);
    }
}
