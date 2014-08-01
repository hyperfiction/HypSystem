package hypsystem.system;

import hypsystem.HypSystem;

import java.lang.StringBuffer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

class DateTools
{
	static final String DATEFORMAT_UTC = "yyyy-MM-dd HH:mm:ss";
	static final String DATEFORMAT_ISO = "yyyy-MM-dd'T'HH:mm:ssZ";
	static final SimpleDateFormat ISOFORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");

	public static float getTimezoneOffset()
	{
		TimeZone tz = TimeZone.getDefault();
		Date now = new Date();
		float offsetFromUtc = tz.getOffset(now.getTime()) / 1000;
		return offsetFromUtc;
	}

	public static float getUTCFullYear(float timestamp)
	{
		return getUTC(getDate(timestamp)).get(Calendar.YEAR);
	}

	public static float getUTCMonth(float timestamp)
	{
		return getUTC(getDate(timestamp)).get(Calendar.MONTH);
	}

	public static float getUTCDay(float timestamp)
	{
		return getUTC(getDate(timestamp)).get(Calendar.DAY_OF_WEEK);
	}

	public static float getUTCDate(float timestamp)
	{
		return getUTC(getDate(timestamp)).get(Calendar.DAY_OF_MONTH);
	}

	public static float getUTCHours(float timestamp)
	{
		return getUTC(getDate(timestamp)).get(Calendar.HOUR_OF_DAY);
	}

	public static float getUTCMinutes(float timestamp)
	{
		return getUTC(getDate(timestamp)).get(Calendar.MINUTE);
	}

	public static float getUTCSeconds(float timestamp)
	{
		return getUTC(getDate(timestamp)).get(Calendar.SECOND);
	}

	public static float getUTCMilliseconds(float timestamp)
	{
		return getUTC(getDate(timestamp)).get(Calendar.MILLISECOND);
	}

	public static String toUTCString(float timestamp)
	{
		Calendar utcCalendar = getUTC(getDate(timestamp));
		DateFormat date = new SimpleDateFormat(DATEFORMAT_UTC);   
		date.setTimeZone(TimeZone.getTimeZone("GMT")); 
		String utc = date.format(utcCalendar.getTime()); 
		return utc;
	}

	public static String toISOString(float timestamp, boolean gmt)
	{
		Date date = new Date((long)timestamp);

		DateTime dateTime = new DateTime(date);
		String ios = dateTime.toString(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z"));
		return ios;
	}

	static String toIndice(final int value)
	{
		if (value > 9)	
			return String.valueOf(value);
		else
			return "0" + value;
	}

	static Calendar getUTC(Date from)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
		calendar.setTime(from);
		return calendar;
	}

	static float fromISO(String iso)
	{
		Date date = null;

		if(iso.indexOf('Z') != -1)
		{
			JodaTimeAndroid.init(HypSystem.mainContext);
			DateTimeFormatter formatter = ISODateTimeFormat.dateTimeParser();
			LocalDateTime result = formatter.parseLocalDateTime(iso);	
			date = result.toDate();
		}
		else
		{
			try
			{
				date = ISOFORMATTER.parse(iso);
			}
			catch (java.text.ParseException error)
			{

			}
		}
		
		if(date == null)
			return 0;

		return (float)date.getTime();
	}

	static Date getDate(float timestamp)
	{
		return new Date((long)timestamp);
	}
}
