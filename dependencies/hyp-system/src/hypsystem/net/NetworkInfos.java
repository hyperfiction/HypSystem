package hypsystem.net;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import hypsystem.HypSystem;
import java.lang.Exception;
import java.lang.IllegalArgumentException;
import java.lang.InterruptedException;
import java.lang.Runnable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class NetworkInfos
{
	static public native void onUpdate();
	static
	{
		System.loadLibrary("hypsystem");
	}

	static GetNetworkTypeCallback getTypeCallable = new GetNetworkTypeCallback();
	static class GetNetworkTypeCallback implements Callable<Integer>
	{
		@Override public Integer call() throws Exception
		{
			Integer result = 0;
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

	final static ConnectivityChangeReceiver receiver = new ConnectivityChangeReceiver();
	final static IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

	static boolean receiverIsRegistered;

	static public void listenForChanges()
	{
		Context context = HypSystem.mainContext;
		if (context != null)
		{
			if (!receiverIsRegistered)
			{
				context.registerReceiver(receiver, filter);
				receiverIsRegistered = true;
			}
		}
	}

	static public boolean isConnected()
	{
		boolean result = false;

		try
		{
			NetworkInfo activeNetwork = getNetworkInfo();
			
			result = activeNetwork != null && 
				activeNetwork.isConnectedOrConnecting();
		}
		catch (Exception exception)
		{
			exception.printStackTrace();		
		}
		return result;
	}

	static public int getActiveConnectionType()
	{
		int result = 0;
		FutureTask<Integer> task = new FutureTask<Integer>(getTypeCallable);
		Activity activity = HypSystem.mainActivity;
		if (activity != null)
		{
			activity.runOnUiThread(task);		
			try
			{
				result = task.get(1, TimeUnit.SECONDS);
			}
			catch (InterruptedException | ExecutionException | TimeoutException exception)
    		{	
    			exception.printStackTrace();
    		}
		}
		return result;
	}

	static boolean isWifi()
	{
		boolean result = false;

		if (isConnected())
		{
			NetworkInfo activeNetwork = getNetworkInfo();
			if (activeNetwork != null)
			{
				int type = activeNetwork.getType();
				result = type == ConnectivityManager.TYPE_WIFI;
			}
		}		
		return result;
	}

	static ConnectivityManager getManager()
	{
		Context context = HypSystem.mainContext;
		ConnectivityManager result = null;	
		if (context != null)
		{
			result = (ConnectivityManager)context.getSystemService(
				Context.CONNECTIVITY_SERVICE);
		}
		return result;
	}

	static NetworkInfo getNetworkInfo()
	{
		NetworkInfo result = null;
		ConnectivityManager cm = getManager();
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
		Runnable runnable = new Runnable()
		{
			@Override
			public void run()
			{
				NetworkInfos.onUpdate();
			}
		};
		HypSystem.mainActivity.runOnUiThread(runnable);
	}
}
