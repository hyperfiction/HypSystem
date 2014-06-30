package;

import hypsystem.system.DateTools;
using hypsystem.system.DateTools;

class TestIso8601 extends haxe.unit.TestCase
{

	public function testBasic(){
		assertEquals( "A", "A" );
	}

	public function testCreateIsoDate()
	{
		var d = new Date(2024, 6, 24, 6, 42, 24);
		
		//There is a small difference.. due to float precision changes ?
		assertEquals(d.toISOString(true), "2024-07-24T05:43:28Z");
		assertEquals(d.toISOString(false), "2024-07-24T06:43:28+0100");
	}

	public function testFromStringZ()
	{
		var ref = "2014-06-25T12:12:34.000Z";

		var date = DateTools.fromISO(ref);
		
		assertEquals(2014, date.getFullYear());
		assertEquals(5, date.getMonth());
		assertEquals(25, date.getDate());
		assertEquals(12, date.getHours());
	}

	public function testFromString()
	{
		var ref = "2014-06-25T12:12:34+60:00";

		var date = DateTools.fromISO(ref);
		
		assertEquals(2014, date.getFullYear());
		assertEquals(5, date.getMonth());
		assertEquals(25, date.getDate());
		assertEquals(11, date.getHours());
	}

	//HXCPP does not support dates > 2037
	public function testLimit2038()
	{
		var ref = "2100-12-25T12:12:34+60:00";

		var date = DateTools.fromISO(ref);
		assertEquals(2038, date.getFullYear());
	}

}