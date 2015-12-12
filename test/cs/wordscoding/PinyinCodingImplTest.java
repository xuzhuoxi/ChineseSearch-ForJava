package cs.wordscoding;

import org.junit.Test;

import cs.cache.ChineseCache;
import cs.cache.IChineseCache;
import cs.cache.valuecoding.ValueCodingTypes;
import cs.resource.Resource;
import cs.resource.ResourceTypes;

public class PinyinCodingImplTest {
	private PinyinCodingImpl impl = new PinyinCodingImpl();

	private String[] testAry = new String[] { "一", "丘", "之", "貉", "一丘之貉" };

	@Test
	public void test() {
		Resource resource;
		IChineseCache cc;
		try {
			resource = Resource.getResource("word", ResourceTypes.TYPE_PINYIN);
			cc = ChineseCache.createChineseCache("wordPinyin", resource, ValueCodingTypes.PINYIN_WORD);
			for (String test : testAry) {
				System.out.println("PinyinCodingImplTest.test()" + test + ":");
				String[] codings = impl.coding(cc, test);
				for (String coding : codings) {
					System.out.println("PinyinCodingImplTest.test()" + coding);
				}
				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
