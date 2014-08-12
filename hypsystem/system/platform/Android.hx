package hypsystem.system.platform;

@:build(ShortCuts.mirrors())
class Android
{
	/**
	Allow to retrieve the device screenbuck name (mdpi,hdpi....)

	@return The screen bucket name (String)
	**/
	@JNI public static function getScreenBucket():String
	{
		return "mdpi";
	}

	/**
	Allow to scale a DP value by device density

	@param dp : The value to be scale in DP (Int)
	@return The scale value
	**/
	@JNI public static function dpToPx(dp:Int):Int
	{
		return dp;
	}

	/**
	Allow to generate device UDID from the Android Build number and device type.

	The generate UDID *should* be unique, but keep in mind than several chinese 
	android device have the same Build number due to a fabrication error.

	@returns The generate UDID (String)
	**/
	@JNI public static function getUDID():String
	{
		return "";
	}
}