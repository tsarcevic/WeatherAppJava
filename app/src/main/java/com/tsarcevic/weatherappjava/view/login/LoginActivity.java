package com.tsarcevic.weatherappjava.view.login;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.tsarcevic.weatherappjava.Constants;
import com.tsarcevic.weatherappjava.R;
import com.tsarcevic.weatherappjava.base.BaseActivity;
import com.tsarcevic.weatherappjava.view.map.MapPickerActivity;
import com.tsarcevic.weatherappjava.view.weatherinfo.WeatherInfoView;

import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    private CallbackManager callbackManager;
    private GoogleSignInClient googleSignInClient;

    @BindView(R.id.activity_login_facebook_button)
    LoginButton loginButton;

    @OnClick(R.id.activity_login_google_button)
    public void onGoogleButtonClicked() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, Constants.GOOGLE_LOGIN_REQUEST_CODE);
    }

    @OnClick(R.id.activity_login_without_login_button)
    public void onContinueWithoutLoginButton() {
        startNewActivity();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initUI() {
        initFacebookLogin();
        initGoogleLogin();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        startWeatherActivity(signInAccount, false);
        if (AccessToken.getCurrentAccessToken() != null) {
            startWeatherActivity(null, true);
        }
    }

    private void startWeatherActivity(GoogleSignInAccount signInAccount, boolean isFacebookLogged) {
        if (signInAccount != null) {
            startActivity(new Intent(this, WeatherInfoView.class));
        }
        if (isFacebookLogged) {
            startActivity(new Intent(this, WeatherInfoView.class));
        }
    }

    @Override
    protected void initGoogleLogin() {
        super.initGoogleLogin();
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(getApplication(), signInOptions);
    }

    private void initFacebookLogin() {
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));

        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Profile profile = Profile.getCurrentProfile();
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "email");
                request.setParameters(parameters);
                request.executeAsync();
                startActivity(new Intent(getApplicationContext(), WeatherInfoView.class));
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Something went horribly wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startNewActivity() {
        showNotification();

        startActivity(new Intent(getApplicationContext(), WeatherInfoView.class));
    }

    private void showNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, MapPickerActivity.class);
        intent.setAction("Osijek");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent intent2 = new Intent(this, MapPickerActivity.class);
        intent2.setAction("Zagreb");
        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, Constants.PENDING_INTENT_REQUEST_CODE, intent, 0);
        PendingIntent pendingIntent2 = PendingIntent.getActivity(this, Constants.PENDING_INTENT_REQUEST_CODE, intent2, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channelFacebook")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Lokacija")
                .setContentText("Odaberite lokaciju")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ic_replace, "Osijek", pendingIntent)
                .addAction(R.drawable.ic_replace, "Zagreb", pendingIntent2)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("10001", "NOTIFICATION_CHANNEL_NAME", NotificationManager.IMPORTANCE_HIGH);
            builder.setChannelId("10001");
            manager.createNotificationChannel(notificationChannel);
        }

        manager.notify(15, builder.build());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.GOOGLE_LOGIN_REQUEST_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            startWeatherActivity(task.getResult(), false);
        }
    }
}
