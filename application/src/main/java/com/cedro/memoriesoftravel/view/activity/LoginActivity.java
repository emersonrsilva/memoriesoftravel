package com.cedro.memoriesoftravel.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.memoriesoftravel.R;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.listeners.OnLoginListener;

import java.util.List;

import static com.facebook.R.id.info;

/**
 * Created by emerson on 08/10/16.
 */

public class LoginActivity extends Activity {

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private TextView info;
    SharedPreferences sharedPresences;
    SharedPreferences.Editor preferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        sharedPresences = getSharedPreferences("login", MODE_PRIVATE);
        preferencesEditor = sharedPresences.edit();

        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        loginButton = (LoginButton)findViewById(R.id.login_button);
        info = (TextView)findViewById(R.id.info);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                autenticar(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                deslogar(true);
            }

            @Override
            public void onError(FacebookException e) {
                    info.setText("Login attempt failed.");
            }
        });

        if(sharedPresences.getBoolean("logado",false)){
            AccessToken token = AccessToken.getCurrentAccessToken();
            if(token != null && !token.isExpired()) {
                autenticar(token);
                return;
            }
        }

        deslogar(false);
    }

    private void deslogar(boolean recreate) {
        preferencesEditor.putBoolean("logado", false);
        preferencesEditor.putString("token", null);
        preferencesEditor.apply();

        if(recreate)
            this.recreate();

    }

    private void autenticar(AccessToken token) {
        preferencesEditor.putBoolean("logado", true);
        preferencesEditor.putString("token", token.getToken());
        preferencesEditor.apply();
        exibirMain();
    }

    private void exibirMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}