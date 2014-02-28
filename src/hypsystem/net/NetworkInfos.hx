package hypsystem.net;

@:build(ShortCuts.mirrors())
class NetworkInfos
{

	public static function listen():Void
	{
		setListener(onStatusChanged);
		listenForChanges();
	}

	#if android
	@JNI
	#end
	public static function isConnected():Bool
	{
		return false;
	}

	public static function getConnectionType():ConnectionType
	{

		var type = 0;

		#if android
			type = getActiveConnectionType();
		#end

		var res:ConnectionType = switch(type)
		{
			case 0:
				ConnectionType.NONE;

			case 1:
				ConnectionType.WIFI;

			case 2:
				ConnectionType.MOBILE;

			case _:

		}

		return res;
	}

	static function onStatusChanged()
	{
		trace("onStatusChanged ::: ");
	}

	#if android
	@JNI
	#end
	public static function isWifi():Bool
	{
		return false;
	}

	#if android
	@JNI
	#end
	static function getActiveConnectionType():Int{}
	
	#if android
	@JNI
	#end
	static function listenForChanges():Void{}

	#if(android || ios)
	@CPP("hypsystem","hypsystem_setEventListener")
	#end
	static function setListener(f:Void->Void):Void{}

}

enum ConnectionType
{
	NONE;
	WIFI;
	MOBILE;
}
