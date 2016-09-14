package com.zqxx.common.dao.support;


import java.io.UnsupportedEncodingException;

import sun.misc.BASE64Encoder;

/**
 * 将字符串进行base64编码
 * @author Administrator
 *
 */
@SuppressWarnings("restriction")
public class StrEncode {
	public static String str2base64(String fileName){
		BASE64Encoder base64Encoder = new BASE64Encoder();
		try {
			return "=?UTF-8?B?"
					+ new String(base64Encoder.encode(fileName
							.getBytes("UTF-8"))) + "?=";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
