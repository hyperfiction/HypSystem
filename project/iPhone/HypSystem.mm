/**
 * ...
 * @author shoe[box]
 */
#include <HypSystem.h>
#import <UIKit/UIKit.h>
#include <netinet/in.h>
#import <SystemConfiguration/SCNetworkReachability.h>
#import <SystemConfiguration/SystemConfiguration.h>
namespace hypsystem{

	bool isConnectedtoInternet( ){
		// Create zero addy
		 // Create zero addy
		 struct sockaddr_in zeroAddress;
		 bzero(&zeroAddress, sizeof(zeroAddress));
		 zeroAddress.sin_len = sizeof(zeroAddress);
		 zeroAddress.sin_family = AF_INET;

		 // Recover reachability flags
		 SCNetworkReachabilityRef defaultRouteReachability = SCNetworkReachabilityCreateWithAddress(NULL, (struct sockaddr *)&zeroAddress);
		 SCNetworkReachabilityFlags flags;

		 BOOL didRetrieveFlags = SCNetworkReachabilityGetFlags(defaultRouteReachability, &flags);
		 CFRelease(defaultRouteReachability);

		 if (!didRetrieveFlags){
		     printf("Error. Could not recover network reachability flags\n");
		     return 0;
		 }

		 BOOL isReachable = flags & kSCNetworkFlagsReachable;
		 BOOL needsConnection = flags & kSCNetworkFlagsConnectionRequired;
		 return (isReachable && !needsConnection) ? YES : NO;

	}

	bool isiPhone( ){
		return (UI_USER_INTERFACE_IDIOM()==UIUserInterfaceIdiomPhone);
	}

	int get_screen_width( ){
		// UIScreen *MainScreen = [UIScreen mainScreen];
		// UIScreenMode *ScreenMode = [MainScreen currentMode];
		// CGSize Size = [ScreenMode size]; // <--- Real screen size
		// return Size.width;
		CGFloat fScl = [[UIScreen mainScreen] scale];
		return [[UIScreen mainScreen] bounds].size.width * fScl;
	}

	int get_screen_height( ){
		// UIScreen *MainScreen = [UIScreen mainScreen];
		// UIScreenMode *ScreenMode = [MainScreen currentMode];
		// CGSize Size = [ScreenMode size]; // <--- Real screen size
		// return Size.height;
		CGFloat fScl = [[UIScreen mainScreen] scale];
		return [[UIScreen mainScreen] bounds].size.height * fScl;
	}

	const char* get_uuid( ) {
		//Generate get a UUID
		NSString *bundleName = [[[NSBundle mainBundle] infoDictionary] objectForKey:@"CFBundleName"];
		NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
		NSString *identifierString = [defaults objectForKey:bundleName];

		if( identifierString == nil ) {
			// Generate UDID
			CFUUIDRef identifierObject = CFUUIDCreate(kCFAllocatorDefault);

			// Convert the CFUUID to a string
			NSString *identifierString = (__bridge_transfer NSString *)CFUUIDCreateString(kCFAllocatorDefault, identifierObject);
			CFRelease((CFTypeRef) identifierObject);

			NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
			[defaults setObject:identifierString forKey:bundleName];
			[defaults synchronize];
		}
		return [identifierString UTF8String];
	}

	const char* get_system_lang( ){
		return [[[NSLocale preferredLanguages] objectAtIndex:0] UTF8String];
	}

}