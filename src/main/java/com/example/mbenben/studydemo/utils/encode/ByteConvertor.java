package com.example.mbenben.studydemo.utils.encode;

/**
 * Created by zhangkai on 17/7/24.
 */
public class ByteConvertor {

	private static final String HEX = "0123456789ABCDEF";

	public static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private static void appendHex(StringBuffer sb, byte b) {
		sb.append("0123456789ABCDEF".charAt(b >> 4 & 0xF)).append(
				"0123456789ABCDEF".charAt(b & 0xF));
	}

	public static int toInt(byte[] byteArray4) {
		int intValue = 0;
		intValue |= byteArray4[3] & 0xFF;
		intValue <<= 8;
		intValue |= byteArray4[2] & 0xFF;
		intValue <<= 8;
		intValue |= byteArray4[1] & 0xFF;
		intValue <<= 8;
		intValue |= byteArray4[0] & 0xFF;
		return intValue;
	}

	public static long toLong(byte[] byteArray8) {
		long longValue = 0L;
		longValue |= byteArray8[7] & 0xFF;
		longValue <<= 8;
		longValue |= byteArray8[6] & 0xFF;
		longValue <<= 8;
		longValue |= byteArray8[5] & 0xFF;
		longValue <<= 8;
		longValue |= byteArray8[4] & 0xFF;
		longValue <<= 8;
		longValue |= byteArray8[3] & 0xFF;
		longValue <<= 8;
		longValue |= byteArray8[2] & 0xFF;
		longValue <<= 8;
		longValue |= byteArray8[1] & 0xFF;
		longValue <<= 8;
		longValue |= byteArray8[0] & 0xFF;
		return longValue;
	}

	public static byte[] toBytes(int intValue) {
		byte[] byteValue = new byte[4];
		byteValue[0] = (byte) (intValue & 0xFF);
		byteValue[1] = (byte) ((intValue & 0xFF00) >> 8);
		byteValue[2] = (byte) ((intValue & 0xFF0000) >> 16);
		byteValue[3] = (byte) ((intValue & 0xFF000000) >> 24);
		return byteValue;
	}

	public static byte[] toBytes(long longValue) {
		byte[] byteValue = new byte[8];
		byteValue[0] = (byte) (int) (longValue & 0xFF);
		byteValue[1] = (byte) (int) ((longValue & 0xFF00) >> 8);
		byteValue[2] = (byte) (int) ((longValue & 0xFF0000) >> 16);
		byteValue[3] = (byte) (int) ((longValue & 0xFF000000) >> 24);
		byteValue[4] = (byte) (int) ((longValue & 0x0) >> 32);
		byteValue[5] = (byte) (int) ((longValue & 0x0) >> 40);
		byteValue[6] = (byte) (int) ((longValue & 0x0) >> 48);
		byteValue[7] = (byte) (int) ((longValue & 0x0) >> 56);
		return byteValue;
	}

	public static byte[] subBytes(byte[] buf, int from, int len) {
		byte[] subBuf = new byte[len];
		for (int i = 0; i < len; i++) {
			subBuf[i] = buf[(from + i)];
		}
		return subBuf;
	}

	public static String bytesToHexString(byte[] bytes) {
		if (bytes == null)
			return null;
		String table = "0123456789abcdef";
		StringBuilder ret = new StringBuilder(2 * bytes.length);

		for (int i = 0; i < bytes.length; i++) {
			int b = 0xF & bytes[i] >> 4;
			ret.append(table.charAt(b));
			b = 0xF & bytes[i];
			ret.append(table.charAt(b));
		}

		return ret.toString();
	}

	public static byte[] hexStringToBytes(String s) {
		if (s == null) {
			return null;
		}
		int sz = s.length();
		byte[] ret = new byte[sz / 2];
		for (int i = 0; i < sz; i += 2) {
			ret[(i / 2)] = (byte) (hexCharToInt(s.charAt(i)) << 4 | hexCharToInt(s
					.charAt(i + 1)));
		}

		return ret;
	}

	public static int hexCharToInt(char c) {
		if ((c >= '0') && (c <= '9'))
			return c - '0';
		if ((c >= 'A') && (c <= 'F'))
			return c - 'A' + 10;
		if ((c >= 'a') && (c <= 'f')) {
			return c - 'a' + 10;
		}
		throw new RuntimeException("invalid hex char '" + c + "'");
	}
}
