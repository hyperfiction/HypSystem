package hypsystem.system;

@:build(ShortCuts.mirrors())
class DateTools
{

	public static function dateFromIsoString(isoString:String):Date
	{
		var time = fromISOStringToMs(isoString);
		if(time == -1)
			throw "Invalid ISO date format or incompatible target";

		return Date.fromTime(time);
	}

	public static function dateToIsoString(date:Date):String
	{
		var timestamp = date.getTime();
		return timestampToIsoString(timestamp);
	}

	@JNI
	static function fromISOStringToMs(isoString:String):Float
	{
		return -1;
	}

	@JNI
	static function timestampToIsoString(timestamp:Float):String
	{
		return '';
	}

}
