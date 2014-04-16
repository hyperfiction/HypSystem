package hypsystem.system;

#if flash
import haxe.crypto.Md5;
import flash.net.SharedObject;
#end

@:build(ShortCuts.mirrors())
class Device
{

	/*
	Return a install wise UUID
	
	@return the uuid
	*/
	@JNI
	@IOS("hyp-system","hypsystem_device_getUuid")
	public static function getUuid():String
	{
		var result:String = null;
		var so = SharedObject.getLocal("hypsystem-device");
		if(so.data.uuid != null)
		{
			result = so.data.uuid;
		}
		else
		{
			var date = Date.now().getTime();
			var random = Std.int(Math.random() * 424242);
			result = "::FLASH::"+Md5.encode('::UNIQUE::.$date.$random');
			so.data.uuid = result;
			so.flush();
		}

		return result;
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
	@IOS("hyp-system","hypsystem_device_isTablet")
	public static function isTablet():Bool
	{
		return false;
	}

	@JNI
	@IOS("hyp-system","hypsystem_device_getScreenHeight")
	public static function getScreenHeight():Int
	{
		return flash.Lib.current.stage.stageHeight;
	}

	@JNI
	@IOS("hyp-system","hypsystem_device_getScreenWidth")
	public static function getScreenWidth():Int
	{
		return flash.Lib.current.stage.stageWidth;
	}

	@JNI
	@IOS("hyp-system","hypsystem_device_getScaleFactor")
	public static function getScaleFactor():Float
	{
		return 1.0;
	}

	@JNI
	@IOS("hyp-system","hypsystem_device_getSystemVersion")
	public static function getSystemVersion():String
	{
		return "x.0";
	}

}