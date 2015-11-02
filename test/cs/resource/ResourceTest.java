package cs.resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.junit.Test;

import code.string.StringUtils;
import cs.resource.Resource;
import cs.resource.ResourceTypes;

public class ResourceTest {

	@Test
	public void test() {
		HashMap<String, ArrayList<String>> rMap = new HashMap<String, ArrayList<String>>();
		try {
			Resource rs = Resource.getResource("word", ResourceTypes.TYPE_PINYIN);
			System.out.println("ResourceTest.test() KeySize: " + rs.size());
			for (int i = 0; i < rs.size(); i++) {
				String key = rs.getKey(i);
				String value = rs.getValue(i).toLowerCase();
				if (null == key || key.length() == 0 || null == value || value.length() == 0) {
					continue;
				}

				String newValue = StringUtils.toNonRepeatString(value);
				for (int j = 0; j < newValue.length(); j++) {
					ArrayList<String> al = null;
					String sKey = newValue.charAt(j) + "";
					if (!sKey.equals("#")) {
						if (rMap.containsKey(sKey)) {
							al = rMap.get(sKey);
						} else {
							al = new ArrayList<String>();
							rMap.put(sKey, al);
						}
						al.add(key);
					}
				}
			}
			Set<String> keySet = rMap.keySet();
			for (String key : keySet) {
				System.out.println("ResourceTest.test() Key: " + key);
				System.out.println("ResourceTest.test() ValueLength: " + rMap.get(key).size());
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
