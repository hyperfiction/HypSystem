package hypsystem.system;

@:build(ShortCuts.mirrors())
class Device
{

	@JNI
	@IOS("hyp-system","hypsystem_device_getUuid")
	public static function getUuid():String
	{
		return "FLASH-UUID";
	}

	@JNI
	@IOS("hyp-system","hypsystem_device_getLangCode")
	public static function getLanguageCode():String
	{
		#if flash
		return flash.system.Capabilities.language;
		#else
		return "unknow";
		#end
	}

	@JNI
	public static function isTablet():Bool
	{
		return false;
	}

	@JNI
	public static function getScreenHeight():Int
	{
		return flash.Lib.current.stage.stageHeight;
	}

	@JNI
	public static function getScreenWidth():Int
	{
		return flash.Lib.current.stage.stageWidth;
	}

}