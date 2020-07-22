#if UNITY_ANDROID
using System.Collections;
using System.Collections.Generic;
using System.Runtime.InteropServices;
using UnityEngine;
using System;


public class AndroidPlatform : PlatformFactory
{
                                                               
    public override void initSDK()
    {
        AndroidPlatformWrapper.Instance.initSDK();
    }
    public override bool isRewardLoaded()
    {
        Debug.Log("AndroidPlatform isRewardLoaded");
        return AndroidPlatformWrapper.Instance.isRewardLoaded();
    }
    public override void showRewardedVideo(string tag)
    {
        Debug.Log("AndroidPlatform showRewardedVideo");
        AndroidPlatformWrapper.Instance.showRewardedVideo(tag);
    }
    public override void showRewardedVideo(string tag, Action<bool> actionCallBack)
    {
        Debug.Log("AndroidPlatform showRewardedVideo Action");
        AndroidPlatformWrapper.Instance.showRewardedVideo(tag, actionCallBack);
    }
    public override bool isInterLoaded()
    {
        Debug.Log("AndroidPlatform isInterLoaded");
        return AndroidPlatformWrapper.Instance.isInterLoaded();
    }
    public override void showInterAd()
    {
        Debug.Log("AndroidPlatform showInterAd");
        AndroidPlatformWrapper.Instance.showInterAd();
    }
    public override void GameQuit()
    {
        AndroidPlatformWrapper.Instance.GameQuit();
    }

}
#endif
