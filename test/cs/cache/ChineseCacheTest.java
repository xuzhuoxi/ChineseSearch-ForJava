package cs.cache;

import org.junit.Test;

import cs.cache.valuecoding.ValueCodingTypes;
import cs.resource.Resource;
import cs.resource.ResourceTypes;

public class ChineseCacheTest {
	@Test
	public void testWordWubi() {
		Resource resource;
		IChineseCache cc;
		try {
			resource = Resource.getResource("word", ResourceTypes.TYPE_WUBI);
			cc = ChineseCache.createChineseCache("wordWubi", resource, ValueCodingTypes.WUBI_WORDS);
			System.out.println("ChineseCacheTest.testWordWubi(): " + cc.getKeysSize());
			// List<String> keyList = cc.getKeys("sg");
			// if (null != keyList) {
			// System.out.println("ChineseCacheTest.testWordWubi(): " +
			// keyList.size());
			// for (String key : keyList) {
			// System.out.print(" " + key);
			// }
			// System.out.println();
			// } else {
			// System.out.println("ChineseCacheTest.testWordWubi(): 0");
			// }
			// System.out.println(cc.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWordsWubi() {
		Resource resource;
		IChineseCache cc;
		try {
			resource = Resource.getResource("words", ResourceTypes.TYPE_WUBI);
			cc = ChineseCache.createChineseCache("wordsWubi", resource, ValueCodingTypes.WUBI_WORDS);
			System.out.println("ChineseCacheTest.testWordsWubi()：" + cc.getKeysSize());
			// List<String> keyList = cc.getKeys("sg");
			// if (null != keyList) {
			// System.out.println("ChineseCacheTest.testWordsWubi(): " +
			// keyList.size());
			// for (String key : keyList) {
			// System.out.print(" " + key);
			// }
			// System.out.println();
			// } else {
			// System.out.println("ChineseCacheTest.testWordsWubi(): 0");
			// }
			// System.out.println(cc.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWordPinyin() {
		Resource resource;
		IChineseCache cc;
		try {
			resource = Resource.getResource("word", ResourceTypes.TYPE_PINYIN);
			cc = ChineseCache.createChineseCache("wordPinyin", resource, ValueCodingTypes.PINYIN_WORD);
			System.out.println("ChineseCacheTest.testWordPinyin()：" + cc.getKeysSize());
			// List<String> keyList = cc.getKeys("sg");
			// if (null != keyList) {
			// System.out.println("ChineseCacheTest.testWordPinyin(): " +
			// keyList.size());
			// for (String key : keyList) {
			// System.out.print(" " + key);
			// }
			// System.out.println();
			// } else {
			// System.out.println("ChineseCacheTest.testWordPinyin(): 0");
			// }
			// System.out.println(cc.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWordsPinyin() {
		Resource resource;
		IChineseCache cc;
		try {
			resource = Resource.getResource("words", ResourceTypes.TYPE_PINYIN);
			cc = ChineseCache.createChineseCache("wordsPinyin", resource, ValueCodingTypes.PINYIN_WORDS);
			System.out.println("ChineseCacheTest.testWordsPinyin()：" + cc.getKeysSize());
			// List<String> keyList = cc.getKeys("sg");
			// if (null != keyList) {
			// System.out.println("ChineseCacheTest.testWordsWubi(): " +
			// keyList.size());
			// for (String key : keyList) {
			// System.out.print(" " + key);
			// }
			// System.out.println();
			// } else {
			// System.out.println("ChineseCacheTest.testWordsWubi(): 0");
			// }
			// System.out.println(cc.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
