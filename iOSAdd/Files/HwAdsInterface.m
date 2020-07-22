//
//  HwAdsInterface.m
//  Unity-iPhone
//
//  Created by game team on 2019/11/15.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>
//#import <HwAdsFramework/HwAds.h>

#import "HwAdsInterface.h"
#import <ThinkingSDK/ThinkingAnalyticsSDK.h>
#import "BDAutoTrack.h"


@implementation HwAdsInterface
typedef void (*CallbackDelegate)(const char *object);
CallbackDelegate callback;


static HwAdsInterface *hwAdsInterfaceInstance;
+ (id) sharedInstance{
    if(hwAdsInterfaceInstance == nil){
        NSLog(@"shareInstance");
        hwAdsInterfaceInstance = [[self alloc] init];
    }
    return hwAdsInterfaceInstance;
}

#pragma mark HwAdsDelegate
- (void) hwAdsRewardedVideoLoadSuccess{
    NSLog(@"call hwAdsRewardedVideoLoadSuccess");
}

- (void) hwAdsRewardedVideoGiveReward{
    NSLog(@"call hwAdsRewardedVideoGiveReward");
    self.isReward = true;
}
- (void) hwAdsRewardedVideoPlayFail{
    NSLog(@"call hwAdsRewardedVideoPlayFail");
}

- (void) hwAdsRewardedVideoClose{
    NSLog(@"call hwAdsRewardedVideoClose");
    if(self.isReward){
        callback("true");
    }else{
        callback("false");
    }
    self.isReward = false;
    
}
-(void) hwAdsRewardedVideoWillAppear{
    NSLog(@"call hwAdsRewardedVideoWillAppear");
}

- (void) hwAdsRewardedVideoClick{
    NSLog(@"call hwAdsRewardedVideoClick");
}

- (void) hwAdsRewardedVideoLoadFail{
    NSLog(@"call hwAdsRewardedVideoLoadFail");
}

- (void)hwAdsRewardedVideoDidAppear {
    NSLog(@"call hwAdsRewardedVideoDidAppear");
}


//- (void)hwAdsRewardedVideoLoadExpire {
//    NSLog(@"call hwAdsRewardedVideoLoadExpire");
//}




-(void)initMHSDK{
    NSLog(@"initHwSDK");
    HwAdsInterface* hwAdsInterface = [HwAdsInterface sharedInstance];
    //[[HwAds instance] initSDK:serverURL]; //3.1版本之前
    [[HwAds instance] initSDK:self.hwads_projectId hwAppToken:self.hwads_AppToken hwImportantToken:self.hwads_ImportantToken hwUACToken:self.hwads_ImportantToken hwMonetizationToken:self.hwads_MonetizationToken];
    HwAds* hwads = [HwAds instance];
    hwads.hwAdsDelegate = hwAdsInterface;
}

-(void) loadMHInterAd{
    NSLog(@"call loadInterAd");
//    [[HwAds instance] loadInter];
}

-(void) showMHInterAd{
    NSLog(@"call ShowInterAd");
    [[HwAds instance] showInter];
}

-(BOOL) isMHInterAdLoaded{
    NSLog(@"call isInterLoaded");
//    return [[HwAds instance] isInterLoad];
}

-(void) loadMHRewardAd{
    NSLog(@"call loadRewardedVideo");
//    [[HwAds instance] loadReward];
}

-(void) showMHRewardAd:(NSString *)tag{
    NSLog(@"call showRewardedVideo");
    self.rewardTag = tag;
    [[HwAds instance] showReward:tag];
}

-(BOOL) isMHRewardAdLoaded{
    NSLog(@"call isRewardLoaded");
    BOOL t =[[HwAds instance] isRewardLoad];
    NSLog(@"cal isRewardLoaded ====%@",t?@"YES":@"NO");
    return [[HwAds instance] isRewardLoad];
}
-(void) loadUrl:(int)projectId{
    self.analyticSwitch = 2;
    self.urlResult =@"http://console.gamebrain.io/advertise/advlist?appid=";
    self.urlResult =  [[NSString alloc] initWithFormat:@"%@%d",self.urlResult, projectId];
    //1.解析参数
    //创建URL
    NSURL *url=[NSURL URLWithString:_urlResult];
    //设置请求
    NSURLRequest *request=[NSURLRequest requestWithURL:url];
    //接收数据
    NSData *responseData=[NSURLConnection sendSynchronousRequest:request returningResponse:nil error:nil];
    if(responseData == nil){
        return;
    }
    //解析数据
    NSDictionary *hwadsparams = [NSJSONSerialization JSONObjectWithData:responseData options:NSJSONReadingMutableLeaves error:nil];
    //展示结果
    
    NSDictionary *hwadsresult= [hwadsparams objectForKey:@"conf"];
    //解析conf中的参数
    NSLog(@"hw json解析的结果： %@ ",[hwadsresult objectForKey:@"AnalyticSwitch"]);
    NSData *AnalyticSwitchData =[[hwadsresult objectForKey:@"AnalyticSwitch"] dataUsingEncoding:NSUTF8StringEncoding];
    NSString * AnalyticSwitchStr = [[NSString alloc]initWithData:AnalyticSwitchData encoding:NSUTF8StringEncoding];
    self.analyticSwitch =[AnalyticSwitchStr integerValue];
    NSLog(@"analyticSwitch json解析的结果： %ld ",(long)self.analyticSwitch);
}
-(NSInteger) getAnalyticSwitch{
    return  self.analyticSwitch;
}

-(void) loadSdkData{
    NSString *bundlePath = [[NSBundle mainBundle] bundlePath];//获取app路径
    NSLog(@"bundlePath========%@",bundlePath);
    NSString *jsonPath = [bundlePath stringByAppendingFormat:@"/Data/Raw/Ios/SdkData.json"];
    NSLog(@"jsonPath ========%@",jsonPath);
    NSString *content = [NSString stringWithContentsOfFile:jsonPath encoding:NSUTF8StringEncoding error:nil];
    NSLog(@"content=======%@",content);
    NSData *jsonData = [content dataUsingEncoding:NSUTF8StringEncoding];
    NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:jsonData options:NSJSONReadingMutableContainers error:nil];
    NSLog(@"dict=========%@",dict);
    self.hwads_projectId = [dict[@"hwads_projectId"] intValue];
    self.hwads_AppToken = dict[@"hwads_AppToken"];
    self.hwads_ImportantToken = dict[@"hwads_ImportantToken"];
    self.hwads_UACToken = dict[@"hwads_UACToken"];
    self.hwads_MonetizationToken = dict[@"hwads_MonetizationToken"];
    self.ga_GameKey = dict[@"ga_GameKey"];
    self.ga_gameSecret = dict[@"ga_gameSecret"];
    NSLog(@"hwads_projectId:%d=>hwads_AppToken:%@=>hwads_ImportantToken:%@=>hwads_UACToken:%@=>hwads_MonetizationToken:%@=>ga_GameKey:%@=>ga_gameSecret:%@",self.hwads_projectId,self.hwads_AppToken,self.hwads_ImportantToken,self.hwads_UACToken,self.hwads_MonetizationToken,self.ga_GameKey,self.ga_gameSecret);
    //dataplayer
    self.dp_appID = dict[@"dp_appID"];
    self.dp_appName = dict[@"dp_appName"];
    self.dp_channel = dict[@"dp_channel"];
    self.dp_serviceVendor = dict[@"dp_serviceVendor"];
    NSLog(@"dp_appID:%@=>dp_appName:%@=>dp_channel:%@=>dp_serviceVendor:%@",self.dp_appID,self.dp_appName,self.dp_channel,self.dp_serviceVendor);
    //bugly
    self.bugly_appID = dict[@"bugly_appID"];
    NSLog(@"bugly_appID:%@",self.bugly_appID);
}
-(int) getHwads_projectId{
    return self.hwads_projectId;
}
-(NSString*) getHwads_AppToken{
    return  self.hwads_AppToken;
}
-(NSString*) getHwads_UACToken{
    return self.hwads_UACToken;
}
-(NSString*) getHwads_ImportantToken{
    return self.hwads_ImportantToken;
}
-(NSString*) getHwads_MonetizationToken{
    return self.hwads_MonetizationToken;
}
-(NSString*) getGA_GameKey{
    return self.ga_GameKey;
}
-(NSString*) getGA_gameSecret{
    return  self.ga_gameSecret;
}
-(NSString*) getDP_appID{
    return self.dp_appID;
}
-(NSString*) getDP_appName{
    return  self.dp_appName;
}
-(NSString*) getDP_channel{
    return  self.dp_channel;
}
-(NSString*) getDP_serviceVendor{
    return  self.dp_serviceVendor;
}
-(NSString*) getBugly_appID{
    return self.bugly_appID;
}

@end

void initHwAds( char *str ,CallbackDelegate callDelegate){
    NSLog(@"HwAdsInterface complete  111111 %s",str);
    callback = callDelegate;
    //[[HwAdsInterface sharedInstance] loadUrl:self.hwads_projectId];
    [[HwAdsInterface sharedInstance] initMHSDK];
}
void showHwRewardAd(char *tag,CallbackDelegate callDelegate){
    NSLog(@"HwAdsInterface complete  showHwRewardAd ");
    callback = callDelegate;
    NSString *str = [NSString stringWithUTF8String:tag];
    [[HwAdsInterface sharedInstance] showMHRewardAd:str];
}
void showHwInterAd(char *tag,CallbackDelegate callback){
    NSLog(@"HwAdsInterface complete  showHwInterAd ");
    [[HwAdsInterface sharedInstance] showMHInterAd];
}
BOOL isHwRewardLoaded(){
    return [[HwAdsInterface sharedInstance] isMHRewardAdLoaded];
}
BOOL isHwInterLoaded(){
    return [[HwAdsInterface sharedInstance] isMHInterAdLoaded];
}

void TAEventHwPropertie(char *custom, char *str){
    NSLog(@"u3d_parseJson custom: %c", custom);
    NSString *jsonStr = [NSString stringWithCString:str encoding:NSUTF8StringEncoding];
    NSLog(@"u3d_parseJson jsonString: %@", jsonStr);

    NSData *jsonData = [jsonStr dataUsingEncoding:NSUTF8StringEncoding];
    
    NSDictionary *dict = [NSJSONSerialization JSONObjectWithData:jsonData options:NSJSONReadingMutableContainers error:nil];
    NSLog(@"u3d_parseJson dict: %@",dict);
//    NSLog(@"dict - name: %@",dict[@"lev"]);
//    NSLog(@"dict - age: %@",dict[@"login_day"]);
    NSMutableDictionary *dic = [[NSMutableDictionary alloc] initWithDictionary:dict];
    NSLog(@"NSMutableDictionary dict: %@",dic);
//    NSLog(@"NSMutableDictionary - name: %@",dic[@"lev"]);
//    NSLog(@"NSMutableDictionary - age: %@",dic[@"login_day"]);
    if( custom == '\0'){
        custom = "tankclash";
         NSLog(@"u3d_parseJson custom: %c", custom);
    }
    NSString *customKey = [NSString stringWithCString:custom encoding:NSUTF8StringEncoding];
    [[ThinkingAnalyticsSDK sharedInstance] track:customKey properties:dict];
    
    //[BDAutoTrack eventV3:@"tankr2_test" params:@{@"video_title":@"Lady Gaga on Oscar"}];
    if(customKey != nil && jsonStr != nil){
        if([[HwAdsInterface sharedInstance] getAnalyticSwitch] == 1){
            NSLog(@"u3d_parseJson BDAutoTrack: ");
            [BDAutoTrack eventV3:customKey params:dict];
        }
        
    }
}






