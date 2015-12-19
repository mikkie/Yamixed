/**
 * 路径工具类<br>
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
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Administrator
 *
 */
public class PathUtil {
	
	//上下文
	public static String CONTEXT_PATH = "context_path";
	
	private static Map<String,String> map = new HashMap<String,String>();
	
	public static String getContextPath(){
		String context_path = map.get(CONTEXT_PATH);
		if(StringUtils.isEmpty(context_path)){
			HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
			context_path = request.getContextPath();
			map.put(CONTEXT_PATH, context_path);
		}
		return context_path;
	}

}
