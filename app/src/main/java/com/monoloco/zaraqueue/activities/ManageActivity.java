package com.monoloco.zaraqueue.activities;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.monoloco.zaraqueue.R;
import com.monoloco.zaraqueue.base.BaseActivity;
import com.monoloco.zaraqueue.fragments.NewClientFragment;
import com.monoloco.zaraqueue.fragments.NewConfirmFragment;
import com.monoloco.zaraqueue.interfaces.OnNewClientListener;
import com.monoloco.zaraqueue.model.QueueUser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManageActivity extends BaseActivity implements ChildEventListener, OnNewClientListener {

    private DatabaseReference databaseReference;
    private ArrayList<QueueUser> pendingList;

    private boolean idle = true;

    private FirebaseUser firebaseUser;

    @BindView(R.id.cabinmanagerbutton) ImageView cabinManagerButton;
    @BindView(R.id.queuemanagerbutton) ImageView queueManagerButton;
    @BindView(R.id.cabinmanager) LinearLayout cabinManager;
    @BindView(R.id.queueManager) ScrollView queueManager;
    @BindView(R.id.queuetime) TextView queueTime;

    @BindView(R.id.timer1) TextView timer1;
    @BindView(R.id.timer2) TextView timer2;
    @BindView(R.id.timer3) TextView timer3;

    private CountDownTimer countDownTimer1 = new CountDownTimer(143*1000, 500) {
        @Override
        public void onTick(long l) {
            long millis = l;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timer1.setText(String.format("%02d:%02d", minutes, seconds));
        }

        @Override
        public void onFinish() {

        }
    };

    private CountDownTimer countDownTimer2 = new CountDownTimer(211*1000, 500) {
        @Override
        public void onTick(long l) {
            long millis = l;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timer2.setText(String.format("%02d:%02d", minutes, seconds));
        }

        @Override
        public void onFinish() {

        }
    };

    private CountDownTimer countDownTimer3 = new CountDownTimer(5*60*1000, 500) {
        @Override
        public void onTick(long l) {
            long millis = l;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timer3.setText(String.format("%02d:%02d", minutes, seconds));
        }

        @Override
        public void onFinish() {

        }
    };
    private long timeCount = 0;
    private ArrayList<QueueUser> auxList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        ButterKnife.bind(this);

        pendingList = new ArrayList<>();
        auxList = new ArrayList<>();

        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            FirebaseAuth.getInstance().signInAnonymously();
        } else {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        }

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.d("AUTH_CHANGE", "changed");
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if (firebaseUser == null){
                    removeListeners();
                } else {
                    initializeQueue();
                }
            }
        });

        countDownTimer1.start();
        countDownTimer2.start();
        countDownTimer3.start();
    }

    private void removeListeners() {
        if (databaseReference != null)
            databaseReference.removeEventListener(this);
    }

    private void initializeQueue(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("queues").child("QUEUE_1");
        databaseReference.orderByChild("timestart").addChildEventListener(this);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStack();
            idle = true;
            manageItem();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        if (dataSnapshot != null && dataSnapshot.getValue() != null){
            QueueUser queueUser = dataSnapshot.getValue(QueueUser.class);
            timeCount += queueUser.getEstimatedTime();

            if (queueUser.getValid() == 0 || queueUser.getReady() == 1){
                if (!pendingList.contains(queueUser)){
                    pendingList.add(queueUser);
                }
            }

            int seconds = (int) (timeCount / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            queueTime.setText(minutes/2 + " min");

            manageItem();
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        if (dataSnapshot != null && dataSnapshot.getValue() != null){
            QueueUser queueUser = dataSnapshot.getValue(QueueUser.class);

            if (queueUser.getReady() == 1){
                if (!pendingList.contains(queueUser)){
                    pendingList.add(queueUser);
                }
            }

            manageItem();
        }
    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {
        if (dataSnapshot != null && dataSnapshot.getValue() != null){
            QueueUser queueUser = dataSnapshot.getValue(QueueUser.class);
            if (pendingList.contains(queueUser)){
                pendingList.remove(queueUser);
            }
        }
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    private void manageItem() {
        if (idle && pendingList.size() > 0){
            idle = false;
            QueueUser queueUser = pendingList.get(0);
            if (queueUser.getReady() == 1){
                launchNewConfirmFragment(queueUser);
            } else {
                launchNewClientFragment(pendingList.get(0));
            }
        }
    }

    @Override
    public void onNewClientManaged() {
        idle = true;
        pendingList.remove(0);

        if (getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStack();
            manageItem();
        }
    }

    @OnClick({R.id.queuemanagerbutton, R.id.cabinmanagerbutton})
    public void switchFragment(View view){
        switch (view.getId()){
            case R.id.queuemanagerbutton:
                queueManager.setVisibility(View.VISIBLE);
                cabinManager.setVisibility(View.GONE);
                break;

            case R.id.cabinmanagerbutton:
                queueManager.setVisibility(View.GONE);
                cabinManager.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void launchNewClientFragment(QueueUser queueUser){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, NewClientFragment.newInstance(queueUser));
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }

    private void launchNewConfirmFragment(QueueUser queueUser){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, NewConfirmFragment.newInstance(queueUser));
        fragmentTransaction.addToBackStack("");
        fragmentTransaction.commit();
    }
}
