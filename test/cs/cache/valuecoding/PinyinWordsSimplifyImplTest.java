package cs.cache.valuecoding;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

import cs.resource.Resource;
import cs.resource.ResourceTypes;

public class PinyinWordsSimplifyImplTest {
	private static IValueCodingStrategy strategy = new PinyinWordsStrategyImpl();

	// @Test
	public void test() {
		// 幸免于难=nie mian xu nan#xing mian xu nan#nie wan xu nan#xing wan xu
		// nan#nie wen xu nan#xing wen xu nan#nie mian yu nan#xing mian yu
		// nan#nie wan yu nan#xing wan yu nan#nie wen yu nan#xing wen yu nan#nie
		// mian xu nuo#xing mian xu nuo#nie wan xu nuo#xing wan xu nuo#nie wen
		// xu nuo#xing wen xu nuo#nie mian yu nuo#xing mian yu nuo#nie wan yu
		// nuo#xing wan yu nuo#nie wen yu nuo#xing wen yu nuo
		String value = "nie mian xu nan#xing mian xu nan#nie wan xu nan#xing wan xu nan#nie wen xu nan#xing wen xu nan#nie mian yu nan#xing mian yu nan#nie wan yu nan#xing wan yu nan#nie wen yu nan#xing wen yu nan#nie mian xu nuo#xing mian xu nuo#nie wan xu nuo#xing wan xu nuo#nie wen xu nuo#xing wen xu nuo#nie mian yu nuo#xing mian yu nuo#nie wan yu nuo#xing wan yu nuo#nie wen yu nuo#xing wen yu nuo";
		String[] values = value.split("#");
		System.out.println("PinyinWordsSimplifyImplTest.test(): " + values.length);
		for (String singleValue : values) {
			String sValue = strategy.getSimplifyValue(singleValue);
			System.out.println("PinyinWordsSimplifyImplTest.test(): [" + singleValue + "][" + sValue + "]");
			String[] sValues = strategy.getDimensionKeys(sValue);
			for (String str : sValues) {
				System.out.print(" " + str);
			}
			System.out.println();
		}

	}

	@Test
	public void testPerformance0() {
		try {
			Resource resource = Resource.getResource("words", ResourceTypes.TYPE_PINYIN);
			String[] values = resource.getValues();
			for (String value : values) {
				String simValue = strategy.getSimplifyValue(value.trim());
				strategy.getDimensionKeys(simValue);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

//	@Test
	public void testPerformance1() {
		try {
			Resource resource = Resource.getResource("words", ResourceTypes.TYPE_PINYIN);
			for (int i = 0; i < resource.size(); i++) {
				strategy.getDimensionKeys(strategy.getSimplifyValue(resource.getValue(i).trim()));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
