package hypsystem.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import hypsystem.HypSystem;
import java.lang.Exception;
import java.lang.InterruptedException;
import java.lang.Runnable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class NetworkInfos
{
	static FutureTask<Integer> futureGetNetworkType = new FutureTask<Integer>(
		new Callable<Integer>()
		{
			@Override public Integer call() throws Exception
			{
				int result = 0;
				if (NetworkInfos.isConnected())
				{
					NetworkInfo activeNetwork = NetworkInfos.getNetworkInfo();
					if (activeNetwork != null)
					{
						int type = activeNetwork.getType();
						switch (type)
						{
							case ConnectivityManager.TYPE_WIFI:
								result = 1;
								break;

							case ConnectivityManager.TYPE_WIMAX:
								result = 1;
								break;

							default:
								result = 2;
								break;
						}
					}
				}

				return result;
			}
		}
	);

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
		boolean res = false;

		try
		{
			NetworkInfo activeNetwork = getNetworkInfo();
			
			res = activeNetwork != null && 
				activeNetwork.isConnectedOrConnecting();
		}
		catch (Exception exception)
		{
			exception.printStackTrace();		
		}
		return res;
	}

	static public int getActiveConnectionType()
	{
		int res = 0;
		HypSystem.mainActivity.runOnUiThread(futureGetNetworkType);
		try
		{
			res = futureGetNetworkType.get();
		}
		catch (InterruptedException | ExecutionException exception)
		{
			exception.printStackTrace();
		}
		return res;
	}

	static boolean isWifi()
	{
		boolean res = false;

		if(isConnected())
		{
			NetworkInfo activeNetwork = getNetworkInfo();
			if (activeNetwork != null)
			{
				res = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
			}
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
		NetworkInfo result = null;
		if (cm != null)
		{
			result = cm.getActiveNetworkInfo();
		}
		return result;
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


