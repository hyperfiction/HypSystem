rm -rf "obj"
echo "compiling for armv6"
haxelib run hxcpp Build.xml -Diphoneos -DHXCPP_CLANG -DOBJC_ARC
echo "compiling for armv7"
haxelib run hxcpp Build.xml -Diphoneos -DHXCPP_ARMV7 -DHXCPP_CLANG -DOBJC_ARC
echo "compiling for simulator"
haxelib run hxcpp Build.xml -Diphonesim -DHXCPP_CLANG -DOBJC_ARC
echo "Done ! \n"
