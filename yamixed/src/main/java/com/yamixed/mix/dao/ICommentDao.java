/**
 * 评论dao<br>
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
package com.yamixed.mix.dao;

import java.util.List;

import com.yamixed.base.dao.CrudDao;
import com.yamixed.base.entity.Comment;
import com.yamixed.base.entity.Mix;

/**
 * @author Administrator
 *
 */
public interface ICommentDao extends CrudDao<Comment, Long>{
	
	
	public List<Comment> findByMix(Mix mix);

}
