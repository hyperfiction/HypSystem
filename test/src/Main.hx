package;

import flash.display.Sprite;

class Main extends Sprite
{

	public function new ()
	{
		super ();
		trace("constructor");

		var r = new haxe.unit.TestRunner();
        r.add(new TestIso8601());
        r.run();
	}
	
}
