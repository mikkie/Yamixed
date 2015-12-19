/**
 * 图片工具类<br>
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

import java.util.HashMap;
import java.util.Map;
import org.springframework.util.StringUtils;

/**
 * @author Administrator
 *
 */
public class ImageUtil {
	
	private static Map<String,String> map = new HashMap<String,String>();
	
	public static String getDefaultImage(){
		String key = "default_img";
		String defaultImg = map.get(key);
		if(StringUtils.isEmpty(defaultImg)){
			defaultImg = PathUtil.getContextPath() + "/static/images/no_image.jpg";
		    map.put(key, defaultImg);
		}
		return defaultImg;
	}

}
