#import "UnityAppController.h"
#import <GameAnalytics/GameAnalytics.h>
#import "BDAutoTrack.h"
#import "HwAdsInterface.h"
#import <Bugly/Bugly.h>
 
@interface CustomAppController : UnityAppController
@end
 
IMPL_APP_CONTROLLER_SUBCLASS (CustomAppController)
 
@implementation CustomAppController
 
- (BOOL)application:(UIApplication*)application didFinishLaunchingWithOptions:(NSDictionary*)launchOptions
{
    [super application:application didFinishLaunchingWithOptions:launchOptions];
    [[HwAdsInterface sharedInstance] loadSdkData];
    HwAdsInterface *instance = [HwAdsInterface sharedInstance];
    int projectId = [instance getHwads_projectId];
    [instance loadUrl:projectId];
    if([instance analyticSwitch]==1){
        //dataplayer初始化
        /* 初始化开始 */
        BDAutoTrackConfig *config = [BDAutoTrackConfig new];

          /* 域名默认国内: BDAutoTrackServiceVendorCN */
        NSString *_appID = [instance getDP_appID];
        NSString *_appName = [instance getDP_appName];
        NSString *_channel = [instance getDP_channel];
        NSString *_serviceVendor = [instance getDP_serviceVendor];
        if([_serviceVendor containsString:@"CN"] || [_serviceVendor containsString:@"cn"]){
            config.serviceVendor = BDAutoTrackServiceVendorCN;//中国
        }
        else if([_serviceVendor containsString:@"SG"] || [_serviceVendor containsString:@"sg"]){
            config.serviceVendor = BDAutoTrackServiceVendorSG;//新加坡
        }
        else if([_serviceVendor containsString:@"VA"] || [_serviceVendor containsString:@"va"]){
            config.serviceVendor = BDAutoTrackServiceVendorVA;//美东
        }
        else {
            config.serviceVendor = BDAutoTrackServiceVendorCN;//中国
        }
        config.appID = _appID; // 如不清楚请联系专属客户成功经理
        config.appName = _appName; // 与您申请APPID时的app_name一致
        config.channel = _channel; // iOS一般默认App Store
        config.showDebugLog = NO; // 是否在控制台输出日志，仅调试使用。release版本请设置为 NO
        config.logNeedEncrypt = YES; // 是否加密日志，默认加密。release版本请设置为 YES
        config.gameModeEnable = YES; // 是否开启游戏模式，游戏APP建议设置为 YES
        [BDAutoTrack startTrackWithConfig:config];
        /* 初始化结束 */
    }
    else if([instance analyticSwitch]==2){
        
    }
    NSString *ga_GameKey = [instance getGA_GameKey];
    NSString *ga_GameSecret = [instance getGA_gameSecret];
    /*GA初始化*/
    [GameAnalytics initializeWithGameKey:ga_GameKey gameSecret:ga_GameSecret];
    /*bugly初始化*/
    NSString *buglyAppId = [instance getBugly_appID];
    [Bugly startWithAppId:buglyAppId];
    return YES;
}
 
@end
