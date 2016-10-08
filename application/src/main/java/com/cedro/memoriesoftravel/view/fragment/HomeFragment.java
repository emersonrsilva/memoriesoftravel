package com.cedro.memoriesoftravel.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cedro.memoriesoftravel.helper.FacebookHelper;
import com.cedro.memoriesoftravel.view.activity.MainActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.memoriesoftravel.R;
import com.squareup.picasso.Picasso;


/**
 * Created by emerson on 07/10/16.
 */

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment{
    private View rootView;
    private ImageView profilePictureView;
    private TextView userNameView;
    private Profile profile;
    private Activity mActivity;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView info;

    @SuppressLint("ValidFragment")
    public HomeFragment(Activity context){
        mActivity = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.home_fragment, container, false);
        FacebookSdk.sdkInitialize(mActivity.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton)rootView.findViewById(R.id.login_button);
        info = (TextView) rootView.findViewById(R.id.info);


        profilePictureView = (ImageView) rootView.findViewById(R.id.user_pic);
        userNameView = (TextView) rootView.findViewById(R.id.user_name);
        profile = FacebookHelper.getLoginInfo();
        if(profile != null){
            userNameView.setText("Bem Vindo!\n"+profile.getFirstName()+" "+profile.getLastName());
            Picasso.with(getContext()).load("https://graph.facebook.com/" + profile.getId() + "/picture?type=large").into(profilePictureView);

        }
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                gotoLogin();
            }


        });


    return  rootView;
    }

    private void gotoLogin() {
        // Como chamar a intent a partir de um fragment pode causar crash no aplicativo,
        // eu resolvi usar Broadcast para a MainActivity e ela exibir o login
        // DESCULPE :(
        /*
        Intent intent = new Intent(mActivity, HomeFragment.class);
        startActivity(intent);
        mActivity.finish();*/
        Intent intnet = new Intent("START_ACTIVITY");
        intnet.putExtra("activity", "login");
        getContext().sendBroadcast(intnet);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
