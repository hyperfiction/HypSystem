rm -rf "obj"
rm ../ndll/Android/libHypSystem.so
echo "Compiling for armv6"
haxelib run hxcpp Build.xml -Dandroid
echo "Copying..."
cp ../ndll/Android/libHypSystem.so ../../../bin/android/bin/libs/armeabi/libHypSystem.so