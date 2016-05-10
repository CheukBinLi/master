package project.master.fw.sh.common;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.security.MessageDigest;

public class MD5 {

	/***
	 * MD5码
	 * 
	 * @param file
	 * @return
	 */
	public static String createMD5(File file) {
		MessageDigest MD5 = null;
		FileInputStream fileInputStream = null;
		MappedByteBuffer byteBuffer = null;
		FileChannel fc = null;
		byte[] b = null;
		int length = 0;
		try {
			MD5 = MessageDigest.getInstance("MD5");
			fileInputStream = new FileInputStream(file);
			if (file.length() > 550 * 1024 * 1024) {
				b = new byte[51200];
				while ((length = fileInputStream.read(b)) > 0) {
					MD5.update(b, 0, length);
				}
				fileInputStream.close();
				return bufferToHex(MD5.digest());
			}
			fc = fileInputStream.getChannel();
			// 写入nio
			byteBuffer = fc.map(MapMode.READ_ONLY, 0, file.length());
			// 载入MD5计算
			MD5.update(byteBuffer);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
				if (fc != null) {
					fc.close();
				}
				// bb = null;
			} catch (Exception e) {
			}
			System.gc();
		}
		return bufferToHex(MD5.digest());
	}

	/** MD5码 */
	public static String createMD5(String path) {
		return createMD5(new File(path));
	}

	/** MD5密码 */
	public static String MD5Password(String str) {
		MessageDigest MD5 = null;
		try {
			MD5 = MessageDigest.getInstance("MD5");
			MD5.update(str.getBytes());
			return bufferToHex(MD5.digest());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.gc();
		}
		return null;
	}

	/***
	 * 默认的密码字符串组合，apache校验下载的文件的正确性用的就是默认的这个组合 枚举作用
	 */
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

}
