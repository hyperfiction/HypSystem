package;

import hypsystem.system.DateTools;
using hypsystem.system.DateTools;

class TestIso8601 extends haxe.unit.TestCase
{

	public function testCreateIsoDate()
	{
		var d = new Date(2024, 6, 24, 6, 42, 24);
		
		//

		var isostring = d.toISOString(true);
		assertEquals("2024-07-24", isostring.split("T")[0]);

		isostring = d.toISOString(false);
		assertEquals("2024-07-24", isostring.split("T")[0]);
		assertTrue(isostring.indexOf("+0100") != -1);
	}

	public function testFromStringZ()
	{
		var ref = "2014-06-25T12:12:34.000Z";

		var date = DateTools.fromISO(ref);
		
		assertEquals(2014, date.getFullYear());
		assertEquals(5, date.getMonth());
		assertEquals(25, date.getDate());
	}

	public function testFromString()
	{
		var ref = "2014-06-25T12:12:34+00:00";

		var date = DateTools.fromISO(ref);
		
		assertEquals(2014, date.getFullYear());
		assertEquals(5, date.getMonth());
		assertEquals(25, date.getDate());
	}

	//HXCPP does not support dates > 2037
	public function testLimit2038()
	{
		var ref = "2100-12-25T12:12:34+60:00";

		var date = DateTools.fromISO(ref);
		assertEquals(2038, date.getFullYear());
	}

}