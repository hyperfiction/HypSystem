package hypsystem.system;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.util.Locale;
import java.util.UUID;

import hypsystem.HypSystem;
import hypsystem.system.platform.Android;

public class Device
{
	public static String getName()
	{
		return Build.MODEL;
	}

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

	public static String getSystemVersion()
	{
		return Build.VERSION.RELEASE;
	}

	static public int getScreenHeight()
	{
		DisplayMetrics metrics = Android.getMetrics();
		Resources res = HypSystem.mainContext.getResources();
		return Math.min(metrics.widthPixels, metrics.heightPixels) ;
	}

	static public int getScreenWidth()
	{
		DisplayMetrics metrics = Android.getMetrics();
		Resources res = HypSystem.mainContext.getResources();
		return Math.max(metrics.widthPixels, metrics.heightPixels);
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

	static float getScaleFactor()
	{
		return Android.dpToPx(1);
	}
}