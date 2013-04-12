package fr.hyperfiction;

import ::APP_PACKAGE::.R;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ProgressBar;
import android.text.Html;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
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

				/*
				case DisplayMetrics.DENSITY_XXHIGH :
					sRes = "xxhdpi";
					break;
				*/
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
		static public void show_loading( ){
			GameActivity.getInstance( ).runOnUiThread(
				new Runnable(){
	                @Override
	                public void run() {
	                	if( dialog_progress == null )
	                    	dialog_progress = new LoadingDialog( GameActivity.getContext( ) );
							dialog_progress.show( );
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
	                	if( dialog_progress != null )
	                    	dialog_progress.hide( );
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

			PowerManager.WakeLock 	wakeLock = pm.newWakeLock( pm.FULL_WAKE_LOCK , "HypWave_lock" );
								wakeLock.acquire( iDelay );

			GameActivity.getInstance( ).runOnUiThread(
				new Runnable(){
	                @Override
	                	public void run() {
						GameActivity.getInstance( ).getWindow( ).addFlags( android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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
	                	if( dialog_progress != null )
	                    	dialog_progress.dismiss( );
	                    	dialog_progress = null;
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
						AlertDialog alert = builder.create();
									alert.show();
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
	                	AlertDialog 	alert = builder.create();
          						alert.show();
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


	// -------o protected



	// -------o misc

		public static void trace( String s ){
			Log.i( TAG, s );
		}

	static class LoadingDialog extends Dialog{

		ProgressBar pb;

		/**
		*
		*
		* @public
		* @return	void
		*/
		public LoadingDialog( final Context context ){
			super( context , R.style.CustomDialogTheme );

			addContentView( pb = new ProgressBar( GameActivity.getContext( ) ) ,  new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			//getWindow().setBackgroundDrawableResource(R.color.bg_tran);
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
	                if (visibility != View.STATUS_BAR_VISIBLE ) {
	                    delay();
	                }
	            }
	        });
	    }

	    private void hideSystemUi() {
	    		trace("hideSystemUi");
	        	mView.setSystemUiVisibility(View.STATUS_BAR_VISIBLE );
	    		GameActivity.getInstance( ).getWindow().getDecorView().setSystemUiVisibility(View.STATUS_BAR_VISIBLE );
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