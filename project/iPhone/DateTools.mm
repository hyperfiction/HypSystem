#include <DateTools.h>

#import <UIKit/UIKit.h>

#import "ISO8601DateFormatter.h"

namespace datetools
{
	static ISO8601DateFormatter *iso = [[ISO8601DateFormatter alloc] init];

	NSDate* getDateFromTimestamp(float timeStamp)
	{
		NSDate *date = [NSDate dateWithTimeIntervalSince1970:timeStamp / 1000];
		return date;
	}

	float getTimezoneOffset()
	{	
		NSInteger ms = 1000 * [[NSTimeZone localTimeZone] secondsFromGMT];
		return (float)ms;
	}

	NSDateComponents* getComponents(NSDate *date, NSCalendarUnit unit)
	{
		NSCalendar *calendar = [[NSCalendar alloc] 
			initWithCalendarIdentifier:NSGregorianCalendar];

		NSDateComponents *components = [[NSCalendar currentCalendar] 
			components:unit fromDate:date];

		return components;
	}

	float getUTCDate(float timestamp)
	{
		NSDate *date = getDateFromTimestamp(timestamp);
		NSDateComponents *components = getComponents(date, NSDayCalendarUnit);
		return [components day];
	}

	float getUTCDay(float timestamp)
	{
		NSDate *date = getDateFromTimestamp(timestamp);
		NSDateComponents *components = getComponents(date, NSWeekdayCalendarUnit);
		return [components weekday];
	}

	float getUTCFullYear(float timestamp)
	{
		NSDate *date = getDateFromTimestamp(timestamp);
		NSDateComponents *components = getComponents(date, NSYearCalendarUnit);
		return [components year];
	}

	float getUTCHours(float timestamp)
	{
		NSDate *date = getDateFromTimestamp(timestamp);
		NSDateComponents *components = getComponents(date, NSHourCalendarUnit);
		return [components hour];
	}

	float getUTCMilliseconds(float timestamp)
	{
		return 0; //TODO
	}

	float getUTCMinutes(float timestamp)
	{
		NSDate *date = getDateFromTimestamp(timestamp);
		NSDateComponents *components = getComponents(date, NSMinuteCalendarUnit);
		return [components minute];
	}

	float getUTCMonth(float timestamp)
	{
		NSDate *date = getDateFromTimestamp(timestamp);
		NSDateComponents *components = getComponents(date, NSMonthCalendarUnit);
		return [components month];
	}

	float getUTCSeconds(float timestamp)
	{
		NSDate *date = getDateFromTimestamp(timestamp);
		NSDateComponents *components = getComponents(date, NSSecondCalendarUnit);
		return [components second];
	}

	const char* toUTCString(float timestamp)
	{
		NSDate *date = getDateFromTimestamp(timestamp);
		NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];

		[dateFormatter setDateFormat:@"yyyy-MM-dd HH:mm:ss"];
		NSString *datestring = [dateFormatter stringFromDate:date];
		return [datestring UTF8String];
	}

	float fromISO(const char* isodate)
	{
		NSString *raw = [NSString stringWithUTF8String:isodate];
		NSDate *date = [iso dateFromString:raw];
		
		int interval = [date timeIntervalSince1970]; 
		return (float) interval * 1000;
	}

	const char* toISOString(float timestamp, bool gmt)
	{
		NSDate *date = getDateFromTimestamp(timestamp);
		
		iso.includeTime = YES;
		NSString *datestring;
		if(gmt)
			datestring = [iso stringFromDate:date 
				timeZone:[NSTimeZone timeZoneWithName:@"GMT"]];
		else
			datestring = [iso stringFromDate:date];
	
		return [datestring UTF8String];
	}	

}
