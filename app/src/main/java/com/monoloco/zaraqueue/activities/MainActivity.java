package com.monoloco.zaraqueue.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;
import com.monoloco.zaraqueue.R;
import com.monoloco.zaraqueue.base.BaseActivity;
import com.monoloco.zaraqueue.firebase.FirebaseManager;
import com.monoloco.zaraqueue.model.Clothes;
import com.monoloco.zaraqueue.model.QueueUser;

import java.util.ArrayList;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    private FirebaseManager firebaseManager;
    private boolean flag = true;

    @Inject QueueUser queueUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseManager = new FirebaseManager(this);

        queueUser.setBirthdate(12345678);
        queueUser.setName("Paquito");
        queueUser.setCompany(true);
        queueUser.setEstimatedTime(9853);
        queueUser.setGender(1);

        Clothes clothes1 = new Clothes("123456", 1);
        Clothes clothes2 = new Clothes("12341", 2);
        Clothes clothes3 = new Clothes("1234", 3);
        Clothes clothes4 = new Clothes("123", 4);

        ArrayList<Clothes> clothes = new ArrayList<>();
        clothes.add(clothes1);
        clothes.add(clothes2);
        clothes.add(clothes3);
        clothes.add(clothes4);

        queueUser.setClothes(clothes);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag){
                    firebaseManager.pushUserToQueue("QUEUE_1", queueUser);
                } else {
                    firebaseManager.popUserFromQueue("QUEUE_1", queueUser);
                }
                flag = !flag;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
