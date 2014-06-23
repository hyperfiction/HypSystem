package hypsystem.system;

import android.app.Activity;
import android.content.pm.ActivityInfo;

import hypsystem.HypSystem;

public class Orientation
{
	public static final int OrientationPortrait = 1;
	public static final int OrientationPortraitUpsideDown = 2;
	public static final int OrientationLandscapeRight = 3;
	public static final int OrientationLandscapeLeft = 4;
	public static final int OrientationFaceUp = 5;
	public static final int OrientationFaceDown = 6;

	public static void set(int value)
	{
		int translatedValue = translateOpenFlValue(value);
		HypSystem.mainActivity.setRequestedOrientation(translatedValue);
	}

	static int translateOpenFlValue(int value)
	{
		int result = -1;
		switch(value)
		{
			case OrientationPortrait:
				result = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
				break;

			case OrientationPortraitUpsideDown:
				result = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
				break;

			case OrientationLandscapeRight:
				result = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
				break;

			case OrientationLandscapeLeft:
				result = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
				break;
		}

		return result;
	}

}
