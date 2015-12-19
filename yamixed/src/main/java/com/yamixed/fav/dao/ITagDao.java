/**
 * 标签接口<br>
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
import com.yamixed.fav.entity.Channel;
import com.yamixed.fav.entity.Tag;

/**
 * @author Administrator
 *
 */
public interface ITagDao extends CrudDao<Tag, Long>{
	
	public List<Tag> findByChannel(Channel channel);
	
	public List<Tag> findByName(String name);

}
