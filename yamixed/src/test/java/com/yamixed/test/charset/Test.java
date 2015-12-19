package com.yamixed.test.charset;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Test {
	
	public static void main(String[] args) {
		try {
			System.out.println(URLEncoder.encode("下午", "gb2312"));
			System.out.println(URLEncoder.encode("上午", "gb2312"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
