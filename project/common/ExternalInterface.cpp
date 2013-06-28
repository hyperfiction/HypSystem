

#ifndef IPHONE
#define IMPLEMENT_API
#endif

#include <hx/CFFI.h>
#include "HypSystem.h"
#include <stdio.h>

using namespace hypsystem;
extern "C"{

	int HypSystem_register_prims(){
		printf("HypSystem: register_prims()\n");
		return 0;
	}
}

#ifdef BLACKBERRY
	static void HypSystem_show_loading( ){
		show_loading( );
	}
	DEFINE_PRIM( HypSystem_show_loading , 0 );

	static void HypSystem_hide_loading( ){
		hide_loading( );
	}
	DEFINE_PRIM( HypSystem_hide_loading , 0 );

	static value HypSystem_get_system_lang( ){
		return alloc_string( get_system_lang( ) );
	}
	DEFINE_PRIM( HypSystem_get_system_lang , 0 );

	static value HypSystem_launch_browser( value sUrl ){
		return alloc_bool( LaunchBrowser( val_string( sUrl ) ) );
	}
	DEFINE_PRIM( HypSystem_launch_browser , 1 );
#endif

#ifdef IPHONE
	static value HypSystem_isConnectedtoInternet( ){
		return alloc_bool( isConnectedtoInternet( ));
	}
	DEFINE_PRIM( HypSystem_isConnectedtoInternet , 0 );

	static value HypSystem_isIphone( ){
		return alloc_bool( isiPhone( ) );
	}
	DEFINE_PRIM( HypSystem_isIphone , 0 );

	static value HypSystem_screen_width( ){
		return alloc_int( get_screen_width( ) );
	}
	DEFINE_PRIM( HypSystem_screen_width , 0 );

	static value HypSystem_screen_height( ){
		return alloc_int( get_screen_height( ) );
	}
	DEFINE_PRIM( HypSystem_screen_height , 0 );

	static value HypSystem_get_system_lang( ){
		return alloc_string( get_system_lang( ) );
	}
	DEFINE_PRIM( HypSystem_get_system_lang , 0 );

	static value HypSystem_get_uuid( ){
		return alloc_string( get_uuid( ) );
	}
	DEFINE_PRIM( HypSystem_get_uuid , 0 );

	static value HypSystem_dialog( value sTitle , value sText , value sButton ){
		openDialog(
					val_string( sTitle ),
					val_string( sText ),
					val_string( sButton )
				);
		return alloc_null( );
	}
	DEFINE_PRIM( HypSystem_dialog , 3 );

	static value HypSystem_setOrientation( value o ){
		setOrientation( val_int( o ) );
		return alloc_null( );
	}
	DEFINE_PRIM( HypSystem_setOrientation , 1 );

#endif
