#include <Device.h>

//#import <Foundation/NSTimeZone.h>
#import <SystemConfiguration/SystemConfiguration.h>
#import <UIKit/UIKit.h>
//#include <netinet/in.h>

namespace device
{
	bool isTablet()
	{
		return [UIDevice currentDevice].userInterfaceIdiom == UIUserInterfaceIdiomPad;
	}

	const char* getUuid()
	{
		NSString *bundleName = [[[NSBundle mainBundle] infoDictionary] 
			objectForKey:@"CFBundleIdentifier"];

		bundleName = [bundleName stringByAppendingString:@".unique"];

		NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
		NSString *identifierString = [defaults objectForKey:bundleName];

		if(identifierString == nil )
		{
			CFUUIDRef identifierObject = CFUUIDCreate(kCFAllocatorDefault);

			identifierString = (__bridge_transfer NSString *)CFUUIDCreateString(
				kCFAllocatorDefault, identifierObject);

			CFRelease((CFTypeRef) identifierObject);

			NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
			[defaults setObject:identifierString forKey:bundleName];
			[defaults synchronize];
		}
		return [identifierString UTF8String];
	}

	const char* getLangCode()
	{
		return [[[NSLocale preferredLanguages] objectAtIndex:0] UTF8String];
	}

	float getScaleFactor()
	{
		float scale = [UIScreen mainScreen].scale;
		return scale;
	}

	int getScreenHeight()
	{
		CGRect bounds = [[UIScreen mainScreen] bounds];
		return bounds.size.height;
	}

	int getScreenWidth()
	{
		CGRect bounds = [[UIScreen mainScreen] bounds];
		return bounds.size.width;
	}
}