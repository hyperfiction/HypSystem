package hypsystem.system;

#if !(android || ios)
import haxe.crypto.Md5;
import flash.net.SharedObject;
#end

@:build(ShortCuts.mirrors())
@CPP_DEFAULT_LIBRARY("hyp-system")
@CPP_PRIMITIVE_PREFIX("hypsystem_device")
class Device
{

	@JNI @IOS public static function getName():String
	{
		return null;
	}

	/*
	Return a install wise UUID
	
	@return the uuid
	*/
	@JNI @IOS public static function getUuid():String
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
			var s = Md5.encode('::FLASH::.$date.$random'); 		
			
			var a = ["F1A5" + s.substr(4, 4)];
			for (i in 0...3)
				a.push(s.substr(i * 4 + 8, 4));
				a.push(s.substr(20, 12));
				
			result = a.join("-");
			result = result.toUpperCase();
			
			so.data.uuid = result;
			so.flush();
		}

		return result;
	}

	@JNI
	@IOS("hyp-system","hypsystem_device_getLangCode")
	public static function getLanguageCode():String
	{
		return flash.system.Capabilities.language;
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