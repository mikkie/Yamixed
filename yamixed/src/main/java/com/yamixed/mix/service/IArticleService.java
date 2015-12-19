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

/**
 * @author Administrator
 *
 */
public interface IArticleService extends ICrudService<Article>{
	
	public Page<Article> findByUserAndTagPage(final Long userID,final Long tagID,int page);
	
	public Article saveArticle(HttpServletRequest request);

	public Page<Article> findByTagPage(final Long tagid, int pageNum);

	public void upAndDown(boolean isUp, Long articleId);

	public Article updateArticle(Long articleid, HttpServletRequest request);

}
