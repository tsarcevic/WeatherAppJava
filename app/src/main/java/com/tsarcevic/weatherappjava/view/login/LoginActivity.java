package com.tsarcevic.weatherappjava.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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
        startActivity(new Intent(this, WeatherInfoView.class));
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
        startWeatherActivity(signInAccount);
    }

    private void startWeatherActivity(GoogleSignInAccount signInAccount) {
        if (signInAccount != null) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.GOOGLE_LOGIN_REQUEST_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            startWeatherActivity(task.getResult());
        }
    }
}
