package cs.wordscoding;

import org.junit.Test;

import cs.cache.ChineseCache;
import cs.cache.IChineseCache;
import cs.cache.valuecoding.ValueCodingTypes;
import cs.resource.Resource;
import cs.resource.ResourceTypes;

public class WubiCodingImplTest {
	private WubiCodingImpl impl = new WubiCodingImpl();

	private String[] testAry = new String[] { "一", "丁", "点", "一丁点" };

	@Test
	public void test() {
		Resource resource;
		IChineseCache cc;
		try {
			resource = Resource.getResource("word", ResourceTypes.TYPE_WUBI);
			cc = ChineseCache.createChineseCache("wordWubi", resource, ValueCodingTypes.WUBI_WORD);
			for (String test : testAry) {
				String[] codings = impl.coding(cc, test);
				System.out.println("WubiCodingImplTest.test()Len: " + codings.length);
				for (String coding : codings) {
					System.out.println("WubiCodingImplTest.test())" + coding);
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
