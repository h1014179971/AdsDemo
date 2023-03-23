package com.hw;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;


import com.facebook.ads.Ad;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.RewardData;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;
//import com.gameanalytics.sdk.GameAnalytics;
import com.gameanalytics.sdk.GameAnalytics;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
//import com.ironsource.mediationsdk.IronSource;
import com.hw.hwadssdk.HwAdsInterface;
import com.hw.hwadssdk.HwAdsInterstitialListener;
import com.hw.hwadssdk.HwAdsRewardVideoListener;
import com.mopub.common.MoPub;
import com.mopub.common.MoPubReward;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubRewardedVideoListener;
import com.mopub.mobileads.MoPubRewardedVideoManager;
import com.mopub.mobileads.MoPubRewardedVideos;
import com.unity3d.player.UnityPlayer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import com.bytedance.applog.AppLog;
import com.bytedance.applog.InitConfig;
import com.bytedance.applog.util.UriConstants;
public class GamePlayer {

    private  String TAG = "GamePlayer";
    private String teaAppId = "164820";
    private String gaKey = "6b0b633c22f10e82b1365fbc9733914e";
    private String gaSecretKey= "8e6829e7493c5c8ba503f8d52a2b7b21398b9f73";
    public Activity mContext;

    private  String AndroidPlatformWrapper = "HwAdsCallBack";
    private  String rewardTag;
    private  boolean isReward;
    public GamePlayer() {
    }

    public  void initHwSDK(Activity context, String url){
        mContext = context;
        Log.i(TAG, "initHwSDK: "+ Thread.currentThread().getId());
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //doyourcode
                Log.i(TAG, "initHwSDK: "+ Thread.currentThread().getId());
                if(DataJson.getHwads_projectId()==null || DataJson.getHwads_projectId().length() ==0)
                    DataJson.ReadJsonFile(mContext);
                String hwads_projectId = DataJson.getHwads_projectId();
                String hwads_AppToken = DataJson.getHwads_AppToken();
                String hwads_ImportantToken = DataJson.getHwads_ImportantToken();
                String hwads_MonetizationToken = DataJson.getHwads_MonetizationToken();
                String hwads_UACToken = DataJson.getHwads_UACToken();
                Log.i(TAG, "hwads_projectId: "+ hwads_projectId+",hwads_AppToken:"+hwads_AppToken+",hwads_ImportantToken:"+hwads_ImportantToken
                +",hwads_MonetizationToken"+hwads_MonetizationToken+",hwads_UACToken"+hwads_UACToken);
                HwAdsInterface.initSDK((Context)context,hwads_projectId,  hwads_AppToken,hwads_ImportantToken, hwads_MonetizationToken, hwads_UACToken);
            }});


        setInterListerner();
        setRewardListener();

        //0.tenjin
//        String apiKey = "EXM8HSJ3XZSSIKABNAMAELRHOESGXUMB";
//        TenjinSDK instance = TenjinSDK.getInstance(mContext, apiKey);
//        instance.connect();
//        InitConfig config = new InitConfig(teaAppId, "Google Play");
//        config.setUriConfig(UriConfig.DEFAULT);
//        config.setEnablePlay(true);//心跳连接
//        AppLog.setEnableLog(false);// 是否在控制台输出⽇日志，可⽤用于观察⽤用户⾏行行为⽇日志上报情况，建议仅在调试时使⽤用，release版本请设置为false ！
//        GameAnalytics.initializeWithGameKey((Activity) mContext, gaKey, gaSecretKey);
//        GameAnalytics.configureBuild("android 9.9");
        /*ga*/
        String ga_GameKey = DataJson.getGa_GameKey();
        String ga_gameSecret = DataJson.getGa_gameSecret();
        String ga_buildId = DataJson.getGa_buildId();
        GameAnalytics.initializeWithGameKey(ga_GameKey,ga_gameSecret);
        GameAnalytics.configureBuild(ga_buildId);
        /*ga*/
    }

    private void setRewardListener(){
        HwAdsInterface.setHwAdsRewardedVideoListener(new HwAdsRewardVideoListener() {
            @Override
            public void onRewardedVideoLoadSuccess() {
                Log.i(TAG, "onInterstitialLoaded: ");

            }

            @Override
            public void onRewardedVideoLoadFailure() {
                Log.i(TAG, "onRewardedVideoLoadFailure: ");

            }

            @Override
            public void onRewardedVideoStarted() {
                Log.i(TAG, "onRewardedVideoStarted: ");
            }

            @Override
            public void onRewardedVideoPlaybackError() {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "onRewardedVideoPlaybackError: ");
                        UnityPlayer.UnitySendMessage(AndroidPlatformWrapper,"RewardCallBack","false");
                        isReward = false;
                    }});


            }

            @Override
            public void onRewardedVideoClicked() {
                Log.i(TAG, "onRewardedVideoClicked: ");
            }

            @Override
            public void onRewardedVideoClosed() {
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "onRewardedVideoClosed: ");
                        if(isReward)
                            UnityPlayer.UnitySendMessage(AndroidPlatformWrapper,"RewardCallBack","true");
                        else
                            UnityPlayer.UnitySendMessage(AndroidPlatformWrapper,"RewardCallBack","false");
                        isReward = false;
                    }});

            }

            @Override
            public void onRewardedVideoCompleted() {
                Log.i(TAG, "onRewardedVideoCompleted: ");
                isReward = true;
            }
        });
    }

    private void setInterListerner(){
        HwAdsInterface.setHwAdsInterstitialListener(new HwAdsInterstitialListener() {
            @Override
            public void onInterstitialLoaded() {
                Log.i(TAG, "onInterstitialLoaded: ");
            }

            @Override
            public void onInterstitialFailed() {
                Log.i(TAG, "onInterstitialFailed: ");
            }

            @Override
            public void onInterstitialShown() {
                Log.i(TAG, "onInterstitialShown: ");
            }

            @Override
            public void onInterstitialClicked() {
                Log.i(TAG, "onInterstitialClicked: ");
            }

            @Override
            public void onInterstitialDismissed() {
                Log.i(TAG, "onInterstitialDismissed: ");
            }
        });
    }


    public void showHwRewardAd(String arg1){
        rewardTag = arg1;
        Log.i(TAG, "showHwRewardAd: "+arg1);
        HwAdsInterface.showReward(arg1);

    }
    public void showHwInterAd(){
        Log.i(TAG, "showHwInterAd: ");
        HwAdsInterface.showInter();

    }
    public boolean isHwRewardLoaded(){
        Log.i(TAG, "isHwRewardLoaded: " );
        return  HwAdsInterface.isRewardLoad();
    }

    public  boolean isHwInterLoaded(){
        Log.i(TAG, "isHwInterLoaded: " );
        return  HwAdsInterface.isInterLoad();
    }

    public  void  TAEventPropertie(String custom,String dic){
        Log.i(TAG,"TAEventPropertie custom:"+custom+"=>dic:"+dic);
        String ssid = AppLog.getSsid(); // 获取数说 ID
        String did = AppLog.getDid(); // 获取服务端 device ID
        String iid = AppLog.getIid(); // 获取 install ID
        Log.i(TAG,"ssid:"+ssid+"did:"+did+"iid:"+iid);
        try{
            JSONObject paramsObj = new JSONObject(dic);
            Log.i(TAG,"TAEventPropertie paramsObj:"+paramsObj);
            AppLog.onEventV3(custom,paramsObj);
            if(paramsObj.length() > 0){
                Iterator iter = paramsObj.keys();
                while (iter.hasNext()) {
                    String gaEventKey = (String) iter.next();
                    String gaEventValue = paramsObj.getString(gaEventKey);
                    if(custom != null && gaEventKey != null && gaEventValue != null){
                        String gaEvent = custom+":"+gaEventKey+":"+gaEventValue;
                        if(gaEvent != null){
                            Log.i(TAG,"GameAnalytics gaEvent:"+gaEvent);
                            GameAnalytics.addDesignEventWithEventId(gaEvent);
                        }
                    }

                }
            }
        }catch (Exception e){
            Log.i(TAG, "TAEventPropertie: " + e.toString());
            e.printStackTrace();
        }
    }


}
