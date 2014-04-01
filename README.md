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

##### The Device class:

>[getUuid](): Return the device uuid. It's not using the device uuid, but a generated uuid for both platforms.

>[getLanguageCode](): Get the language code used by the device.

>[getScreenHeight](): Return the device screen height

>[getScreenWidth](): Return the device screen width

#### The Android platform class (hypsystem.system.platform.Android)

>[getScreenBucket](): Return the screen bucket of the device. (ldpi/mdpi/hdpi/xhdpi/xxhdpi)

>[dpToPx](): Convert a DP value to pixels

>[getDensity](): Return the device screen density

Made at Hyperfiction
--------------------
[hyperfiction.fr](http://hyperfiction.fr)

Developed by : Johann Martinache [@shoe_box](https://twitter.com/shoe_box)

License
-------
This work is under BSD simplified License.
