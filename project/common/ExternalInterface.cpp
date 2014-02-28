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
#include "VideoPlayer.h"

using namespace hypsystem;
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
