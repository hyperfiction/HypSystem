package hypsystem.system;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.TimeZone;
import java.util.Date;

public class DateTools
{

	public static float fromISOStringToMs(String iso)
	{
		Date result = null;
		try
		{
			SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy-MM-dd'T'HH:mm:ssXXX");
			result = formatter.parse(iso);
		}
		catch ( ParseException e )
		{

		}

		Float time = result == null ? -1 : (float)result.getTime();
		return time;
	}	

	public static String timestampToIsoString(float timestamp)
	{
		Date date = new Date((long)timestamp);
		TimeZone tz = TimeZone.getTimeZone("UTC");
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
	    df.setTimeZone(tz);
    	String dateAsIso = df.format(date);
    	return dateAsIso;
	}

}
