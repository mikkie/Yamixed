/**
 * 文章控制器
 */
package com.yamixed.fav.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yamixed.base.util.JsonUtil;
import com.yamixed.base.util.UserUtils;
import com.yamixed.fav.entity.Article;
import com.yamixed.fav.entity.Tag;
import com.yamixed.fav.entity.User;
import com.yamixed.fav.service.ITagService;
import com.yamixed.mix.service.IArticleService;

/**
 * @author xiaxue
 *
 */
@Controller
@RequestMapping("/article")
public class ArticleController {
	
	
	@Autowired
	private ITagService tagService;
	
	
	@Autowired
	private IArticleService articleService;
	
	
	/**
	 * 获取用户的文章
	 * @param tagid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/findUserArticles/{tagid}")
	public String findUserArticles(@PathVariable("tagid")Long tagid,HttpServletRequest request){
		Tag tag = tagService.findOne(tagid);
		User user = UserUtils.getCurrentUser(request);
		if(tag == null || user == null){
			return "{\"error\":\"no_data\"}";
		}
		List<Article> artciles = articleService.findByUserAndTag(user, tag);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("success", artciles);
		return JsonUtil.parseJsonToString(result);
	}

}
