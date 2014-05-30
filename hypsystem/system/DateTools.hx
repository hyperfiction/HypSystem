package hypsystem.system;

class DateTools
{

	public static function getTimezoneOffset(date:Date):Float
	{
		return DateNative.getTimezoneOffset();
	}

	static inline function getDateTime(date:Date):Float
	{
		return date.getTime();
	}

	public static function getUTCFullYear(date:Date):Float
	{
		return DateNative.getUTCFullYear(getDateTime(date));
	}

	public static function getUTCMonth(date:Date):Float
	{
		return DateNative.getUTCMonth(getDateTime(date));
	}

	public static function getUTCDay(date:Date):Float
	{
		return DateNative.getUTCDay(getDateTime(date));
	}

	public static function getUTCDate(date:Date):Float
	{
		return DateNative.getUTCDate(getDateTime(date));
	}

	public static function getUTCHours(date:Date):Float
	{
		return DateNative.getUTCHours(getDateTime(date));
	}

	public static function getUTCMinutes(date:Date):Float
	{
		return DateNative.getUTCMinutes(getDateTime(date));
	}

	public static function getUTCSeconds(date:Date):Float
	{
		return DateNative.getUTCSeconds(getDateTime(date));
	}

	public static function getUTCMilliseconds(date:Date):Float
	{
		return DateNative.getUTCMilliseconds(getDateTime(date));
	}

	public static function toUTCString(date:Date):String
	{
		return DateNative.toUTCString(getDateTime(date));
	}

	public static function toISOString(date:Date):String
	{
		return DateNative.toISOString(getDateTime(date));
	}

	public static function fromISO(s:String):Date
	{
		var t = DateNative.fromISO(s);
		return Date.fromTime(t);
	}
}

@:build(ShortCuts.mirrors())
class DateNative
{

	@JNI public static function getTimezoneOffset():Float{}
	@JNI public static function getUTCFullYear(timestamp:Float):Float{}
	@JNI public static function getUTCMonth(timestamp:Float):Float{}
	@JNI public static function getUTCDay(timestamp:Float):Float{}
	@JNI public static function getUTCDate(timestamp:Float):Float{}
	@JNI public static function getUTCHours(timestamp:Float):Float{}
	@JNI public static function getUTCMinutes(timestamp:Float):Float{}
	@JNI public static function getUTCSeconds(timestamp:Float):Float{}
	@JNI public static function getUTCMilliseconds(timestamp:Float):Float{}
	@JNI public static function toUTCString(timestamp:Float):String{}
	@JNI public static function toISOString(timestamp:Float):String{}
	@JNI public static function fromISO(s:String):Float{}

	static function dateFromIsoString(isoString:String):Date
	{
		var time = fromISOStringToMs(isoString);
		if(time == -1)
			throw "Invalid ISO date format or incompatible target";

		return Date.fromTime(time);
	}

	static function dateToIsoString(date:Date):String
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
