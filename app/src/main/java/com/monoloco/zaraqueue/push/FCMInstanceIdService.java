package com.monoloco.zaraqueue.push;

import android.content.Context;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.monoloco.zaraqueue.ApplicationClass;

/**
 * Created by root on 5/10/17.
 */

public class FCMInstanceIdService extends FirebaseInstanceIdService {

    private Context context;

    public FCMInstanceIdService() {
        ApplicationClass.injectMember(this);
    }

    public FCMInstanceIdService(Context context) {
        ApplicationClass.injectMember(this);
        this.context = context;
    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("TOKEN", "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}
