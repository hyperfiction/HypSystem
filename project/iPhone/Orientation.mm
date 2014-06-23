#include "Orientation.h"
#import <UIKit/UIKit.h>

namespace orientation
{	
	int const OrientationPortrait = 1;
	int const OrientationPortraitUpsideDown = 2;
	int const OrientationLandscapeRight = 3;
	int const OrientationLandscapeLeft = 4;
	int const OrientationFaceUp = 5;
	int const OrientationFaceDown = 6;

	void set(int orientation)
	{
		UIDeviceOrientation	result = UIDeviceOrientationUnknown;
		switch(orientation)
		{
			case OrientationPortrait:
				result = UIDeviceOrientationPortrait;
				break;

			case OrientationPortraitUpsideDown:
				result = UIDeviceOrientationPortraitUpsideDown;
				break;

			case OrientationLandscapeRight:
				result = UIDeviceOrientationLandscapeRight;
				break;

			case OrientationLandscapeLeft:
				result = UIDeviceOrientationLandscapeLeft;
				break;
		}	

		[[UIApplication sharedApplication] setStatusBarOrientation:result animated:NO];
		[[UIDevice currentDevice] setValue:[NSNumber numberWithInteger: result]
			forKey:@"orientation"];
	}
}