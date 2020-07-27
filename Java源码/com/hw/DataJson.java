package com.hw;
import android.app.Activity;
import android.app.Application;
import android.provider.ContactsContract;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.io.IOException;
public class DataJson {
    private static final String TAG = "DataJson";
    private static String hwads_projectId;
    private static String hwads_AppToken;
    private static String hwads_ImportantToken;
    private static String hwads_MonetizationToken;
    private static String hwads_UACToken;
    private static String ga_GameKey;
    private static String ga_gameSecret;
    private static String ga_buildId;
    private static String dp_appID;
    private static String dp_appName;
    private static String dp_channel;
    private static String dp_serviceVendor;
    private static String bugly_appID;
    public static String getHwads_projectId() {
        return hwads_projectId;
    }

    public static void setHwads_projectId(String hwads_projectId) {
        DataJson.hwads_projectId = hwads_projectId;
    }

    public static String getHwads_AppToken() {
        return hwads_AppToken;
    }

    public static void setHwads_AppToken(String hwads_AppToken) {
        DataJson.hwads_AppToken = hwads_AppToken;
    }

    public static String getHwads_ImportantToken() {
        return hwads_ImportantToken;
    }

    public static void setHwads_ImportantToken(String hwads_ImportantToken) {
        DataJson.hwads_ImportantToken = hwads_ImportantToken;
    }

    public static String getHwads_MonetizationToken() {
        return hwads_MonetizationToken;
    }

    public static void setHwads_MonetizationToken(String hwads_MonetizationToken) {
        DataJson.hwads_MonetizationToken = hwads_MonetizationToken;
    }

    public static String getHwads_UACToken() {
        return hwads_UACToken;
    }

    public static void setHwads_UACToken(String hwads_UACToken) {
        DataJson.hwads_UACToken = hwads_UACToken;
    }

    public static String getGa_GameKey() {
        return ga_GameKey;
    }

    public static void setGa_GameKey(String ga_GameKey) {
        DataJson.ga_GameKey = ga_GameKey;
    }

    public static String getGa_gameSecret() {
        return ga_gameSecret;
    }

    public static void setGa_gameSecret(String ga_gameSecret) {
        DataJson.ga_gameSecret = ga_gameSecret;
    }

    public static String getGa_buildId() {
        return ga_buildId;
    }

    public static void setGa_buildId(String ga_buildId) {
        DataJson.ga_buildId = ga_buildId;
    }

    public static String getDp_appID() {
        return dp_appID;
    }

    public static void setDp_appID(String dp_appID) {
        DataJson.dp_appID = dp_appID;
    }

    public static String getDp_appName() {
        return dp_appName;
    }

    public static void setDp_appName(String dp_appName) {
        DataJson.dp_appName = dp_appName;
    }

    public static String getDp_channel() {
        return dp_channel;
    }

    public static void setDp_channel(String dp_channel) {
        DataJson.dp_channel = dp_channel;
    }

    public static String getDp_serviceVendor() {
        return dp_serviceVendor;
    }

    public static void setDp_serviceVendor(String dp_serviceVendor) {
        DataJson.dp_serviceVendor = dp_serviceVendor;
    }

    public static String getBugly_appID() {
        return bugly_appID;
    }

    public static void setBugly_appID(String bugly_appID) {
        DataJson.bugly_appID = bugly_appID;
    }

    //读取文本
    public  static  void ReadJsonFile(Application application){
        String jsonStr = "";
        String filePath = "";
        File directory = new File("");//参数为空
        Log.i(TAG, "filePath111: " + filePath);
        filePath = System.getProperty("src/main/assets/Android/SdkData.json");
        Log.i(TAG, "filePath: " + filePath);
        try{
            InputStream is = application.getAssets().open("Android/SdkData.json");
            int lenght = is.available();
            byte[] buffer = new byte[lenght];
            is.read(buffer);
            String result = new String(buffer,"utf8");
            Log.i(TAG, "result: " + result);
            ToJson(result);
        }catch (IOException e){
            Log.i(TAG, "ReadJsonFile: " + e.toString());
            e.printStackTrace();
        }
//        try{
//            File jsonFile = new File(filePath);
//            FileReader fileReader = new FileReader(jsonFile);
//            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
//            int ch =0;
//            StringBuffer sb = new StringBuffer();
//            while ((ch = reader.read()) !=-1){
//                sb.append((char)ch);
//            }
//            fileReader.close();
//            reader.close();
//            jsonStr = sb.toString();
//            return  jsonStr;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return  null;
//        }
    }
    //读取文本
    public  static  void ReadJsonFile(Activity activity){
        String jsonStr = "";
        String filePath = "";
        File directory = new File("");//参数为空
        Log.i(TAG, "filePath111: " + filePath);
        filePath = System.getProperty("src/main/assets/Android/SdkData.json");
        Log.i(TAG, "filePath: " + filePath);
        try{
            InputStream is = activity.getAssets().open("Android/SdkData.json");
            int lenght = is.available();
            byte[] buffer = new byte[lenght];
            is.read(buffer);
            String result = new String(buffer,"utf8");
            Log.i(TAG, "result: " + result);
            ToJson(result);
        }catch (IOException e){
            Log.i(TAG, "ReadJsonFile: " + e.toString());
            e.printStackTrace();
        }
//        try{
//            File jsonFile = new File(filePath);
//            FileReader fileReader = new FileReader(jsonFile);
//            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
//            int ch =0;
//            StringBuffer sb = new StringBuffer();
//            while ((ch = reader.read()) !=-1){
//                sb.append((char)ch);
//            }
//            fileReader.close();
//            reader.close();
//            jsonStr = sb.toString();
//            return  jsonStr;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return  null;
//        }
    }
    //解析json
    public  static  void  ToJson(String s){
        if(s != null && s.length() !=0){
            try {
                JSONObject jobj = new JSONObject(s);
                setHwads_projectId(jobj.getString("hwads_projectId"));
                setHwads_AppToken(jobj.getString("hwads_AppToken"));
                setHwads_ImportantToken(jobj.getString("hwads_ImportantToken"));
                setHwads_MonetizationToken(jobj.getString("hwads_MonetizationToken"));
                setHwads_UACToken(jobj.getString("hwads_UACToken"));
                setGa_GameKey(jobj.getString("ga_GameKey"));
                setGa_gameSecret(jobj.getString("ga_gameSecret"));
                setGa_buildId(jobj.getString("ga_buildId"));
                setDp_appID(jobj.getString("dp_appID"));
                setDp_appName(jobj.getString("dp_appName"));
                setDp_channel(jobj.getString("dp_channel"));
                setDp_serviceVendor(jobj.getString("dp_serviceVendor"));
                setBugly_appID(jobj.getString("bugly_appID"));
            }catch (Exception e){
                //Log.i(TAG, "TAEventPropertie: " + e.toString());
                e.printStackTrace();
            }
        }
    }

}
