package com.cedro.memoriesoftravel.helper;

import android.app.Activity;

import com.facebook.AccessToken;
import com.facebook.Profile;

/**
 * Created by emerson on 08/10/16.
 */

public class FacebookHelper {

    public static boolean isLoggedIn(Activity act) {
        AccessToken accesstoken = AccessToken.getCurrentAccessToken();
        return !(accesstoken == null || accesstoken.getPermissions().isEmpty());
    }

    public static Profile getLoginInfo() {
        Profile profile = Profile.getCurrentProfile();
        return profile;
    }

}
