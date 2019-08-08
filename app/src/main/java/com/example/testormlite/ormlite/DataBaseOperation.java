package com.example.testormlite.ormlite;

import java.util.List;

public interface DataBaseOperation<T> {

    boolean insertData(T t);
    boolean deleteData(T t);
    List<T> queryAll();
    boolean deleteAll();

}
