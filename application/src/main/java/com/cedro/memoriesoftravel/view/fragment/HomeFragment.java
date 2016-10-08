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


/**
 * Created by emerson on 07/10/16.
 */

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment{
    private View rootView;
    private ProfilePictureView profilePictureView;
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


        profilePictureView = (ProfilePictureView) rootView.findViewById(R.id.user_pic);
        profilePictureView.setCropped(true);
        profilePictureView.setPresetSize(ProfilePictureView.LARGE);
        userNameView = (TextView) rootView.findViewById(R.id.user_name);
        profile = FacebookHelper.getLoginInfo();
        if(profile != null){
            userNameView.setText(profile.getFirstName());
            profilePictureView.setProfileId(profile.getId());

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
        Intent intent = new Intent(rootView.getContext(), HomeFragment.class);
        startActivity(intent);
        mActivity.finish();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
