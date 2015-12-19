/**
 * 用户工具类<br>
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

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.yamixed.base.constants.Constants;
import com.yamixed.fav.entity.User;

/**
 * @author Administrator
 *
 */
public class UserUtils {
	
	
	/**
	 * 获取当前用户
	 * @return
	 */
	public static User getCurrentUser(HttpServletRequest request){
		if(request == null){
			ServletRequestAttributes sr = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			request = sr.getRequest();
			if(request == null){
				return null;
			}
		}
		return (User)request.getSession().getAttribute(Constants.Session.USER);
	}

}
