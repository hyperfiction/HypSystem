package hypsystem.system;

@:build(ShortCuts.mirrors())
@CPP_DEFAULT_LIBRARY("hyp-system")
@CPP_PRIMITIVE_PREFIX("hypsystem_orientation")
class Orientation
{

	@JNI @IOS public static function set(value:Int):Void{}

}
