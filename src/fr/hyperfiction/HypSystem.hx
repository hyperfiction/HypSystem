package fr.hyperfiction;

import nme.JNI;
#if cpp
import cpp.Lib;
#elseif neko
import neko.Lib;
#end

import org.haxe.nme.HaxeObject;

/**
* ...
* @author shoe[box]
*/
@:build( org.shoebox.utils.NativeMirror.build( ) )
class HypSystem{

	#if blackberry
	private static var hyp_show_loading	= Lib.load( "HypSystem" , "HypSystem_show_loading"  , 0 );
	private static var hyp_hide_loading	= Lib.load( "HypSystem" , "HypSystem_hide_loading"  , 0 );
	private static var hyp_system_lang		= Lib.load( "HypSystem" , "HypSystem_get_system_lang" , 0 );
	private static var hyp_launch_browser	= Lib.load( "HypSystem" , "HypSystem_launch_browser" , 1 );
	#end

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		private function new() {

		}

	// -------o public

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		#if ios
		@CPP("HypSystem","HypSystem_isConnectedtoInternet")
		#end
		static public function isConnected( ) : Bool {
			return true;
		}



		/**
		*
		*
		* @public
		* @return	void
		*/
		#if ios
		@CPP("HypSystem","HypSystem_isIphone")
		#end

		#if android
		@JNI
		#end
		static public function isSmartPhone( ) : Bool {
			return false;
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		static public function dialog(
											sTitle		: String,
											sText		: String
											#if android	,
											?bCancelable	: Bool,
											?sNeg		: String ,
											?sPos		: String ,
											?fPos		: Void->Void,
											?fNeg		: Void->Void
											#end
										) : Void {
			#if android
			_show_error_dialog( sTitle , sText , bCancelable , sNeg , sPos , fPos , fNeg );
			#end
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		#if ios
		@CPP( "HypSystem" , "HypSystem_get_system_lang" )
		#end
		static public function getSystem_lang( ) : String {

			var s = 'unknow';

			#if iphone
			s = hyp_system_lang( );
			#end

			#if blackberry
			s = hyp_system_lang( );
			#end

			return s;

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function show_loading( ) : Void {
			#if blackberry
			hyp_show_loading();
			#end
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function hide_loading( ) : Void {
			#if blackberry
			hyp_hide_loading();
			#end
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function dismiss_loading( ) : Void {

		}

		#if blackberry
		static public function launch_browser( sUrl : String ) : Void {
			hyp_launch_browser( sUrl );
		}
		#end

		/**
		*
		*
		* @private
		* @return	void
		*/
		static private function _show_error_dialog(
											sTitle		: String,
											sText		: String ,
											?bCancelable	: Bool,
											?sNeg		: String ,
											?sPos		: String ,
											?fPos		: Void->Void,
											?fNeg		: Void->Void
										) : Void{
			trace('_show_error_dialog ::: '+sText+" - "+sNeg+" - "+sPos);

			#if android
				if( sNeg == null && sPos == null )
					openDialog( sTitle , sText , bCancelable );
				else
					openCustomDialog( sTitle , sText , sNeg , sPos , new PopupCallBack( fPos , fNeg ));

			#end
		}

		/**
		*
		*
		* @private
		* @return	void
		*/
		#if android
		@JNI
		#end
		static private function openCustomDialog( sTitle : String , sText : String , sPos : String , sNeg : String , cb : HaxeObject ) : Void{
		}

		//Privates

		#if android
		@JNI
		#end
		private static function openDialog( sTitle : String , sText : String , bCancelable : Bool ) : Void{}

		/**
		*
		*
		* @public
		* @return	void
		*/
		#if android
		@JNI
		#end
		static public function setFixed_orientation( i : Int ) : Void {

		}

	// -------o protected

	// -------o iOS

	#if ios

		/**
		*
		*
		* @public
		* @return	void
		*/
		@CPP("HypSystem","HypSystem_screen_width")
		static public function get_screen_width( ) : Int {
			return hyp_webview_screen_w( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		@CPP("HypSystem","HypSystem_screen_height")
		static public function get_screen_height( ) : Int {
			return hyp_webview_screen_h( );
		}

		#end

	// -------o android

	#if android

		/**
		*
		*
		* @public
		* @return	void
		*/
		@JNI
		static public function isWifi( ) : Bool {

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		@JNI
		static public function getScreen_bucket( ) : String {

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		@JNI
		static public function getDensity( ) : String {

		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		@JNI
		static public function wakeLock( iDelay : Int ) : Void {}

		/**
		*
		*
		* @public
		* @return	void
		*/
		@JNI
		static public function keepScreen_on( ) : Void {}

		/**
		*
		*
		* @public
		* @return	void
		*/
		@JNI
		static public function getLocal_IP( ) : String {}

		/**
		*
		*
		* @public
		* @return	void
		*/
		@JNI
		static public function lightsOut( ) : Void {}


	#end

	// -------o misc

}

#if android

/**
 * ...
 * @author shoe[box]
 */

class PopupCallBack extends HaxeObject{

	public var fPos : Void->Void;
	public var fNeg : Void->Void;

	// -------o constructor

		/**
		* constructor
		*
		* @param
		* @return	void
		*/
		public function new( fPos : Void->Void , fNeg : Void->Void ) {
			super( );
			this.fPos = fPos;
			this.fNeg = fNeg;
		}

	// -------o public

		/**
		*
		*
		* @public
		* @return	void
		*/
		public function pos( ) : Void {
			fPos( );
		}

		/**
		*
		*
		* @public
		* @return	void
		*/
		public function neg( ) : Void {
			fNeg( );
		}

	// -------o protected



	// -------o misc

}
#end
