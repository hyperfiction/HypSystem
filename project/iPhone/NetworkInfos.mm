#include "NetworkInfos.h"
#import "Reachability.h"
#import <UIKit/UIKit.h>

extern "C"
{
	void hypsystem_networkinterface_onUpdate();
}

@interface NetworkInfos:NSObject

+(NetworkInfos *) getInstance;

-(bool)isConnected;
-(void)listen;
-(void)statusUpdated:(NSNotification*)notification;
-(int)getActiveConnectionType;
@end

@implementation NetworkInfos

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
		
	}
	return self;
}

-(NetworkStatus)getStatus
{
	Reachability *reachability = [Reachability reachabilityForInternetConnection];   
	NetworkStatus networkStatus = [reachability currentReachabilityStatus];  
	return networkStatus;
}

-(bool)isConnected
{	
	NetworkStatus networkStatus = [self getStatus];
	return networkStatus != NotReachable;
}

-(bool)isWifi
{
	NSLog(@"isWifi");
	NetworkStatus networkStatus = [self getStatus];
	return networkStatus == ReachableViaWiFi;
}

-(void)listen
{
	NSLog(@"listen");
	[[NSNotificationCenter defaultCenter] addObserver:self
		selector:@selector(statusUpdated:) name:kReachabilityChangedNotification 
		object:nil];
}

-(void)statusUpdated:(NSNotification*)notification
{	
	NSLog(@"statusUpdated");
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