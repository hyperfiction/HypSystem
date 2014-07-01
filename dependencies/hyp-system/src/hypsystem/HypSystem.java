package hypsystem;

import android.util.Log;

import org.haxe.extension.Extension;

public class HypSystem extends Extension
{
	
	public static void trace(String s)
	{
		Log.i( TAG, s );
	}	

	private static String TAG = "HypSystem";
}
