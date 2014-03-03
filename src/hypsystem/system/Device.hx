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

	#end

}