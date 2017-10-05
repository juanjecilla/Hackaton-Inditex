package com.monoloco.zaraqueue.firebase;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.monoloco.zaraqueue.ApplicationClass;
import com.monoloco.zaraqueue.interfaces.OnQueueUpdatedListener;
import com.monoloco.zaraqueue.model.QueueUser;
import com.monoloco.zaraqueue.preferences.PreferencesManager;
import com.monoloco.zaraqueue.utils.Constants;

import java.util.Calendar;

import javax.inject.Inject;

/**
 * Created by root on 5/10/17.
 */

public class FirebaseManager implements ChildEventListener {

    private Context context;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    @Inject QueueUser queueUser;
    @Inject PreferencesManager preferencesManager;

    private static FirebaseManager instance;

    public static FirebaseManager getInstance(Context context){
        if (instance == null){
            new FirebaseManager(context);
        }

        return instance;
    }

    public FirebaseManager(Context context) {
        ApplicationClass.injectMember(this);
        this.context = context;
        initialize();
        instance = this;
    }

    private void initialize() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            Log.d("HOLA", "NOAUTH");
            FirebaseAuth.getInstance().signInAnonymously();
        } else {
            Log.d("HOLA", "AUTH");
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
                    preferencesManager.setUserToken(firebaseUser.getUid());
                    initializeQueue();
                }
            }
        });
    }

    private void removeListeners() {
        databaseReference.removeEventListener(this);
        databaseReference.removeEventListener(valueEventListener);
    }

    private void initializeQueue(){
        Log.d("HOLA", "nnnNULL");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("queues").child("QUEUE_1");
        databaseReference.orderByChild("timestart").addChildEventListener(this);
        databaseReference.orderByChild("timestart").addValueEventListener(valueEventListener);
    }

    public void pushUserToQueue(String uid, QueueUser queueUser){
        if (databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference().child("queues").child("QUEUE_1");
        }
        String key = databaseReference.push().getKey();
        this.queueUser.setUid(preferencesManager.getUserToken());
        this.queueUser.setTimestart(Calendar.getInstance().getTimeInMillis());
        databaseReference.child(preferencesManager.getUserToken()).setValue(queueUser);
        preferencesManager.setIsInQueue(true);
    }

    public void popUserFromQueue(String uid, QueueUser queueUser){
        if (databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference().child("queues").child("QUEUE_1");
        }
        databaseReference.child(preferencesManager.getUserToken()).removeValue();
        preferencesManager.setIsInQueue(false);
    }

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
        Log.d("HOLA_R", String.valueOf(dataSnapshot.getValue()));
/*                QueueItem queueItem = dataSnapshot.getValue(QueueItem.class);
                localDatabasesUtils.removeFromQueue(queueItem);*/
    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    private ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d("ON_DATA_CHANGED", String.valueOf(dataSnapshot));
            if (dataSnapshot != null && dataSnapshot.getValue() != null){
                long timeCount = 0;
                int peopleCount = 0;

                if (dataSnapshot.hasChild(preferencesManager.getUserToken())){
                    preferencesManager.setIsInQueue(true);
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        QueueUser queueUser = child.getValue(QueueUser.class);

                        if (!queueUser.getUid().equals(preferencesManager.getUserToken())){
                            timeCount += queueUser.getEstimatedTime();
                            peopleCount++;
                        }
                    }

                    if (timeCount < 1000*60*5){
                        ((OnQueueUpdatedListener)context).onQueueStateChanged(Constants.USER_WAITING);
                    } else {
                        ((OnQueueUpdatedListener)context).onQueueStateChanged(Constants.USER_IN_QUEUE);
                        ((OnQueueUpdatedListener)context).onQueueUpdated(timeCount, peopleCount);
                    }

                } else {
                    preferencesManager.setIsInQueue(false);
                    ((OnQueueUpdatedListener)context).onQueueStateChanged(Constants.USER_OUT_OF_QUEUE);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}
