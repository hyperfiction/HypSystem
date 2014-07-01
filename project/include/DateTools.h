namespace datetools
{
	float getTimezoneOffset();
	float getUTCDate(float timestamp);
	float getUTCDay(float timestamp);
	float getUTCFullYear(float timestamp);
	float getUTCHours(float timestamp);
	float getUTCMilliseconds(float timestamp);
	float getUTCMinutes(float timestamp);
	float getUTCMonth(float timestamp);
	float getUTCSeconds(float timestamp);
	const char* toISOString(float timestamp, bool gmt);
	const char* toUTCString(float timestamp);
	float fromISO(const char* date);
}
