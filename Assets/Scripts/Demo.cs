using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;


public class Demo : MonoBehaviour
{
    public Text btnText;
    private double coinNum;

    // Start is called before the first frame update
    void Start()
    {
        Application.targetFrameRate = 10;
        PlatformFactory.Instance.initSDK();
        Debug.Log("data path ========" + Application.streamingAssetsPath);
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    public void InitSdk()
    {
        
    }
    public void ShowAdOnClick()
    {
        coinNum += 1000000000000000000;
        if (PlatformFactory.Instance.isRewardLoaded())
        {
            btnText.text = "True";
            PlatformFactory.Instance.showRewardedVideo("showad");
        }
        else
        {
            btnText.text = "False";
        }
        
    }
    public void EventPropertieOnClick()
    {
        Debug.Log("Demo EventPropertieOnClick");
        string customKey = "Demo";
        Dictionary<string, string> dic = new Dictionary<string, string>();
        dic.Add("Test", "Test1");
        PlatformFactory.Instance.TAEventPropertie(customKey,dic);
    }
}
