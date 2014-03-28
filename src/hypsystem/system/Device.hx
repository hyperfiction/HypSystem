package hypsystem.system;

@:build(ShortCuts.mirrors())
class Device
{

	@JNI
	@IOS("hyp-system","hypsystem_device_getUuid")
	public static function getUuid():String
	{
		return null;
	}

	@JNI
	@IOS("hyp-system","hypsystem_device_getLangCode")
	public static function getLanguageCode():String
	{
		return null;
	}

	#if android

	@JNI
	public static function getScreenBucket():String
	{

	}

	@JNI
	public static function dpToPx(dp:Int):Int
	{
		return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
	}

	@JNI
	public static function getDensity():Float
	{
		return 1.0;
	}

	@JNI
	public static function getScreenHeight():Int
	{
		return flash.Lib.current.stage.stageHeight;
	}

	@JNI
	public static function getScreenWidth():Int
	{
		return flash.Lib.current.stage.stageHeight;
	}

	#end

}