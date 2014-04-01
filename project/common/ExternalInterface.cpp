#ifndef STATIC_LINK
#define IMPLEMENT_API
#endif

#if defined(HX_WINDOWS) || defined(HX_MACOS) || defined(HX_LINUX)
#define NEKO_COMPATIBLE
#endif


#include <hx/CFFI.h>
#include <hxcpp.h>
#include <stdio.h>

#ifdef IPHONE
#include <stddef.h>
#include "Device.h"
#include "NetworkInfos.h"
#endif

#ifdef ANDROID
	#include <cstddef>
	#include <hx/CFFI.h>
	#include <jni.h>
	#define LOG_TAG "HypSystem"
	#define ALOG(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#endif


//Common -----------------------------------------------------------------------

AutoGCRoot *fOnEventListener = 0;

static value hypsystem_setEventListener(value fListener)
{
	fOnEventListener = new AutoGCRoot(fListener);
	return alloc_bool(true);
}
DEFINE_PRIM(hypsystem_setEventListener, 1);

//Android-----------------------------------------------------------------------

#ifdef ANDROID

extern "C" JNIEXPORT void JNICALL Java_hypsystem_net_NetworkInfos_onUpdate(
	JNIEnv * env, jobject obj)
{
	int top = 0;
	gc_set_top_of_stack(&top,true);
	gc_exit_blocking();

	val_call0(fOnEventListener->get());

	gc_enter_blocking();
}

#endif

//iOS --------------------------------------------------------------------------

#ifdef IPHONE

//NetworkInfos.hx

extern "C" void hypsystem_networkinterface_onUpdate()
{
	int top = 0;
	gc_set_top_of_stack(&top,true);
	gc_exit_blocking();

	val_call0(fOnEventListener->get());

	gc_enter_blocking();	
}

static value hypsystem_networkinterface_isConnected()
{
	return alloc_bool(networkinfos::isConnected());	
}
DEFINE_PRIM(hypsystem_networkinterface_isConnected, 0);

static value hypsystem_networkinterface_connectionType()
{
	return alloc_int(networkinfos::getActiveConnectionType());	
}
DEFINE_PRIM(hypsystem_networkinterface_connectionType, 0);

static value hypsystem_networkinterface_isWifi()
{
	bool isWifi = networkinfos::isWifi();
	return alloc_bool(isWifi);	
}
DEFINE_PRIM(hypsystem_networkinterface_isWifi, 0);

static value hypsystem_networkinterface_listen()
{
	networkinfos::listen();
	return alloc_null();
}
DEFINE_PRIM(hypsystem_networkinterface_listen, 0);

//Device.hx

static value hypsystem_device_getUuid()
{
	const char *res = device::getUuid();
	return alloc_string(res);
}
DEFINE_PRIM(hypsystem_device_getUuid, 0);

static value hypsystem_device_getLangCode()
{
	const char *res = device::getLangCode();
	return alloc_string(res);
}
DEFINE_PRIM(hypsystem_device_getLangCode, 0);

static value hypsystem_device_getScreenWidth()
{
	int res = device::getScreenWidth();
	return alloc_int(res);
}
DEFINE_PRIM(hypsystem_device_getScreenWidth, 0);

static value hypsystem_device_getScreenHeight()
{
	int res = device::getScreenHeight();
	return alloc_int(res);
}
DEFINE_PRIM(hypsystem_device_getScreenHeight, 0);

static value hypsystem_device_isTablet()
{
	bool isTablet = device::isTablet();
	return alloc_bool(isTablet);
}
DEFINE_PRIM(hypsystem_device_isTablet, 0);

static value hypsystem_device_getScaleFactor()
{
	float scaleFactor = device::getScaleFactor();
	return alloc_float(scaleFactor);
}
DEFINE_PRIM(hypsystem_device_getScaleFactor, 0);

#endif

//Main -------------------------------------------------------------------------

extern "C" void hypsystem_main() 
{
	val_int(0); // Fix Neko init	
}
DEFINE_ENTRY_POINT(hypsystem_main);

extern "C" int hypsystem_register_prims()
{ 
	return 0; 
}
