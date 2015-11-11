package cs.resource;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import code.path.BasePathUtils;

/**
 * 
 * @author xuzhuoxi
 *
 */
public class Resource {
	private String baseName;
	private String resourceType;
	private List<String> keyList = new ArrayList<String>();
	private List<String> valueList = new ArrayList<String>();

	private Resource() {
	}

	public final String getBaseName() {
		return baseName;
	}

	public final String getResourceType() {
		return resourceType;
	}

	/**
	 * 判断是否为键
	 * 
	 * @param key
	 *            待判断的键
	 * @return 是true否false
	 */
	public final boolean isKey(String key) {
		return keyList.contains(key);
	}

	/**
	 * 键值对数量
	 * 
	 * @return 键值对数量
	 */
	public final int size() {
		return keyList.size();
	}

	/**
	 * 取键
	 * 
	 * @param index
	 *            索引
	 * @return 键
	 */
	public final String getKey(int index) {
		return keyList.get(index);
	}

	/**
	 * 取全部键
	 * 
	 * @return 键组成的数组
	 */
	public final String[] getKeys() {
		String[] rs = new String[keyList.size()];
		return keyList.toArray(rs);
	}

	/**
	 * 取值
	 * 
	 * @param index
	 *            索引
	 * @return 值
	 */
	public final String getValue(int index) {
		return valueList.get(index);
	}

	/**
	 * 通过资源的路径创建实例，默认资源编码格式为UTF-8。<br>
	 * 其它编码可参考{@link #getResourceByPath(String, String)}
	 * 
	 * @param path
	 *            文件路径
	 * @return Resource实例
	 * @throws UnsupportedEncodingException
	 *             如果不支持指定的字符集
	 * @throws FileNotFoundException
	 *             当试图打开指定路径名表示的文件失败时，抛出此异常。
	 * @throws IOException
	 *             如果发生 I/O 错误
	 */
	public static final Resource getResourceByPath(String path)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		return getResourceByPath(path, "UTF-8");
	}

	/**
	 * 通过资源的路径和编码格式创建实例<br>
	 * 其它请参考{@link #getResourceByPath(String)}
	 * 
	 * @param path
	 *            文件路径
	 * @param charsetName
	 *            文件编码格式
	 * @return Resource实例
	 * @throws UnsupportedEncodingException
	 *             如果不支持指定的字符集
	 * @throws FileNotFoundException
	 *             当试图打开指定路径名表示的文件失败时，抛出此异常。
	 * @throws IOException
	 *             如果发生 I/O 错误
	 */
	public static final Resource getResourceByPath(String path, String charsetName)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		if (null == path || 0 == path.length()) {
			return null;
		}
		int sepIndex = path.lastIndexOf(".");
		String fullName;
		if (-1 == sepIndex) {
			fullName = path;
		} else {
			fullName = path.substring(0, sepIndex);
		}
		String baseName;
		String resourceType;
		sepIndex = fullName.lastIndexOf("_");
		if (-1 == sepIndex) {
			baseName = fullName;
			resourceType = "";
		} else {
			baseName = fullName.substring(0, sepIndex);
			resourceType = fullName.substring(sepIndex, fullName.length());
		}
		Resource rs = getResource(new File(path), charsetName, baseName, resourceType);
		return rs;
	}

	/**
	 * 通过两部分信息创建实例{@link #baseName}{@link #resourceType}<br>
	 * 资源位置固定为：运行目录/resource/<br>
	 * 默认编码为：UTF-8<br>
	 * 其它： {@link #getResource(String, String, String)}<br>
	 * 
	 * @param baseName
	 *            基本名称
	 * @param resourceType
	 *            类型名称
	 * @return Resource实例
	 * @throws UnsupportedEncodingException
	 *             如果不支持指定的字符集
	 * @throws FileNotFoundException
	 *             当试图打开指定路径名表示的文件失败时，抛出此异常。
	 * @throws IOException
	 *             如果发生 I/O 错误
	 */
	public static final Resource getResource(String baseName, String resourceType)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		return getResource(baseName, resourceType, "UTF-8");
	}

	/**
	 * 通过两部分信息以及编码信息创建实例{@link #baseName}{@link #resourceType}<br>
	 * 资源位置固定为：运行目录/resource/<br>
	 * 其它： {@link #getResource(String, String)}<br>
	 * 
	 * @param baseName
	 *            基本名称
	 * @param resourceType
	 *            类型名称
	 * @param charsetName
	 *            文件编码格式
	 * @return Resource实例
	 * @throws UnsupportedEncodingException
	 *             如果不支持指定的字符集
	 * @throws FileNotFoundException
	 *             当试图打开指定路径名表示的文件失败时，抛出此异常。
	 * @throws IOException
	 *             如果发生 I/O 错误
	 */
	public static final Resource getResource(String baseName, String resourceType, String charsetName)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		File file = new File(BasePathUtils.getAppPath(Resource.class) + "/resource/" + baseName + "_" + resourceType
				+ ".properites");
		Resource rs = getResource(file, charsetName, baseName, resourceType);
		return rs;
	}

	/**
	 * 通过字符串数据创建实例<br>
	 * 
	 * @param data
	 *            字符串数据
	 * @return Resource实例
	 * @throws IOException
	 *             如果发生 I/O 错误
	 */
	public static final Resource getResourceByData(String data) throws IOException {
		if (null == data || data.length() == 0)
			return null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new StringReader(data));
			Resource rs = new Resource();
			saveData(rs, br);
			return rs;
		} finally {
			close(br);
		}
	}

	private static final Resource getResource(File file, String charsetName, String baseName, String resourceType)
			throws UnsupportedEncodingException, FileNotFoundException, IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			Resource rs = new Resource();
			rs.baseName = baseName;
			rs.resourceType = resourceType;
			saveData(rs, br);
			return rs;
		} finally {
			close(br);
		}
	}

	private static final void saveData(Resource rs, BufferedReader br) throws IOException {
		String line;
		int sepIndex;
		String key;
		String value;
		while ((line = br.readLine()) != null) {
			if (line.length() != 0) {
				sepIndex = line.indexOf("=");
				if (-1 == sepIndex) {
					key = line.trim();
					value = null;
				} else {
					key = line.substring(0, sepIndex).trim();
					value = line.substring(sepIndex + 1, line.length()).trim();
				}
				rs.keyList.add(key);
				rs.valueList.add(value);
			}
		}
	}

	private static final void close(Closeable c) {
		if (null != c) {
			try {
				c.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
