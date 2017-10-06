package com.monoloco.zaraqueue.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monoloco.zaraqueue.R;
import com.monoloco.zaraqueue.base.BaseActivity;
import com.monoloco.zaraqueue.firebase.FirebaseManager;
import com.monoloco.zaraqueue.interfaces.OnQueueUpdatedListener;
import com.monoloco.zaraqueue.model.Clothes;
import com.monoloco.zaraqueue.model.QueueUser;
import com.monoloco.zaraqueue.preferences.PreferencesManager;
import com.monoloco.zaraqueue.utils.Constants;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClientActivity extends BaseActivity implements OnQueueUpdatedListener{

    public static final String MIME_TEXT_PLAIN = "text/plain";

    private FirebaseManager firebaseManager;

    @BindView(R.id.msg_box_call) LinearLayout msgBoxCall;
    @BindView(R.id.msg_box_queue) LinearLayout msgBoxQueue;
    @BindView(R.id.msg_box_timeover) LinearLayout msgBoxTimeover;
    @BindView(R.id.queuetime) TextView queueTime;
    @BindView(R.id.timer) TextView timer;

    @Inject QueueUser queueUser;
    @Inject PreferencesManager preferencesManager;

    private NfcAdapter nfcAdapter;

    private PendingIntent nfcPendingIntent;
    private IntentFilter[] intentFiltersArray;
    private Handler timerHandler;
    private long startTime = 0;

    private CountDownTimer countDownTimer = new CountDownTimer(5*60*1000, 500) {
        @Override
        public void onTick(long l) {
            long millis = l;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            timer.setText(String.format("%02d:%02d", minutes, seconds));
        }

        @Override
        public void onFinish() {
            firebaseManager.popUserFromQueue("QUEUE_1", queueUser);
            onQueueStateChanged(Constants.USER_TIMEOUT);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        firebaseManager = FirebaseManager.getInstance(this);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        tagDetected.addCategory(Intent.CATEGORY_DEFAULT);

        Intent nfcIntent = new Intent(this, getClass());
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        nfcPendingIntent =
                PendingIntent.getActivity(this, 0, nfcIntent, 0);

        IntentFilter tagIntentFilter =
                new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            tagIntentFilter.addDataType("text/plain");
            intentFiltersArray = new IntentFilter[]{tagIntentFilter};
        }
        catch (Throwable t) {
            t.printStackTrace();
        }

        queueUser.setAge(0);
        queueUser.setName("");
        queueUser.setCompany(0);
        queueUser.setEstimatedTime(0);
        queueUser.setGender(2);
        queueUser.setValid(0);

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
    }

    @OnClick(R.id.timeover_renovar)
    public void manageTimeover(){
        firebaseManager.pushUserToQueue("QUEUE_1", queueUser);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if(NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)){
            if (!preferencesManager.getIsInQueue()){
                firebaseManager.pushUserToQueue("QUEUE_1", queueUser);
            } else {
                firebaseManager.popUserFromQueue("QUEUE_1", queueUser);
            }

            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, nfcPendingIntent, intentFiltersArray, null);
        handleIntent(getIntent());
    }

    @Override
    protected void onPause() {
        nfcAdapter.disableForegroundDispatch(this);
        super.onPause();
    }

    @Override
    public void onQueueUpdated(long timecount, int size) {
        Log.d("QUEUE_SIZE", String.valueOf(size) + "with a time of " + timecount);
        Log.d("QUEUE_IN", String.valueOf(preferencesManager.getIsInQueue()));

        int seconds = (int) (timecount / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;

        queueTime.setText(minutes + " min");
    }

    @Override
    public void onQueueStateChanged(int state) {
        msgBoxCall.setVisibility(View.GONE);
        msgBoxQueue.setVisibility(View.GONE);
        msgBoxTimeover.setVisibility(View.GONE);

        switch (state){
            case Constants.USER_OUT_OF_QUEUE:
                break;
            case Constants.USER_IN_FITTING:
                stopTimer();
                break;
            case Constants.USER_IN_QUEUE:
                msgBoxQueue.setVisibility(View.VISIBLE);
                break;
            case Constants.USER_TIMEOUT:
                msgBoxTimeover.setVisibility(View.VISIBLE);
                break;
            case Constants.USER_WAITING:
                msgBoxCall.setVisibility(View.VISIBLE);
                startTimer();
                break;
        }
    }

    private void startTimer(){
        countDownTimer.start();
    }

    private void stopTimer(){
        countDownTimer.cancel();
        //timerHandler.removeCallbacks(timerRunnable);
    }
}
