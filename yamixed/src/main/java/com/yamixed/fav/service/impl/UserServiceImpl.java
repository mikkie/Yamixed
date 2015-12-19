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
package com.yamixed.fav.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.yamixed.base.constants.Constants;
import com.yamixed.base.service.impl.CrudServiceImpl;
import com.yamixed.fav.dao.IUserDao;
import com.yamixed.fav.entity.User;
import com.yamixed.fav.service.IUserService;

/**
 * @author Administrator
 *
 */

@Service
@Transactional
public class UserServiceImpl extends CrudServiceImpl<User, IUserDao> implements IUserService{
	
	private static Logger log = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	@Override
	public void setDao(IUserDao dao) {
		this.dao = dao;
	}

	@Override
	public User login(HttpServletRequest request) {
		String openid = request.getParameter("openId");
		String accessToken = request.getParameter("accessToken");
		String avatar = request.getParameter("avatar");
		String nickname = request.getParameter("nickname");
		List<User> list = dao.findByOpenId(openid);
		User user = null;
		if(list.size() > 1){
			user = list.get(0);
			log.error(String.format("账号异常,openid:%s", openid));
		}else if(CollectionUtils.isEmpty(list)){
			user = register(openid,accessToken,avatar,nickname);
		}else{
			user = list.get(0);
			updateUserInfo(user,accessToken,avatar,nickname);
		}
		if(user != null){
			request.getSession().setAttribute(Constants.Session.USER, user);
			Hibernate.initialize(user.getTags());
		}
		return user;
	}

	
	/**
	 * 更新用户信息
	 * @param user
	 * @param accessToken
	 * @param avatar
	 * @param nickname
	 */
	@Transactional(readOnly=false)
	private void updateUserInfo(User user, String accessToken, String avatar,
			String nickname) {
		user.setAccessToken(accessToken);
		user.setAvatar(avatar);
		user.setName(nickname);
		dao.save(user);
	}

	/**
	 * 注册
	 * @param openid
	 * @param accessToken
	 * @param avatar
	 */
	@Transactional(readOnly=false)
	private User register(String openid, String accessToken, String avatar,String nickname) {
		User user = new User();
		user.setOpenId(openid);
		user.setAccessToken(accessToken);
		user.setAvatar(avatar);
		user.setName(nickname);
		user.setIntroduce("");
		dao.save(user);
		return user;
	}

}
