/**
 * 评论服务接口<br>
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
package com.yamixed.mix.service;

import java.util.List;

import com.yamixed.base.entity.Comment;
import com.yamixed.base.service.ICrudService;

/**
 * @author Administrator
 *
 */
public interface ICommentService extends ICrudService<Comment>{

	
	/**
	 * 根据mix id查询评论
	 * @param mixId
	 * @return
	 */
	public List<Comment> findCommentByMix(Long mixId);

	/**
	 * 创建新的评论
	 * @param mixId
	 * @param commentId
	 * @param content
	 */
	public void createNewComment(Long mixId, Long commentId, String content);
	
	
	/**
	 * 删除mix对应的评论
	 * @param mixId
	 */
	public void delCommentByMix(Long mixId);

}
