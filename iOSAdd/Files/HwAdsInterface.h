//
//  HwAdsInterface_h
//  Unity-iPhone
//
//  Created by game team on 2019/11/15.
//

#ifndef HwAdsInterface_h
#define HwAdsInterface_h


#import <Foundation/Foundation.h>
//#import "HwAds.h"
#import <HwAdsFramework/HwAds.h>
@interface HwAdsInterface : NSObject<HwAdsDelegate>
//add
@property (nonatomic,strong) HwAds *hwAds;
@property BOOL isReward;
@property NSString *rewardTag;
@property NSString *urlResult;
@property NSInteger analyticSwitch;
@property int hwads_projectId;
@property NSString *hwads_AppToken;
@property NSString *hwads_ImportantToken;
@property NSString *hwads_UACToken;
@property NSString *hwads_MonetizationToken;
@property NSString *ga_GameKey;
@property NSString *ga_gameSecret;
@property NSString *ga_buildId;
//dataplayer
@property NSString *dp_appID;
@property NSString *dp_appName;
@property NSString *dp_channel;
@property NSString *dp_serviceVendor;
//bugly
@property NSString *bugly_appID;
+(id) sharedInstance;

//- (void)initHwSDK:(char *)serverURL;
//- (void)loadHwInterAd;
//- (void)showHwInterAd;
//- (BOOL)isHwInterAdLoaded;
//- (void)loadHwRewardAd;
//- (void)showHwRewardAd:(char *)tag;
//- (BOOL)isHwRewardAdLoaded;
//- (void)hwFbEvent:(char *)category
//           action:(char *)action
//            label:(char *)label;
-(void) initMHSDK;
-(void) loadMHInterAd;
-(void) showMHInterAd;
-(BOOL) isMHInterAdLoaded;
-(void) loadMHRewardAd;
-(void) showMHRewardAd:(NSString *)tag;
-(BOOL) isMHRewardAdLoaded;
-(void) loadUrl:(int)projectId;
-(NSInteger) getAnalyticSwitch;
-(void) loadSdkData;
-(int) getHwads_projectId;
-(NSString*) getHwads_AppToken;
-(NSString*) getHwads_ImportantToken;
-(NSString*) getHwads_UACToken;
-(NSString*) getHwads_MonetizationToken;
-(NSString*) getGA_GameKey;
-(NSString*) getGA_gameSecret;
//dataplayer
-(NSString*) getDP_appID;
-(NSString*) getDP_appName;
-(NSString*) getDP_channel;
-(NSString*) getDP_serviceVendor;
-(NSString*) getBugly_appID;
@end









#endif /* HwAdsCall_h */

//@interface HwAdsInterface : NSObject<HwAdsDelegate>
//@property (nonatomic, strong) HwAds *hwAdsDelegate;
//
//- (void)initTest;
//@end


