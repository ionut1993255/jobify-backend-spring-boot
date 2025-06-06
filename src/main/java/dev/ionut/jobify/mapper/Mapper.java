package dev.ionut.jobify.mapper;

public interface Mapper<A, B> {

    B mapTo(A a);

    A mapFrom(B b);
}
