package hypsystem.system.platform;

import android.app.Activity;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import hypsystem.HypSystem;
import hypsystem.system.Device;

import java.util.UUID;

public class Android
{
	public static String getUDID()
	{
		String androidId = "" +Settings.Secure.getString(
			HypSystem.mainContext.getContentResolver(), 
			Settings.Secure.ANDROID_ID);

		String serial = Build.SERIAL;

		androidId +=  serial;

		byte[] b = androidId.getBytes();

		UUID deviceUuid = UUID.nameUUIDFromBytes(b);
		
		String uniqueId = deviceUuid.toString();
		return uniqueId;
	}

	public static DisplayMetrics getMetrics()
	{
		Activity mainActivity = HypSystem.mainActivity;
		WindowManager wm = mainActivity.getWindowManager();
		Display d = wm.getDefaultDisplay();

		DisplayMetrics dm = new DisplayMetrics();
		d.getMetrics(dm);

		return dm;
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

	public static int dpToPx(int dp)
	{
		DisplayMetrics metrics = getMetrics();
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, 
			metrics);
		return (int)px;
	}

	public static float getDensity()
	{
		DisplayMetrics metrics = getMetrics();
		return metrics.density;
	}

	public static boolean isFirebaseTestLab()
	{
		String testLabSetting = Settings.System.getString(HypSystem.mainContext.getContentResolver(), "firebase.test.lab");
		if ("true".equals(testLabSetting)) {
			return true;
		}
		return false;
	}

}