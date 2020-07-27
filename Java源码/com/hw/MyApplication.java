package com.hw;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustAttribution;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.AdjustEventFailure;
import com.adjust.sdk.AdjustEventSuccess;
import com.adjust.sdk.BuildConfig;
import com.adjust.sdk.LogLevel;
import com.adjust.sdk.OnAttributionChangedListener;
import com.adjust.sdk.OnEventTrackingFailedListener;
import com.adjust.sdk.OnEventTrackingSucceededListener;
import com.bytedance.applog.AppLog;
import com.bytedance.applog.InitConfig;
import com.bytedance.applog.util.UriConstants;
import com.tencent.bugly.*;
import com.tencent.bugly.crashreport.CrashReport;
import com.gameanalytics.sdk.GameAnalytics;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        DataJson.ReadJsonFile(this);
        Log.i(TAG, "application onCreate: ");
        /* dataplayer 初始化开始 */
        String dp_appId = DataJson.getDp_appID();
        String dp_channel = DataJson.getDp_channel();
        Log.i(TAG, "dp_appid:"+dp_appId+",dp_channel:"+dp_channel);
        final InitConfig initConfig = new InitConfig(dp_appId,dp_channel);
        initConfig.setUriConfig (UriConstants.AMERICA);
        //initConfig.setAbEnable(true);  // 开启ABTest
        initConfig.setAutoStart(true);
        AppLog.init(this, initConfig);

        /*bugly*/
        String buglyId = DataJson.getBugly_appID();
        Log.i(TAG, "buglyId:"+buglyId);
        CrashReport.initCrashReport(getApplicationContext(), buglyId, false);
        /*bugly*/
        Log.i(TAG, "appToken:"+DataJson.getHwads_AppToken());
        String appToken = DataJson.getHwads_AppToken();//注意这个值找对接人员要，这个值非常重要，不能错
        String environment = AdjustConfig.ENVIRONMENT_SANDBOX;
        if(BuildConfig.BUILD_TYPE!="debug"){
            environment = AdjustConfig.ENVIRONMENT_PRODUCTION;
        }

//        String environment = AdjustConfig.ENVIRONMENT_PRODUCTION;
        AdjustConfig config = new AdjustConfig(this, appToken, environment);
        config.setLogLevel(LogLevel.VERBOSE);

        config.setOnAttributionChangedListener(new OnAttributionChangedListener() {
            @Override
            public void onAttributionChanged(AdjustAttribution attribution) {
                Log.i(TAG, "onAttributionChanged: " + attribution.toString());
            }
        });
        config.setOnEventTrackingSucceededListener(new OnEventTrackingSucceededListener() {
            @Override
            public void onFinishedEventTrackingSucceeded(AdjustEventSuccess eventSuccessResponseData) {
                Log.i(TAG, "onFinishedEventTrackingSucceeded: " + eventSuccessResponseData.toString());
            }
        });
        config.setOnEventTrackingFailedListener(new OnEventTrackingFailedListener() {
            @Override
            public void onFinishedEventTrackingFailed(AdjustEventFailure eventFailureResponseData) {
                Log.i(TAG, "onFinishedEventTrackingFailed: " + eventFailureResponseData.toString());
            }
        });

        config.setDefaultTracker("AJHwAds");
        config.setSendInBackground(true);


        Adjust.onCreate(config);



        registerActivityLifecycleCallbacks(new AdjustLifecycleCallbacks());
    }

    private static final class AdjustLifecycleCallbacks implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            Adjust.onResume();
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Adjust.onPause();
        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }
}