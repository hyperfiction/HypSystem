package hypsystem.system.platform;

@:build(ShortCuts.mirrors())
class Android
{
	@JNI
	public static function getScreenBucket():String
	{
		return "mdpi";
	}

	@JNI
	public static function dpToPx(dp:Int):Int
	{
		return dp;
	}
}