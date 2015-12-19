/**
 * 评论服务类<br>
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
package com.yamixed.mix.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.yamixed.base.entity.Comment;
import com.yamixed.base.entity.Mix;
import com.yamixed.base.service.impl.CrudServiceImpl;
import com.yamixed.mix.dao.ICommentDao;
import com.yamixed.mix.service.ICommentService;

/**
 * @author Administrator
 *
 */
@Service
@Transactional
public class CommentServiceImpl extends CrudServiceImpl<Comment, ICommentDao> implements ICommentService{

	@Autowired
	@Override
	public void setDao(ICommentDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Comment> findCommentByMix(Long mixId) {
		Mix mix = new Mix();
		mix.setId(mixId);
		List<Comment> comments = dao.findByMix(mix);
	    if(!CollectionUtils.isEmpty(comments)){
	    	return sort(comments);
	    }
		return comments;
	}

	/**
	 * 排序
	 * @param comments
	 */
	private List<Comment> sort(List<Comment> comments) {
		Collections.sort(comments);
		List<Comment> result = new ArrayList<Comment>();
		Iterator<Comment> it = comments.iterator();
		while(it.hasNext()){
			Comment next = it.next();
			if(next.getParent() == null){
				result.add(next);
			}
			else{
				Comment parent = next.getParent();
				int i = result.indexOf(parent);
				int j = i + 1;
				if(j >= result.size()){
					result.add(next);
				}
				else{
					Comment comment = result.get(j);
					boolean added = false;
					while(comment.getParent() == next.getParent()){
						j++;
						if(j >= result.size()){
							result.add(next);
							added = true;
							break;
						}
						else{
							comment = result.get(j);
						}
					}
					if(!added){
						result.add(j,next);
					}
				}
			}
		}
		tagLevel(result);
		return result;
	}

	
	/**
	 * 标记层级
	 * @param result
	 */
	private void tagLevel(List<Comment> result) {
		for(Comment comment : result){
			comment.setPos(result.indexOf(comment));
			Comment parent = comment.getParent();
			if(parent != null){
				comment.setParent_pos(result.indexOf(parent));
			}
		}
	}

	@Override
	public void createNewComment(Long mixId, Long commentId, String content) {
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setCreateTime(new Date());
		Mix mix = new Mix();
		mix.setId(mixId);
		comment.setMix(mix);
		if(commentId != null){
			Comment parent = new Comment();
			parent.setId(commentId);
			comment.setParent(parent);
		}
		dao.save(comment);
	}

	@Override
	public void delCommentByMix(Long mixId) {
		Mix mix = new Mix();
		mix.setId(mixId);
		List<Comment> comments = dao.findByMix(mix);
		if(!CollectionUtils.isEmpty(comments)){
			dao.delete(comments);
		}
	}

}
