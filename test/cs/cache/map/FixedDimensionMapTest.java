package cs.cache.map;

import org.junit.Test;

import cs.cache.map.FixedDimensionMapImpl;
import cs.cache.map.IDimensionMap;

public class FixedDimensionMapTest {
	private static final Character[] keyCode = new Character[] { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
			'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	private IDimensionMap mdMap = new FixedDimensionMapImpl(keyCode, 1);

	@Test
	public void test() {
		System.out.println("FixedCharsetMultidimensionalMapTest.test()");
		System.out.println(mdMap.toString());
	}

}
