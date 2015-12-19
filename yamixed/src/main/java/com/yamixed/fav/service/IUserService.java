/**
 * <br>
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
package com.yamixed.fav.service;

import javax.servlet.http.HttpServletRequest;

import com.yamixed.base.service.ICrudService;
import com.yamixed.fav.entity.User;

/**
 * @author Administrator
 *
 */
public interface IUserService extends ICrudService<User>{
	
	/**
	 * 登录
	 * @param request
	 * @return
	 */
	public User login(HttpServletRequest request);

}
