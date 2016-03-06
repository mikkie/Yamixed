/**
 * 文章服务接口<br>
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

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;

import com.yamixed.base.service.ICrudService;
import com.yamixed.fav.entity.Article;
import com.yamixed.fav.entity.User;

/**
 * @author Administrator
 *
 */
public interface IArticleService extends ICrudService<Article>{
	
	public Page<Article> findByUserAndTagPage(final Long userID,final Long tagID,int page);
	
	public Article saveArticle(HttpServletRequest request);

	public Page<Article> findByTagPage(final Long tagid, int pageNum);

	public void upAndDown(boolean isUp, Long articleId);

	public Article updateArticle(Article article, HttpServletRequest request);
	
	/**
	 * 分页搜索标签，用户登录过则包含隐私标签，否则为公开标签 
	 * @param tagid
	 * @param pageNum
	 * @param user
	 * @return
	 */
	public Page<Article> searchByTagPage(final Long tagid,final int pageNum,final User user);
	
	public boolean needUpdateTag(Article article, HttpServletRequest request);

}
