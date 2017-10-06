package com.monoloco.zaraqueue.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        ButterKnife.bind(this);

        pendingList = new ArrayList<>();

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
    }

    private void removeListeners() {
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

            if (queueUser.getValid() == 0){
                if (!pendingList.contains(queueUser)){
                    pendingList.add(queueUser);
                }
            }

            manageItem();
        }
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
            launchNewClientFragment(pendingList.get(0));
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
}
