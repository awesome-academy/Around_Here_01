package com.trunghoang.aroundhere.data.db;

public interface DaoHandler<P, T> {
    T execute(P[] args, PlaceDAO placeDao);
}
