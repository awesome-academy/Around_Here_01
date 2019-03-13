package com.trunghoang.aroundhere.ui;

import android.support.annotation.NonNull;

public interface BaseView<T> {
    void setPresenter(@NonNull T presenter);
}
