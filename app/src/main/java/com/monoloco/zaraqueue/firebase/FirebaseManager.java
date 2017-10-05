package com.monoloco.zaraqueue.firebase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.monoloco.zaraqueue.ApplicationClass;
import com.monoloco.zaraqueue.model.QueueUser;
import com.monoloco.zaraqueue.utils.LocalDatabasesUtils;

import javax.inject.Inject;

/**
 * Created by root on 5/10/17.
 */

public class FirebaseManager {

    private Context context;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    @Inject QueueUser queueUser;

    public FirebaseManager(Context context) {
        ApplicationClass.injectMember(this);
        this.context = context;
        initialize();
    }

    private void initialize() {
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                initializeQueue();
            }
        });

        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            Log.d("HOLA", "NOAUTH");
            FirebaseAuth.getInstance().signInAnonymously();
        } else {
            Log.d("HOLA", "AUTH");
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        }
    }

    private void initializeQueue(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("queues").child("QUEUE_1");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("HOLA_A", String.valueOf(dataSnapshot.getValue()));
/*                QueueItem queueItem = dataSnapshot.getValue(QueueItem.class);
                localDatabasesUtils.addToQueue(queueItem);*/
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d("HOLA_C", String.valueOf(dataSnapshot.getValue()));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
/*                QueueItem queueItem = dataSnapshot.getValue(QueueItem.class);
                localDatabasesUtils.removeFromQueue(queueItem);*/
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void pushUserToQueue(String uid, QueueUser queueUser){
        String key = databaseReference.push().getKey();
        this.queueUser.setUid(key);
        databaseReference.child(key).setValue(queueUser);
    }

    public void popUserFromQueue(String uid, QueueUser queueUser){
        databaseReference.child(queueUser.getUid()).removeValue();
    }
}
