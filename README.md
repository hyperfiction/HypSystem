HypSystem
=============================
A native extension for OpenFL
-----------------------------

This extensions contains a bunch of tools methods for iOS & Android.

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

##### The NetworkInfos class:

>[isConnected](): Does the device has a active connection of any type.

>[getConnectionType](): Get the type of the active connection (NONE / WIFI / MOBILE)

>[isWifi](): Allow to test if active connection is of Wifi type.

>[listen]():The extension listen for connectivity status change.

##### The [Device](hypsystem/system/Device.hx) class:

>[getName](hypsystem/system/Device.hx#L14): Return the device name on iOS (example: Bob's iPad), the device model name on Android (Example : Nexus 4)

>[getUuid](hypsystem/system/Device.hx#L26): Return the device uuid. It's not using the device uuid, but a generated uuid for both platforms.

>[getLanguageCode](hypsystem/system/Device.hx#L57): Return the device language code.

>[getScreenHeight](hypsystem/system/Device.hx#L75): Return the device screen height.

>[getScreenWidth](hypsystem/system/Device.hx#L82): Return the device screen width.

>[getScaleFactor](hypsystem/system/Device.hx#L89): Return the screen scale factor.

>[isTablet](hypsystem/system/Device.hx#L68): Return true if the current device is a tablet.

##### The [Android](hypsystem/system/platform/Android.hx) platform class (hypsystem.system.platform.Android)

>[getScreenBucket](hypsystem/system/platform/Android.hx#L11): Return the screen bucket of the device. (ldpi/mdpi/hdpi/xhdpi/xxhdpi)

>[dpToPx](hypsystem/system/platform/Android.hx#L22): Convert a DP value to pixels

>[getUDID](hypsystem/system/platform/Android.hx#L35): Return the device UDID.

>[isFirebaseTestLab](hypsystem/system/platform/Android.hx#L46): Return if device is currently in the Pre-Launch test lab.

#### The [DateTools](hypsystem/system/DateTools.hx) class (hypsystem.system.DateTools)
Adding ISO & UTF date support to haxe by using native methods (iOS & Android)

Made at Hyperfiction
--------------------
[hyperfiction.fr](http://hyperfiction.fr)

Developed by : Johann Martinache [@shoe_box](https://twitter.com/shoe_box)

License
-------
This work is under BSD simplified License.
