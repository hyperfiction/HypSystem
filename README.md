HypSystem
=============================
A native extension for Haxe NME
-----------------------------

This extensions contains a bunch of tools methods for iOS & Android ( and some for blackberry too )

Installation
------------
There is an [include.nmml]() file and the [ndll]() are compiled for:
* ios armv6
* ios armv7
* ios simulator
* android armv6

Recompiling
-----------
For recompiling the native extensions just use the sh files contained in the project folder.

Basic reference
---------------

<b>Common between iOs & Android:</b>

- isConnected : Test connection status

- isSmartPhone : Smartphone or tablet ?

- getSystem_lang : return the system language code

- getUuid: return an UUID. It's random generated and store in user prefs, so it's unique for each installation.

<b>Android:</b>

 - show_loading : show a loading spinner

 - hide_loading : hide the loading
   spinner

 - dismiss_loading : dismiss the spinner

 - isWifi : Test if the device is Wifi
   connected

 - getScreen_bucket : Return the device
   screen bucker

 - getDensity : Return the screen
   density of the device

 - wakeLock : Acquire a FULL_WAKE_LOCK

 - keepScreen_on : Prevent screen dimming

 - lightsOut : Lights Out mode

 - getLocal_IP  : Get the local ip of the
   device

 - openDialog : Open a native Android
   Dialog

Made at Hyperfiction
--------------------
[hyperfiction.fr](http://hyperfiction.fr)

Developed by : Johann Martinache [@shoe_box](https://twitter.com/shoe_box)

License
-------
This work is under BSD simplified License.
