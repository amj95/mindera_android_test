package com.androidtest.minderatest;

public interface BaseView<T extends BasePresenter> {

    void setPresenter(T presenter);

}
