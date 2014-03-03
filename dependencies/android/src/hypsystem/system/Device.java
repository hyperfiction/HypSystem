package hypsystem.system;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.util.Locale;
import java.util.UUID;

import hypsystem.HypSystem;

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

	public static String getScreenBucket()
	{
		DisplayMetrics dm = getMetrics();

		String res = "mdpi";

		switch(dm.densityDpi)
		{
			case DisplayMetrics.DENSITY_LOW:
				res = "ldpi";
				break;

			case DisplayMetrics.DENSITY_MEDIUM:
				res = "mdpi";
				break;

			case DisplayMetrics.DENSITY_HIGH:
				res = "hdpi";
				break;

			case DisplayMetrics.DENSITY_XHIGH:
				res = "xhdpi";
				break;

			case DisplayMetrics.DENSITY_XXHIGH:
				res = "xxhdpi";
				break;
		}
		return res;
	}

	static DisplayMetrics getMetrics()
	{
		Activity mainActivity = HypSystem.mainActivity;
		WindowManager wm = mainActivity.getWindowManager();
		Display d = wm.getDefaultDisplay();

		DisplayMetrics dm = new DisplayMetrics();
		d.getMetrics(dm);

		return dm;
	}

}