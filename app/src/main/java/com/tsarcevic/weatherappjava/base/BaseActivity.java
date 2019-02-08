package com.tsarcevic.weatherappjava.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.tsarcevic.weatherappjava.Constants;
import com.tsarcevic.weatherappjava.service.LogoutService;
import com.tsarcevic.weatherappjava.view.login.LoginActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private GoogleSignInClient signInClient;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            logoutUser();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        initGoogleLogin();
        initUI();
        if (!(BaseActivity.this instanceof LoginActivity)) {
            startService(new Intent(this, LogoutService.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(Constants.STARTING_ACTIVITY));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        LogoutService.resetTimer();
    }

    protected void initGoogleLogin() {
    }

    protected void initGoogleSignInClient(GoogleSignInOptions options) {
        signInClient = GoogleSignIn.getClient(this, options);
    }

    protected abstract int getLayout();

    protected abstract void initUI();

    protected void logoutUser() {
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            signInClient.signOut().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    startLoginActivity();
                }
            });
        } else if (AccessToken.getCurrentAccessToken() != null) {
            LoginManager.getInstance().logOut();
            startLoginActivity();
        } else {
            startLoginActivity();
        }
    }

    protected void startLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
