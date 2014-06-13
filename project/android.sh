rm -rf "obj"
rm -rf "all_objs"
echo "Compiling for armv6"
haxelib run hxcpp Build.xml -Dandroid
echo "Compiling for armv7"
haxelib run hxcpp Build.xml -Dandroid -DHXCPP_ARMV7
rm -rf "obj"
rm -rf "all_objs"