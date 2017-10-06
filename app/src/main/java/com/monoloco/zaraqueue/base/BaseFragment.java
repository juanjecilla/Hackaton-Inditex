package com.monoloco.zaraqueue.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.monoloco.zaraqueue.ApplicationClass;

/**
 * Created by root on 5/10/17.
 */

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApplicationClass.injectMember(this);
    }
}
