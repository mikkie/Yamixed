/**
 * 标签服务<br>
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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yamixed.base.service.impl.CrudServiceImpl;
import com.yamixed.fav.dao.ITagDao;
import com.yamixed.fav.entity.Channel;
import com.yamixed.fav.entity.Tag;
import com.yamixed.fav.service.ITagService;

/**
 * @author Administrator
 *
 */
@Service
@Transactional
public class TagServiceImpl extends CrudServiceImpl<Tag, ITagDao> implements ITagService{

	
	@Autowired
	@Override
	public void setDao(ITagDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Tag> findByChannelID(Long channelID) {
		Channel channel = new Channel();
		channel.setId(channelID);
		return dao.findByChannel(channel);
	}

	@Override
	public List<Tag> findMostPopularTagByChanndelID(final Long channelID) {
		Specification<Tag> tagSpec = new Specification<Tag>() {

			@Override
			public Predicate toPredicate(Root<Tag> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<Object> channelPath = root.get("channel").get("id");
				query.where(cb.equal(channelPath, channelID));
				return null;
			}
		};
		return dao.findAll(tagSpec, new Sort(Direction.DESC, "hit"));
	}

	@Override
	public List<Tag> findByName(String name) {
		return dao.findByName(name);
	}

}