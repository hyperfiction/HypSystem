#include "NetworkInfos.h"
#import "Reachability.h"
#import <UIKit/UIKit.h>

extern "C"
{
	void hypsystem_networkinterface_onUpdate();
}

@interface NetworkInfos:NSObject

@property (retain, nonatomic)  Reachability* reach;

+(NetworkInfos *) getInstance;

-(bool)isConnected;
-(void)listen;
-(void)statusUpdated:(NSNotification*)notification;
-(int)getActiveConnectionType;
@end

@implementation NetworkInfos

@synthesize reach;

+(NetworkInfos *)getInstance
{
	static NetworkInfos *instance;
	@synchronized(self)
	{
		if(!instance)
		{
			instance = [[NetworkInfos alloc] init];			
		}
		return instance;
	}
}

-(id)init
{
	if( self == [super init])
	{
		self.reach = [Reachability reachabilityForInternetConnection]; //retain reach	
	}
	return self;
}

-(NetworkStatus)getStatus
{
	NetworkStatus networkStatus = [self.reach currentReachabilityStatus];  
	return networkStatus;
}

-(bool)isConnected
{	
	NetworkStatus networkStatus = [self getStatus];
	return networkStatus != NotReachable;
}

-(bool)isWifi
{
	NetworkStatus networkStatus = [self getStatus];
	return networkStatus == ReachableViaWiFi;
}

-(void)listen
{
	[[NSNotificationCenter defaultCenter] addObserver:self
		selector:@selector(statusUpdated:) name:kReachabilityChangedNotification 
		object:nil];
	[self.reach startNotifier];
}

-(void)statusUpdated:(NSNotification*)notification
{	
	hypsystem_networkinterface_onUpdate();
}

-(int)getActiveConnectionType
{
	int res;
	NetworkStatus networkStatus = [self getStatus];
	switch(networkStatus)
	{
		case ReachableViaWWAN:
			res = 2;
			break;
			
		case ReachableViaWiFi:
			res = 1;
			break;

		case NotReachable:
			res = 0;
			break;
	}
	return res;
}

@end

namespace networkinfos
{

	void listen()
	{
		[[NetworkInfos getInstance] listen];
	}

	bool isConnected()
	{
		bool result = [[NetworkInfos getInstance] isConnected];
		return result;
	}

	int getActiveConnectionType()
	{
		int result = [[NetworkInfos getInstance] getActiveConnectionType];
		return result;
	}

	bool isWifi()
	{	
		bool result = [[NetworkInfos getInstance] isWifi];
		return result;
	}

}
