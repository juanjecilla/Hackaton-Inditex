package com.monoloco.zaraqueue.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.monoloco.zaraqueue.R;
import com.monoloco.zaraqueue.base.BaseFragment;
import com.monoloco.zaraqueue.interfaces.OnNewClientListener;
import com.monoloco.zaraqueue.model.Clothes;
import com.monoloco.zaraqueue.model.QueueUser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by root on 5/10/17.
 */

public class NewClientFragment extends BaseFragment {

    @BindView(R.id.numberofitems) TextView numberOfItems;
    @BindView(R.id.userstatus) TextView userStatus;
    @BindView(R.id.scanclothes) Button scanClothes;
    @BindView(R.id.addtoqueue) Button addToQueue;
    @BindView(R.id.clothesitem01) FrameLayout clothesitems01;
    @BindView(R.id.clothesitem02) FrameLayout clothesitems02;
    @BindView(R.id.eliminateitem1) TextView eliminate1;
    @BindView(R.id.eliminateitem2) TextView eliminate2;

    private QueueUser queueUser;
    private ArrayList<Clothes> clothes;

    public static NewClientFragment newInstance(QueueUser queueUser) {
        NewClientFragment fragment = new NewClientFragment();
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
        View view = inflater.inflate(R.layout.fragment_newclient, container, false);
        ButterKnife.bind(this, view);

        numberOfItems.setGravity(Gravity.CENTER);
        numberOfItems.setText("No hay prendas");

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

    @OnClick(R.id.scanclothes)
    public void scanClothes(){
        if (clothes.size() < 6 ){
            if (clothes.size() == 0){
                clothesitems01.setVisibility(View.VISIBLE);
                numberOfItems.setGravity(Gravity.LEFT);
                numberOfItems.setText("1 prenda");
                clothes.add(new Clothes("a",1));
                return;
            }

            if (clothes.size() == 1){
                clothesitems02.setVisibility(View.VISIBLE);
                numberOfItems.setGravity(Gravity.LEFT);
                numberOfItems.setText("2 prendas");
                clothes.add(new Clothes("b",2));
                return;
            }
        }
    }

    @OnClick(R.id.addtoqueue)
    public void addToQueue(){
        if (queueUser.getValid() == 0){
            queueUser.setValid(1);
            FirebaseDatabase.getInstance().getReference().child("queues").child("QUEUE_1").child(queueUser.getUid()).setValue(queueUser);
            userStatus.setText("El usuario está en la cola");
            addToQueue.setText("Finalizar");
        } else {
            ((OnNewClientListener)getContext()).onNewClientManaged();
        }
    }

    @OnClick(R.id.eliminateitem1)
    public void eliminateItem1(){
        clothesitems01.setVisibility(View.GONE);
        clothes.remove(0);
    }

    @OnClick(R.id.eliminateitem2)
    public void eliminateItem2(){
        clothesitems02.setVisibility(View.GONE);
        if (clothes.size() > 1){
            clothes.remove(1);
        } else {
            clothes.remove(0);
        }
    }
}