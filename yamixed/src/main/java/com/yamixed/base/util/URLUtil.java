/**
 * URL工具类<br>
 * ------------------------------------------------------------<br>
 * History<br>
 * ------------------------------------------------------------<br>
 * Legend:<br>
 * 　(+) added feature<br>
 * 　(-) deleted feature<br>
 * 　(#) fixed bug<br>
 * 　(^) upgraded implementation<br>
 *<br>
 * V1.00.00 2012-2-24 limj 新建
 * @author limj
 * @since V1.00.00
 */
package com.yamixed.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

/**
 * @author Administrator
 *
 */
public class URLUtil {
	
	private static final Pattern COMPILE = Pattern.compile("(.+)\\.(jpg|jpeg|png|gif|bmp)$");
	
	
	public static boolean isSimpleImageUrl(String url){
		if(StringUtils.isEmpty(url)){
			throw new IllegalArgumentException("url为空");
		}
		Matcher matcher = COMPILE.matcher(url.toLowerCase());
		return matcher.matches();
	}
	

}
