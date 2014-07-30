package hypsystem.system;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import fr.hypsystem.HypSystemExtension.R;

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
		int result;
		if (res.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) 
			result = Math.min(metrics.widthPixels, metrics.heightPixels);
		else
			result = Math.max(metrics.widthPixels, metrics.heightPixels);
		return result;
	}

	static public int getScreenWidth()
	{
		DisplayMetrics metrics = Android.getMetrics();
		Resources res = HypSystem.mainContext.getResources();
		int result;
		if (res.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) 
			result = Math.max(metrics.widthPixels, metrics.heightPixels);
		else
			result = Math.min(metrics.widthPixels, metrics.heightPixels);

		return result;
	}

	static public boolean isTablet()
	{
		boolean deviceIsTablet = HypSystem.mainActivity.getResources().getBoolean(R.bool.isTablet);
        return deviceIsTablet;
	}

    public static float getScreenInchesSize(double screen_size)
    {
        DisplayMetrics displayMetrics = Android.getMetrics();

        double density = displayMetrics.density * 160;
        double x = Math.pow(displayMetrics.widthPixels / density, 2);
        double y = Math.pow(displayMetrics.heightPixels / density, 2);
        double screenInches = Math.round(Math.sqrt(x + y));

        return (float)screenInches;
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