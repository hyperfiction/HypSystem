package hypsystem.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import hypsystem.HypSystem;

import java.lang.Runnable;

public class NetworkInfos
{

	static public native void onUpdate();
	static
	{
		System.loadLibrary("hypsystem");
	}

	static ConnectivityChangeReceiver receiver = new ConnectivityChangeReceiver();

	static public void listenForChanges()
	{
		IntentFilter filter = new IntentFilter(
			ConnectivityManager.CONNECTIVITY_ACTION);

		Context context = HypSystem.mainContext;
				context.registerReceiver(receiver, filter);
	}

	static public boolean isConnected()
	{
		NetworkInfo activeNetwork = getNetworkInfo();
		
		boolean res = activeNetwork != null && 
			activeNetwork.isConnectedOrConnecting();

		return res;
	}

	static public int getActiveConnectionType()
	{
		int res = 0;
		if(isConnected())
		{
			NetworkInfo activeNetwork = getNetworkInfo();			
			int type = activeNetwork.getType();

			if(type == ConnectivityManager.TYPE_WIFI)
				res = 1;
			else if(type == ConnectivityManager.TYPE_WIMAX)
				res = 1;
			else
				res = 2;			
		}
		return res;
	}

	static boolean isWifi()
	{
		boolean res = false;

		if(isConnected())
		{
			NetworkInfo activeNetwork = getNetworkInfo();
			res = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
		}		

		return res;
	}

	static ConnectivityManager getManager()
	{
		Context context = HypSystem.mainContext;

		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(
			Context.CONNECTIVITY_SERVICE);

		return cm;
	}

	static NetworkInfo getNetworkInfo()
	{
		ConnectivityManager cm = getManager();
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

		return activeNetwork;
	}   

	public static void trace( String s )
	{
		HypSystem.trace(s);
	}
}

class ConnectivityChangeReceiver extends BroadcastReceiver
{

	public void onReceive(Context context, Intent intent)
	{
		HypSystem.callbackHandler.post(
			new Runnable()
			{
				@Override
				public void run()
				{
					NetworkInfos.onUpdate();
				}
			}
		);
		
	}

}