package com.xuechuan.xcedu.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(initInflateView(), container, false);
        initCreateView(savedInstanceState);
        return  inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewCreate(view, savedInstanceState);
    }
    protected abstract int initInflateView();
    protected abstract void initCreateView(Bundle savedInstanceState);
    protected abstract void initViewCreate(View view, Bundle savedInstanceState);

}
