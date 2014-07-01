package;

import flash.display.Sprite;
import haxe.unit.TestRunner;

class Main extends Sprite
{

	public function new ()
	{
		super ();
		trace("constructor");

		var r = new TestRunner();
        r.add(new TestIso8601());
        r.run();

        trace(r.result.toString());
	}
	
}
