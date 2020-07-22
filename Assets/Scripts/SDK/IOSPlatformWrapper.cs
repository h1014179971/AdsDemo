#if UNITY_IOS 
using AOT;
using System.Collections;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using UnityEngine;

public class IOSPlatformWrapper{

    public delegate void CallbackDelegate(string str);
    private static System.Action<bool> rewardCallBack;
    [DllImport("__Internal")]
    private static extern void initHwAds( string jsonStr, CallbackDelegate callBack = null);
    [DllImport("__Internal")]
    private static extern void showHwRewardAd(string tag, CallbackDelegate platformCallback_RewardAd);
    [DllImport("__Internal")]
    private static extern void showHwInterAd(CallbackDelegate platformCallback_InterAd);
    [DllImport("__Internal")]
    private static extern bool isHwRewardLoaded();
    [DllImport("__Internal")]
    private static extern bool isHwInterLoaded();
    /////////////// 统计////////////////
    [DllImport("__Internal")]
    private static extern void TAEventHwPropertie(string key,string jsonStr);
    public static void initSDK()
    {
        Debug.Log("initSDK");
        initHwAds("121212", initSDKCallback);
    }
    [MonoPInvokeCallback(typeof(CallbackDelegate))]
    public static void initSDKCallback(string str)
    {
        Debug.Log("initSDKCallback");
    }

    public static void showRewardedVideo(string tag)
    {
        Debug.Log("IOSPlatformWrapper showRewardedVideo");
        showHwRewardAd(tag, PlatformCallback_RewardAd);
    }
    public static void showRewardedVideo(string tag,System.Action<bool> actionCallBack)
    {
        Debug.Log("IOSPlatformWrapper showRewardedVideo Action");
        rewardCallBack = actionCallBack;
        showHwRewardAd(tag, PlatformCallback_RewardAd);
    }
    [MonoPInvokeCallback(typeof(CallbackDelegate))]
    public static void PlatformCallback_RewardAd(string str)
    {
        Debug.Log("IOSPlatformWrapper   PlatformCallback_RewardAd:" + str);
        if (str.Equals("true"))
        {
            if (rewardCallBack != null)
                rewardCallBack(true);
            
        }
        else
        {
            if (rewardCallBack != null)
                rewardCallBack(false);
        }
    }
    public static bool isRewardLoaded()
    {
        return isHwRewardLoaded();
    }
    public static void showInterAd()
    {
        showHwInterAd(PlatformCallback_InterAd);
    }
    [MonoPInvokeCallback(typeof(CallbackDelegate))]
    public static void PlatformCallback_InterAd(string str)
    {

    }
    public static bool isInterLoaded()
    {
        return isHwInterLoaded();
    }

    public static void  TAEventPropertie(string key,string jsonStr)
    {
        Debug.Log("IOSPlatformWrapper EventPropertie:"+jsonStr);
        TAEventHwPropertie(key,jsonStr);
    }

}
#endif
