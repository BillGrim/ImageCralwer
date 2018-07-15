package com.rensanning.crawler.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// commons-codec  DigestUtils.java
public class MD5Checksum {
	private static final char[] DIGITS_LOWER = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	private static char[] encodeHex(byte[] data, char[] toDigits) {
		int l = data.length;
		char[] out = new char[l << 1];

		int i = 0;
		for (int j = 0; i < l; i++) {
			out[(j++)] = toDigits[((0xF0 & data[i]) >>> 4)];
			out[(j++)] = toDigits[(0xF & data[i])];
		}
		return out;
	}

	private static String encodeHexString(byte[] data) {
		return new String(encodeHex(data, DIGITS_LOWER));
	}

	private static MessageDigest updateDigest(MessageDigest digest, InputStream data) throws IOException {
		byte[] buffer = new byte[1024];
		int read = data.read(buffer, 0, 1024);
		while (read > -1) {
			digest.update(buffer, 0, read);
			read = data.read(buffer, 0, 1024);
		}
		return digest;
	}

	private static byte[] digest(MessageDigest digest, InputStream data) throws IOException {
		return updateDigest(digest, data).digest();
	}

	private static MessageDigest getDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private static MessageDigest getMd5Digest() {
		return getDigest("MD5");
	}

	private static byte[] md5(InputStream data) throws IOException {
		return digest(getMd5Digest(), data);
	}

	private static String md5Hex(InputStream data) throws IOException {
		return encodeHexString(md5(data));
	}

	public static String md5Hex(File file) throws IOException {
		String result = "";
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(file);
			result = md5Hex(fs);
		} finally {
			if (fs != null) {
				fs.close();
			}
		}
		return result;
	}
}
