#ifndef Device

#define Device

namespace hypsystem{

	bool isConnectedtoInternet( );
	bool isiPhone( );
	int get_screen_height( );
	int get_screen_width( );
	const char* get_system_lang( );
	const char* get_uuid( );

	#ifdef BLACKBERRY
		void show_loading( );
		void hide_loading( );
		bool LaunchBrowser( const char *inUtf8Url );
	#endif

}

#endif