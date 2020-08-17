package com.mapmyindia.sdk.exploremyindia;

import android.app.Application;

import com.mapbox.mapboxsdk.MapmyIndia;
import com.mmi.services.account.MapmyIndiaAccountManager;

/**
 * Created by CEINFO on 29-06-2018.
 */

public class MapApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MapmyIndiaAccountManager.getInstance().setRestAPIKey(getRestAPIKey());
        MapmyIndiaAccountManager.getInstance().setMapSDKKey(getMapSDKKey());
        MapmyIndiaAccountManager.getInstance().setAtlasClientId(getAtlasClientId());
        MapmyIndiaAccountManager.getInstance().setAtlasClientSecret(getAtlasClientSecret());
        MapmyIndiaAccountManager.getInstance().setAtlasGrantType(getAtlasGrantType());
        MapmyIndia.getInstance(this);
    }

    public String getAtlasClientId() {
        return "AANhLd6qR9COjz9EQXab0ueYTT75Svt26U65RLPolKnXE9wB3IYOPgWqRfM6B0pQoHvZRLEQt7_lEKdGMF-yujp69NYXZmZk";
    }

    public String getAtlasClientSecret() {
        return "9K_q_9Q2GHNxdOQooiSyFY3hjy_NKBYn1AjpkY849Uho-0-TbnsOf5B2OZ5W65qcssseubSh7OXq_3u_XtQC4Aem_t_UjN3ivsZ1N8m623Q=";
    }


    public String getAtlasGrantType() {
        return "client_credentials";
    }

    public String getMapSDKKey() {
        return "gqj5thyfabowg4yxja9pughyntks6cll";
    }

    public String getRestAPIKey() {
        return "cgmpdryhmhd9mtgqi3cbulvs4vza61st";
    }

}
