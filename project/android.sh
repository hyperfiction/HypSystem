rm -rf "obj"
rm -rf "all_objs"
echo "Compiling for armv6"
haxelib run hxcpp Build.xml -Dandroid -debug
echo "Compiling for armv7"
haxelib run hxcpp Build.xml -Dandroid -debug -DHXCPP_ARMV7
rm -rf "obj"
rm -rf "all_objs"