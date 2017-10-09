package com.monoloco.zaraqueue.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.monoloco.zaraqueue.R;
import com.monoloco.zaraqueue.base.BaseFragment;
import com.monoloco.zaraqueue.interfaces.OnNewClientListener;
import com.monoloco.zaraqueue.interfaces.OnNewConfirmListener;
import com.monoloco.zaraqueue.model.Clothes;
import com.monoloco.zaraqueue.model.QueueUser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by root on 6/10/17.
 */

public class NewConfirmFragment extends BaseFragment {

    @BindView(R.id.numberofitems) TextView numberOfItems;
    @BindView(R.id.userstatus) TextView userStatus;
    @BindView(R.id.scanclothes) Button scanClothes;
    @BindView(R.id.clothesitem01) FrameLayout clothesitems01;
    @BindView(R.id.clothesitem02) FrameLayout clothesitems02;
    @BindView(R.id.eliminateitem1) TextView eliminate1;
    @BindView(R.id.eliminateitem2) TextView eliminate2;
    @BindView(R.id.cabingrid) LinearLayout cabinGrid;

    private QueueUser queueUser;
    private ArrayList<Clothes> clothes;

    public static NewConfirmFragment newInstance(QueueUser queueUser) {
        NewConfirmFragment fragment = new NewConfirmFragment();
        Bundle args = new Bundle();
        args.putParcelable("queueUser", queueUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            queueUser = getArguments().getParcelable("queueUser");
        }
        clothes = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirmclient, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @OnClick(R.id.checkin)
    public void addToQueue() {
        if (cabinGrid.getVisibility() == View.GONE) {
            cabinGrid.setVisibility(View.VISIBLE);
        } else {
            ((OnNewClientListener)getContext()).onNewClientManaged();
            queueUser.setValid(0);
            queueUser.setReady(0);
            FirebaseDatabase.getInstance().getReference().child("queues").child("QUEUE_1").child(queueUser.getUid()).setValue(queueUser);
        }
    }
}