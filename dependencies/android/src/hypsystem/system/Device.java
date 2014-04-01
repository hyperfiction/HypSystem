package hypsystem.system;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.Locale;
import java.util.UUID;

import hypsystem.HypSystem;
import hypsystem.system.platform.Android;

public class Device
{

	public static String getUuid()
	{
		String UNIQUE_ID = "::APP_PACKAGE::.unique";

		Activity activiy = HypSystem.mainActivity;

		SharedPreferences sharedPrefs = activiy.getSharedPreferences(
			UNIQUE_ID, Context.MODE_PRIVATE);

		String uniqueID = sharedPrefs.getString(UNIQUE_ID, null);

		if (uniqueID == null)
		{
			uniqueID = UUID.randomUUID().toString();

			Editor 	editor = sharedPrefs.edit();
					editor.putString(UNIQUE_ID, uniqueID);
					editor.commit();
		}

		return uniqueID;
	}

	public static String getLanguageCode()
	{
		return Locale.getDefault().getLanguage();
	}


	static public int getScreenHeight()
	{
		return Android.getMetrics().heightPixels;
	}

	static public int getScreenWidth()
	{
		return Android.getMetrics().widthPixels;
	}

	static public boolean isTablet()
	{
		boolean result = false;

		Resources resources = HypSystem.mainContext.getResources();
		boolean xLarge = ((resources.getConfiguration().screenLayout & 
			Configuration.SCREENLAYOUT_SIZE_MASK) == 
			Configuration.SCREENLAYOUT_SIZE_XLARGE);

		if (xLarge)
		{
			DisplayMetrics metrics = Android.getMetrics();

			if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT
				|| metrics.densityDpi == DisplayMetrics.DENSITY_HIGH
				|| metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM
				|| metrics.densityDpi == DisplayMetrics.DENSITY_TV
				|| metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH)
			{
				result = true;
			}
		}

		return result;
	}

	static Resources getRessource()
	{
		return HypSystem.mainContext.getResources();
	}
}