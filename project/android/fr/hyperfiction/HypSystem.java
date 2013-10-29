package fr.hyperfiction;

import ::APP_PACKAGE::.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ApplicationErrorReport;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION_CODES;
import android.os.Build;
import android.os.Handler;
import android.content.Intent;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ProgressBar;
import android.text.Html;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.provider.Settings.Secure;
import android.content.pm.ActivityInfo;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import android.widget.Toast;
import org.apache.http.conn.util.InetAddressUtils;

import org.haxe.nme.GameActivity;
import org.haxe.nme.HaxeObject;

/**
 * ...
 * @author shoe[box]
 */

class HypSystem{

	private static String TAG = "HypSystem";


	private static LoadingDialog dialog_progress;
	private static SystemUiHider uiHider;

	public static final int TABLET_MIN_DP_WEIGHT = 450;

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		private void HypSystem() {
			Log.i( TAG, " constructor" );
		}

	// -------o public

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public int getGMT_offset( ){

			TimeZone tz = TimeZone.getDefault( );

			int offset = tz.getRawOffset( );
				offset = (offset / (1000 * 60 * 60)) % 24;
			if( tz.useDaylightTime( ) )
				offset += 1;

			return offset;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void toast( final String s ){

			GameActivity.getInstance( ).runOnUiThread(
				new Runnable(){
	                @Override
	                public void run() {
	                	Toast 	toast = Toast.makeText( GameActivity.getContext( ) , s , Toast.LENGTH_SHORT);
								toast.show( );
	                }
		        }
            );


		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getLocal_IP( ){
				try {
					String ipv4;
					List<NetworkInterface> nilist = Collections.list(NetworkInterface.getNetworkInterfaces());
					for (NetworkInterface ni: nilist) {
						List<InetAddress> ialist = Collections.list(ni.getInetAddresses());
						for (InetAddress address: ialist){
							if (!address.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipv4=address.getHostAddress())) {
								return ipv4;
							}
						}
					}

				} catch (SocketException ex) {
				//Log.e(LOG_TAG, ex.toString());
				}
				return null;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public boolean isWifi( ) {
			ConnectivityManager cm = (ConnectivityManager) GameActivity.getContext( ).getSystemService(Context.CONNECTIVITY_SERVICE);
			return cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public boolean isConnected() {

			boolean haveConnectedWifi = false;
		    boolean haveConnectedMobile = false;

		    ConnectivityManager cm = (ConnectivityManager) GameActivity.getContext( ).getSystemService(Context.CONNECTIVITY_SERVICE);
		    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		    for (NetworkInfo ni : netInfo) {
		        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
		            if (ni.isConnected())
		                haveConnectedWifi = true;
		        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
		            if (ni.isConnected())
		                haveConnectedMobile = true;
		    }
		    return haveConnectedWifi || haveConnectedMobile;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public boolean isSmartPhone( ){

		    DisplayMetrics metrics = new DisplayMetrics();
		    GameActivity.getInstance( ).getWindowManager().getDefaultDisplay().getMetrics(metrics);

		    int dpi = 0;
		    if (metrics.widthPixels < metrics.heightPixels){
		        dpi = (int) (metrics.widthPixels / metrics.density);
		    }else{
		        dpi = (int) (metrics.heightPixels / metrics.density);
		    }

		   return dpi < TABLET_MIN_DP_WEIGHT;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getScreen_bucket( ){

			DisplayMetrics dm = new DisplayMetrics();
			GameActivity.getInstance( ).getWindowManager().getDefaultDisplay().getMetrics(dm);
			trace("bucket : "+dm.densityDpi);

			String sRes = "mdpi";

			switch( dm.densityDpi ){

				case DisplayMetrics.DENSITY_LOW :
					sRes = "ldpi";
					break;

				/*
				case DisplayMetrics.DENSITY_DEFAULT:
					sRes = "mdpi";
					break;
				*/

				case DisplayMetrics.DENSITY_MEDIUM:
					sRes = "mdpi";
					break;

				case DisplayMetrics.DENSITY_HIGH:
					sRes = "hdpi";
					break;

				case 0x00000140://DisplayMetrics.DENSITY_XHIGH :
					sRes = "xhdpi";
					break;

				case DisplayMetrics.DENSITY_XXHIGH :
					sRes = "xxhdpi";
					break;

			}

			return sRes;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getDensity( ) {
			return GameActivity.getInstance( ).getResources().getDisplayMetrics().density+"";
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void show_loading( final boolean bCancelable ){
			trace("show_loading");
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable(){
	                @Override
	                public void run() {

	                	try{
						if( dialog_progress == null )
	                    		dialog_progress = new LoadingDialog( GameActivity.getContext( ) , bCancelable );
							dialog_progress.show( );

					} catch( Exception e) {
						trace( "Exception in show_loading");
						e.printStackTrace();
					}


	                }
		        }
            );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void hide_loading( ){
			trace("hide_loading");
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable(){
	                @Override
	                public void run() {
	                	try{
							if( dialog_progress != null )
		                   		dialog_progress.hide( );
						} catch( Exception e) {
							trace( "Exception in hide_loading");
							e.printStackTrace();
						}
	                }
		        }
            );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void wakeLock( int iDelay ){
			trace("wavelock ::: "+iDelay);
			PowerManager pm = (PowerManager) GameActivity.getInstance( ).getSystemService(Context.POWER_SERVICE);

			PowerManager.WakeLock	wakeLock = pm.newWakeLock( pm.FULL_WAKE_LOCK , "HypWave_lock" );
			if( iDelay == 0 ) {
				wakeLock.acquire( );
			} else {
				wakeLock.acquire( iDelay );
			}
		}

		static public void keepScreen_on( ) {
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable(){
	                @Override
						public void run() {
						GameActivity.getInstance( ).getWindow( ).addFlags( android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
						View mainView =	(View) GameActivity.getMainView( );
						mainView.setKeepScreenOn( true );
					}

				}
			);
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void lightsOut( ){
			trace("lightsOut :: "+uiHider);

			if( android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB ){
				View mainView =	(View) GameActivity.getMainView( );
				mainView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
			}

               /*
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable(){
				@Override
					public void run() {
						if( uiHider == null )
							uiHider = new SystemUiHider( );
							uiHider.setup( );
					}
				}
			);
			*/
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void dismiss_loading( ){
			trace("dismiss_loading");
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable(){
	                @Override
	                public void run() {
	                	try{
						if( dialog_progress != null )
	                    		dialog_progress.dismiss( );
	                    		dialog_progress = null;
					} catch( Exception e) {
						trace( "Exception in dismiss_loading");
						e.printStackTrace();
					}
	                }
		        }
            );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void openDialog( String sTitle , String sText , boolean bCancelable ){
			trace("show_error_dialog ::: "+sText+" - "+bCancelable);
			final AlertDialog.Builder builder;

			if( android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH ){
				trace("HOLO DARK");
				builder = new AlertDialog.Builder( GameActivity.getContext( ) , 2  );
			}else
				builder = new AlertDialog.Builder( GameActivity.getContext( ) );
				builder.setMessage( Html.fromHtml( sText ) );

			if( sTitle != "" )
				builder.setTitle( sTitle );
				builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
			 			dialog.cancel();
					}
				});

			GameActivity.getInstance( ).runOnUiThread(
				new Runnable(){
					@Override
					public void run() {
						try{
							AlertDialog 	alert = builder.create();
										alert.show();
						} catch( Exception e) {
							trace( "Exception in openDialog" );
							e.printStackTrace();
						}
					}
				}
			);
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void openCustomDialog(
										String sTitle,
										String sText ,
										String sButtonPos ,
										String sButtonNeg ,
										final HaxeObject obj_call_back
									){
			trace("show_custom_dialog error_msg : "+sText+" | sButtonPos : "+sButtonPos+" | sButtonNeg : "+sButtonNeg);
			final AlertDialog.Builder builder;
			if( android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH ){
				trace("HOLO DARK");
				builder = new AlertDialog.Builder( GameActivity.getContext( ) , 2  );
			}else
				builder = new AlertDialog.Builder( GameActivity.getContext( ) );
				builder.setTitle( sTitle );
				builder.setMessage( Html.fromHtml( sText ) );
				builder.setPositiveButton( sButtonPos , new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						trace("onclick button pos");
						obj_call_back.callD0("pos");
					}
				});
				builder.setNegativeButton( sButtonNeg , new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						trace("onclick button neg");
						obj_call_back.callD0("neg");
					}
					});

			GameActivity.getInstance( ).runOnUiThread(
				new Runnable(){
	                @Override
	                public void run() {
	                	try{
						AlertDialog 	alert = builder.create();
									alert.show();
					} catch( Exception e) {
						trace( "Exception in openCustomDialog" );
						e.printStackTrace();
					}
	                }
		        }
            );

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public String getSystem_lang( ){
			return Locale.getDefault().getLanguage();
		}

		static public String getUuid( ) {
			String UNIQUE_ID = "::APP_PACKAGE::.unique";
	        SharedPreferences sharedPrefs = GameActivity.getInstance( ).getSharedPreferences( UNIQUE_ID, Context.MODE_PRIVATE);
	        String uniqueID = sharedPrefs.getString(UNIQUE_ID, null);
	        if (uniqueID == null) {
	            uniqueID = UUID.randomUUID().toString();
	            Editor editor = sharedPrefs.edit();
	            editor.putString(UNIQUE_ID, uniqueID);
	            editor.commit();
	        }
			return uniqueID;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void setFixed_orientation( int o ){
			trace("setFixed_orientation ::: "+o);

			if( o == 0 ){
				trace("><< landscape");
				GameActivity.getInstance( ).setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE );
			}else{
				trace("><< portrait");
				GameActivity.getInstance( ).setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
			}

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void reportError( String sClass_name , String sMessage , String sStack ){
			trace("reportError ::: "+sClass_name+" - "+sMessage+" - "+sStack);
			ApplicationErrorReport.CrashInfo 	crash = new ApplicationErrorReport.CrashInfo();
										crash.exceptionClassName	= sClass_name;
										crash.exceptionMessage	= sMessage;
										crash.stackTrace		= sStack;
										crash.throwClassName	= sClass_name;
										crash.throwFileName		= sClass_name;
										//crash.throwLineNumber	= iLine;

			ApplicationErrorReport 	report = new ApplicationErrorReport();
								report.packageName	= report.processName = GameActivity.getInstance( ).getApplication().getPackageName();
								report.time		= System.currentTimeMillis();
								report.type		= ApplicationErrorReport.TYPE_CRASH;
								report.systemApp	= false;
								report.crashInfo	= crash;

			final Intent 	intent = new Intent(Intent.ACTION_APP_ERROR);
						intent.putExtra(Intent.EXTRA_BUG_REPORT, report);

			GameActivity.getInstance( ).runOnUiThread(
				new Runnable( ) {
					public void run() {
						GameActivity.getInstance( ).startActivity(intent);
					}
				});

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public void email( String sSubject , String sMessage , String sTo ){
			final Intent 	email = new Intent(Intent.ACTION_SEND);
						email.putExtra(Intent.EXTRA_EMAIL, new String[]{sTo});
						email.putExtra(Intent.EXTRA_SUBJECT, sSubject);
						email.putExtra(Intent.EXTRA_TEXT,sMessage);

						email.setType("message/rfc822");
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable( ) {
					public void run() {
						GameActivity.getInstance( ).startActivity(Intent.createChooser(email, "Choose an Email client :"));
					}
				});

		}


	// -------o protected



	// -------o misc

		public static void trace( String s ){
			Log.i( TAG, s );
		}

	static class LoadingDialog extends Dialog{

		ProgressBar pb;
		Boolean bCancelable;

		/**
		*
		*
		* @public
		* @return	void
		*/
		public LoadingDialog( final Context context , boolean b ){
			super( context , R.style.HypSystemDialogTheme );
			bCancelable = b;
			addContentView( pb = new ProgressBar( GameActivity.getContext( ) ) ,  new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			//getWindow().setBackgroundDrawableResource(R.color.bg_tran);
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public void onBackPressed( ){
			if( bCancelable )
				super.onBackPressed( );
		}
	}

	static public class SystemUiHider {
	    private int HIDE_DELAY_MILLIS = 2000;

	    private Handler mHandler;
	    private View mView;

	    public SystemUiHider() {
	        mView = GameActivity.getInstance( ).getMainView( );
	    }

	    public void setup() {
	        hideSystemUi();

	        mHandler = new Handler();
	        mView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
	            @Override
	            public void onSystemUiVisibilityChange(int visibility) {
	            	trace("onSystemUiVisibilityChange ::: "+visibility);
	                if (visibility != View.SYSTEM_UI_FLAG_LOW_PROFILE  ) {
	                    delay();
	                }
	            }
	        });
	    }

	    private void hideSystemUi() {
	    		trace("hideSystemUi");
	        	//mView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE  );
	    		//GameActivity.getInstance( ).getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LOW_PROFILE );
	    		GameActivity.getInstance( ).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE );
	    }

	    private Runnable mHideRunnable = new Runnable() {
	        public void run() {
	            hideSystemUi();
	        }
	    };

	    public void delay() {
	        mHandler.removeCallbacks(mHideRunnable);
	        mHandler.postDelayed(mHideRunnable, HIDE_DELAY_MILLIS);
	    }
	}

}