/**
 * user Dao<br>
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
package com.yamixed.fav.dao;

import java.util.List;

import com.yamixed.base.dao.CrudDao;
import com.yamixed.fav.entity.User;

/**
 * @author Administrator
 *
 */
public interface IUserDao extends CrudDao<User, Long>{
	
	public List<User> findByOpenId(String openid);

}
