/**
 * 文章服务类<br>
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
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.yamixed.base.constants.Constants;
import com.yamixed.base.service.impl.CrudServiceImpl;
import com.yamixed.base.util.UserUtils;
import com.yamixed.fav.dao.ILinkDao;
import com.yamixed.fav.dao.ITagDao;
import com.yamixed.fav.entity.Article;
import com.yamixed.fav.entity.Channel;
import com.yamixed.fav.entity.Link;
import com.yamixed.fav.entity.Tag;
import com.yamixed.fav.entity.User;
import com.yamixed.mix.dao.IArticleDao;
import com.yamixed.mix.service.IArticleService;

/**
 * @author Administrator
 * 
 */
@Service
@Transactional
public class ArticleServiceImpl extends CrudServiceImpl<Article, IArticleDao>
		implements IArticleService {

	// 每日显示mix数
	private static final int DAY_MIX_SIZE = 30;

	@Autowired
	private ITagDao tagDao;

	@Autowired
	private ILinkDao linkDao;

	@Autowired
	@Override
	public void setDao(IArticleDao dao) {
		this.dao = dao;
	}

	public Article saveArticle(HttpServletRequest request) {
		try {
			// 处理标签
			Tag tag = handleTag(request);
			// 处理文章
			Article article = handleArticle(request, tag);
			// 处理链接
			handleLinks(request, article);
			save(article);
			return article;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			request.getSession().removeAttribute(Constants.Session.LINKS);
		}
		return null;
	}

	@Override
	public Article updateArticle(Article article, HttpServletRequest request) {
		try {
			if (article == null) {
				return null;
			}
			if(needUpdateTag(article,request)){
				Tag newTag = handleTag(request);
				updateOldTagCount(article.getTag());
				article.setTag(newTag);
			}
			article.setContent(request.getParameter("desc"));
			String privated = request.getParameter("privated");
			if("on".equals(privated)){
				article.setPrivated(true);
			}
			else{
				article.setPrivated(false);
			}
			handleLinks(request, article);
			delLinks(article, request);
			save(article);
			return article;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			request.getSession().removeAttribute(Constants.Session.LINKS);
		}
		return null;
	}

	
	private void updateOldTagCount(Tag tag) {
		Long articleCount = tag.getArticleCount();
		if(articleCount > 0){
			tag.setArticleCount(articleCount - 1);
			tagDao.save(tag);
		}
	}

	/**
	 * 是否更新标签
	 * @param article
	 * @param request
	 * @return
	 */
	public boolean needUpdateTag(Article article, HttpServletRequest request) {
		String customTag = request.getParameter("customTag");
		if(!StringUtils.isEmpty(customTag)){
			return true;
		}
		String selectedTag = request.getParameter("selectTagId");
		Long newTagID = Long.valueOf(selectedTag);
		Tag tag = article.getTag();
		return newTagID.longValue() != tag.getId().longValue();
	}

	@SuppressWarnings("unchecked")
	private void delLinks(Article article, HttpServletRequest request) {
		List<Link> links = article.getLinks();
		if (CollectionUtils.isEmpty(links)) {
			return;
		}
		Object object = request.getSession().getAttribute(
				Constants.Session.DEL_LINK_IDS);
		if (!(object instanceof List)) {
			return;
		}
		List<Long> ids = (List<Long>) object;
		if (CollectionUtils.isEmpty(ids)) {
			return;
		}
		Iterator<Link> it = links.iterator();
		try {
			while (it.hasNext()) {
				Link next = it.next();
				for (Long id : ids) {
					if (next.getId() != null
							&& next.getId().longValue() == id.longValue()) {
						linkDao.delete(id);
						next.setArticle(null);
						it.remove();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 处理链接
	 * 
	 * @param request
	 * @param article
	 */
	@SuppressWarnings("unchecked")
	private void handleLinks(HttpServletRequest request, Article article) {
		HttpSession session = request.getSession();
		Object linksObj = session.getAttribute(Constants.Session.LINKS);
		if (linksObj instanceof Map) {
			Map<Integer, Link> linksmap = (Map<Integer, Link>) linksObj;
			List<Link> links = new ArrayList<Link>();
			for (Entry<Integer, Link> entry : linksmap.entrySet()) {
				Link value = entry.getValue();
				value.setArticle(article);
				links.add(value);
			}
			List<Link> oldLinks = article.getLinks();
			if (CollectionUtils.isEmpty(oldLinks)) {
				article.setLinks(links);
			} else {
				oldLinks.addAll(links);
			}
		}
	}

	/**
	 * 处理文章
	 * 
	 * @param request
	 */
	private Article handleArticle(HttpServletRequest request, Tag tag) {
		String desc = request.getParameter("desc");
		String privated = request.getParameter("privated");
		Article article = new Article();
		article.setContent(desc);
		article.setCreateTime(new Date());
		article.setTag(tag);
		if("on".equals(privated)){
			article.setPrivated(true);
		}
		else{
			article.setPrivated(false);
		}
		User currentUser = UserUtils.getCurrentUser(request);
		article.setUser(currentUser);
		return article;
	}

	/**
	 * 标签处理
	 * 
	 * @param request
	 * @return
	 */
	private Tag handleTag(HttpServletRequest request) {
		String customTag = request.getParameter("customTag");
		String selectedTag = request.getParameter("selectTagId");
		User currentUser = UserUtils.getCurrentUser(request);
		Tag tag = null;
		if (!StringUtils.isEmpty(customTag)) {
			List<Tag> tags = tagDao.findByName(customTag.trim());
			if (CollectionUtils.isEmpty(tags)) {
				tag = new Tag();
				String channelID = request.getParameter("channelid");
				Channel channel = new Channel();
				channel.setId(Long.valueOf(channelID));
				tag.setChannel(channel);
				tag.setName(customTag);
				tag.setArticleCount(1L);
				List<User> users = new ArrayList<User>();
				users.add(currentUser);
				tag.setUsers(users);
				return tag;
			}
			else{
				tag = tags.get(0);
			}
		}
		else{
			tag = tagDao.findOne(Long.valueOf(selectedTag));
		}
		tag.setArticleCount(tag.getArticleCount() + 1L);
		if (!tag.containsUser(currentUser)) {
			tag.addUser(currentUser);
		}
		return tag;
	}

	/**
	 * 按用户和标签查询
	 */
	@Override
	public Page<Article> findByUserAndTagPage(final Long userID,
			final Long tagID, int page) {
		PageRequest pr = new PageRequest(page, DAY_MIX_SIZE, new Sort(
				Direction.DESC, "createTime"));
		Specification<Article> articleSpec = new Specification<Article>() {

			@Override
			public Predicate toPredicate(Root<Article> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<Object> userPath = root.get("user").get("id");
				Path<Object> tagPath = root.get("tag").get("id");
				query.where(cb.and(cb.equal(userPath, userID),
						cb.equal(tagPath, tagID)));
				return null;
			}
		};
		return (Page<Article>) dao.findAll(articleSpec, pr);
	}

	@Override
	public Page<Article> findByTagPage(final Long tagid, int pageNum) {
		PageRequest pr = new PageRequest(pageNum, DAY_MIX_SIZE, new Sort(
				Direction.DESC, "createTime"));
		Specification<Article> articleSpec = new Specification<Article>() {

			@Override
			public Predicate toPredicate(Root<Article> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				Path<Object> tagPath = root.get("tag").get("id");
				Path<Boolean> privatedPath = root.get("privated");
				predicates.add(cb.equal(tagPath, tagid));
				predicates.add(cb.equal(privatedPath, false));
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		};
		return (Page<Article>) dao.findAll(articleSpec, pr);
	}

	@Override
	public void upAndDown(boolean isUp, Long articleId) {
		Article article = dao.findOne(articleId);
		if (isUp) {
			article.setUp(article.getUp() + 1);
		} else {
			article.setDown(article.getDown() + 1);
		}
	}

	@Override
	public Page<Article> searchByTagPage(final Long tagid,final int pageNum,final User user) {
		PageRequest pr = new PageRequest(pageNum, DAY_MIX_SIZE, new Sort(
				Direction.DESC, "id"));
		Specification<Article> articleSpec = new Specification<Article>() {

			@Override
			public Predicate toPredicate(Root<Article> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				Path<Object> tagPath = root.get("tag").get("id");
				predicates.add(cb.equal(tagPath, tagid));
				Path<Boolean> privatedPath = root.get("privated");
				//未登录使用公开
				if(user == null){
					predicates.add(cb.equal(privatedPath, false));
				}
				//登录为公开及隐私
				else{
					Path<Long> userIdPath = root.get("user").get("id");
					Predicate pre2 = cb.and(cb.equal(privatedPath, true),cb.equal(userIdPath, user.getId()));
					predicates.add(cb.or(cb.equal(privatedPath, false),pre2));
				}
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		};
		return (Page<Article>) dao.findAll(articleSpec, pr);
	}

}
