rm -rf "obj"
echo "compiling for armv6"
haxelib run hxcpp Build.xml -Diphoneos
echo "compiling for armv7"
haxelib run hxcpp Build.xml -Diphoneos -DHXCPP_ARMV7
echo "compiling for simulator"
haxelib run hxcpp Build.xml -Diphonesim
echo "Copying sim"
cp ../ndll/iPhone/libHypSystem.iphonesim.a ../../../Export/ios/hypertest/lib/i386/libHypSystem.a
echo "Copying v6"
cp ../ndll/iPhone/libHypSystem.iphoneos.a ../../../Export/ios/hypertest/lib/armv6/libHypSystem.a
echo "Copying v7"
cp ../ndll/iPhone/libHypSystem.iphoneos-v7.a ../../../Export/ios/hypertest/lib/armv7/libHypSystem.a