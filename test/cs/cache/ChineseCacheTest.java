package cs.cache;

import java.util.List;

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
			cc = ChineseCache.createChineseCache("wordWubi", resource, ValueCodingTypes.WUBI_WORD);
			traceInfo(cc, "sg", "testWordWubi");
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
			traceInfo(cc, "sg", "testWordsWubi");
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
			traceInfo(cc, "sg", "testWordPinyin");
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
			traceInfo(cc, "sg", "testWordsPinyin");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void traceInfo(IChineseCache cc, String key, String desc) {
		System.out.println(desc + ": " + cc.getKeysSize());
		List<String> keyList = cc.getKeys(key);
		if (null != keyList) {
			System.out.println(desc + ": " + keyList.size());
			System.out.print(keyList + "\n");
		} else {
			System.out.println(desc + ": 0");
		}
	}
}
