package hypsystem.net;

@:build(ShortCuts.mirrors())
class NetworkInfos
{

	public static function listen():Void
	{
		setListener(onStatusChanged);
		listenForChanges();
	}

	@JNI public static function isConnected():Bool
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

	static function onStatusChanged():Void
	{
		trace("onStatusChanged ::: ");
	}

	@JNI public static function isWifi():Bool
	{
		return false;
	}

	@JNI static function getActiveConnectionType():Int{
		return 0;
	}
	
	@JNI static function listenForChanges():Void{}
	

	@CPP("hypsystem","hypsystem_setEventListener")
	static function setListener(f:Void->Void):Void{}

}

enum ConnectionType
{
	NONE;
	WIFI;
	MOBILE;
}
