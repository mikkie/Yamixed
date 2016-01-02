/**
 * 书签控制器<br>
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
package com.yamixed.fav.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.yamixed.base.constants.Constants;
import com.yamixed.base.util.JsonUtil;
import com.yamixed.base.util.UserUtils;
import com.yamixed.fav.entity.Article;
import com.yamixed.fav.entity.Channel;
import com.yamixed.fav.entity.Link;
import com.yamixed.fav.entity.Tag;
import com.yamixed.fav.entity.User;
import com.yamixed.fav.service.IChannelService;
import com.yamixed.fav.service.ITagService;
import com.yamixed.fav.service.IUserService;
import com.yamixed.mix.service.IArticleService;
import com.yamixed.mix.service.ILinkService;

/**
 * @author Administrator
 * 
 */

@RequestMapping("/fav")
@Controller
public class FavController {

	@Autowired
	private IUserService userService;

	@Autowired
	private ITagService tagService;

	@Autowired
	private ILinkService linkService;

	@Autowired
	private IArticleService articleService;

	@Autowired
	private IChannelService channelService;

	/**
	 * 公共
	 * 
	 * @return
	 */
	@RequestMapping("/{channelid}")
	public String favPublic(@PathVariable("channelid") Long channelid,
			Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		List<Tag> tags = tagService.findMostPopularTagByChanndelID(channelid);
		Assert.notEmpty(tags, "无法获取最受欢迎的标签");
		String op = request.getParameter("op");
		if (!StringUtils.isEmpty(op)) {
			redirectAttributes.addAttribute("op", op);
		}
		String cb = request.getParameter("cb");
		if (!StringUtils.isEmpty(cb)) {
			redirectAttributes.addAttribute("cb", cb);
		}
		return "redirect:/fav/" + channelid + "/" + tags.get(0).getId() + "/0";
	}

	@RequestMapping("/{channelid}/{tagid}/{page}")
	public String favIndex(@PathVariable("channelid") Long channelid,
			@PathVariable("tagid") Long tagid,
			@PathVariable("page") Integer pageNum, Model model,
			HttpServletRequest request) {
		// 操作
		String op = request.getParameter("op");
		// 回调
		String cb = request.getParameter("cb");
		if (!StringUtils.isEmpty(op)) {
			model.addAttribute("op", op);
		}
		if (!StringUtils.isEmpty(cb)) {
			model.addAttribute("cb", cb);
		}
		Channel channel = channelService.findOne(channelid);
		model.addAttribute("channel", channel);
		List<Tag> tags = tagService.findMostPopularTagByChanndelID(channelid);
		model.addAttribute("tags", tags);
		Page<Article> articlePage = articleService
				.findByTagPage(tagid, pageNum);
		model.addAttribute("articles", articlePage.getContent());
		model.addAttribute("pages", articlePage.getTotalPages() - 1);
		model.addAttribute("pageNum", pageNum);
		return "/fav/public";
	}

	@RequestMapping("/myfav/{channelid}")
	public String myFav(@PathVariable("channelid") Long channelid,
			HttpServletRequest request, Model model) {
		Object o = request.getSession().getAttribute(Constants.Session.USER);
		// 防止盗入
		if (!(o instanceof User)) {
			return "redirect:/fav/" + channelid + "?op=login";
		}
		User currentUser = (User) o;
		User user = userService.findOne(currentUser.getId());
		List<Tag> tags = user.getTags();
		if (CollectionUtils.isEmpty(tags)) {
			model.addAttribute("editable", true);
			Channel channel = channelService.findOne(channelid);
			model.addAttribute("channel", channel);
			model.addAttribute("loginUser", currentUser);
			model.addAttribute("pageNum", 0);
			model.addAttribute("pages", 0);
			return "/fav/myfav";
		}
		Long userid = currentUser.getId();
		Long tagid = tags.get(0).getId();
		String url = "redirect:/fav/userfav/%s/%s/%s/0";
		url = String.format(url, channelid, userid, tagid);
		return url;
	}

	@RequestMapping("/userfav/{channelid}/{userid}/{tagid}/{page}")
	public String userFav(@PathVariable("channelid") Long channelid,
			@PathVariable("userid") Long userid,
			@PathVariable("tagid") Long tagid,
			@PathVariable("page") Integer pageNum, Model model,
			HttpServletRequest request) {
		Channel channel = channelService.findOne(channelid);
		model.addAttribute("channel", channel);
		model.addAttribute("tagid", tagid);
		User user = userService.findOne(userid);
		model.addAttribute("loginUser", user);
		Object object = request.getSession().getAttribute(
				Constants.Session.USER);
		if (object instanceof User
				&& user.getId().longValue() == ((User) object).getId()
						.longValue()) {
			model.addAttribute("editable", true);
		}
		List<Tag> tags = user.getTags();
		model.addAttribute("tags", tags);
		Page<Article> articlePage = articleService.findByUserAndTagPage(userid,
				tagid, pageNum);
		model.addAttribute("articles", articlePage.getContent());
		model.addAttribute("pages", articlePage.getTotalPages() - 1);
		model.addAttribute("pageNum", pageNum);
		return "/fav/myfav";
	}

	@RequestMapping("/article/{channelid}/{articleid}")
	public String viewArticle(@PathVariable("channelid") Long channelid,
			@PathVariable("articleid") Long articleid, Model model,
			HttpServletRequest request) {
		Channel channel = channelService.findOne(channelid);
		model.addAttribute("channel", channel);
		Article article = articleService.findOne(articleid);
		filterLinks(article,request);
		model.addAttribute("article", article);
		return "/fav/article";
	}

	/**
	 * 非当前用户过滤私有链接
	 * @param article
	 */
	private void filterLinks(Article article,HttpServletRequest request) {
		long articleOwner = article.getUser().getId().longValue();
		User currentUser = (User) request.getSession().getAttribute(
				Constants.Session.USER);
		//非当前用户
		if(currentUser == null || articleOwner != currentUser.getId().longValue()){
		   List<Link> links = article.getLinks();
		   List<Link> newLinks = new ArrayList<Link>();
		   if(!CollectionUtils.isEmpty(links)){
			   for(Link link : links){
				   if(link.getPrivated() == null || !link.getPrivated().booleanValue()){
					   newLinks.add(link);
				   }
			   }
			   article.setLinks(newLinks);
		   }
		}
	}

	@RequestMapping("/newarticle/{channelid}")
	public String newArticle(@PathVariable("channelid") Long channelid,
			Model model, HttpServletRequest request) {
		Channel channel = channelService.findOne(channelid);
		model.addAttribute("channel", channel);
		User currentUser = (User) request.getSession().getAttribute(
				Constants.Session.USER);
		// 防止盗入
		if (!(currentUser instanceof User)) {
			return "redirect:/fav/" + channelid + "?op=login";
		}
		String oldtag = request.getParameter("oldtag");
		if(!StringUtils.isEmpty(oldtag)){
			Tag tag = tagService.findOne(Long.valueOf(oldtag));
			if(tag != null){
				model.addAttribute("oldtag", tag.getName());
			}
		}
		List<Tag> tagList = tagService.findByChannelID(channelid);
		model.addAttribute("taglist", tagList);
		model.addAttribute("channelid", channelid);
		clearOldSessionData(request);
		return "/fav/newarticle";
	}

	@RequestMapping("/editarticle/{channelid}/{articleid}")
	public String editArticle(@PathVariable("channelid") Long channelid,
			@PathVariable("articleid") Long articleid, Model model,
			HttpServletRequest request) {
		Channel channel = channelService.findOne(channelid);
		model.addAttribute("channel", channel);
		User currentUser = (User) request.getSession().getAttribute(
				Constants.Session.USER);
		// 防止盗入
		if (!(currentUser instanceof User)) {
			return "redirect:/fav/" + channelid + "?op=login";
		}
		Article article = articleService.findOne(articleid);
		List<Tag> tagList = tagService.findByChannelID(channelid);
		model.addAttribute("taglist", tagList);
		model.addAttribute("article", article);
		model.addAttribute("channelid", channelid);
		clearOldSessionData(request);
		return "/fav/editarticle";
	}

	@RequestMapping("/parseLink")
	@ResponseBody
	public String parseUrl(@RequestParam("url") String url,
			HttpServletRequest request) {
		Link link = linkService.parseURL(url);
		if (link != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("link", link);
			addSession(link, request);
			return JsonUtil.parseJsonToString(map);
		}
		return "{\"error\":\"can not parse url\"}";
	}

	/**
	 * 选中预览图
	 * 
	 * @param tempid
	 * @param imgurl
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/selectPreviewImg")
	@ResponseBody
	public void selectPreviewImg(@RequestParam("tempid") String tempid,
			@RequestParam("imgurl") String imgurl,
			@RequestParam("title") String title,
			@RequestParam("desc") String desc,
			@RequestParam("privated") String privated,HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object linksObj = session.getAttribute(Constants.Session.LINKS);
		if (linksObj instanceof Map) {
			Map<Integer, Link> linksmap = (Map<Integer, Link>) linksObj;
			Link link = linksmap.get(Integer.valueOf(tempid));
			if (link != null) {
				link.setPreviewImgUrl(imgurl);
				link.setTitle(title);
				link.setDescription(desc);
				if("on".equals(privated)){
					link.setPrivated(true);
				}
				else{
					link.setPrivated(false);
				}
			}
		}
	}

	/**
	 * 暂存于session中
	 * 
	 * @param link
	 */
	@SuppressWarnings("unchecked")
	private void addSession(Link link, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object linksObj = session.getAttribute(Constants.Session.LINKS);
		if (!(linksObj instanceof Map)) {
			linksObj = new HashMap<Integer, Link>();
			session.setAttribute(Constants.Session.LINKS, linksObj);
		}
		Map<Integer, Link> linksmap = (Map<Integer, Link>) linksObj;
		String tempID = request.getParameter("tempID");
		int i = linksmap.size();
		if(!StringUtils.isEmpty(tempID)){
			i = Integer.valueOf(tempID);
		}
		else{
			Integer iByUrl = findIByUrl(linksmap,request.getParameter("url"));
			if(iByUrl != null){
				i = iByUrl;
			}
		}
		// 临时ID，用于前后台关联
		link.setTempID(i);
		linksmap.put(new Integer(i), link);
	}

	
	/**
	 * 找到相同url
	 * @param parameter
	 */
	private Integer findIByUrl(Map<Integer, Link> linksmap,String url) {
		if(CollectionUtils.isEmpty(linksmap)){
			return null;
		}
		for(Entry<Integer, Link> entry : linksmap.entrySet()){
			Link link = entry.getValue();
			if(link.getUrl().equals(url)){
				return entry.getKey();
			}
		}
		return null;
	}

	@RequestMapping("/saveArticle/{channelid}")
	public String saveArticle(@PathVariable("channelid") Long channelid,
			HttpServletRequest request) {
		Article article = articleService.saveArticle(request);
		if(article == null){
			return "redirect:/fav/myfav/" + channelid;
		}
		String url = "redirect:/fav/userfav/%s/%s/%s/0";
		url = String.format(url, channelid, article.getUser().getId(), article.getTag().getId());
		return url;
	}

	@RequestMapping("/updateArticle/{channelid}/{tagid}/{articleid}")
	public String updateArticle(@PathVariable("channelid") Long channelid,
			@PathVariable("tagid") Long tagid,
			@PathVariable("articleid") Long articleid,
			HttpServletRequest request) {
		Article article = articleService.updateArticle(articleid, request);
		if(article == null){
			return "redirect:/fav/myfav/" + channelid;
		}
		User currentUser = UserUtils.getCurrentUser(request);
		// 防止盗入
		if (!(currentUser instanceof User)) {
			return "redirect:/fav/" + channelid + "?op=login";
		}
		String url = "redirect:/fav/userfav/%s/%s/%s/0";
		url = String.format(url, channelid, currentUser.getId(), article.getTag().getId());
		return url;
	}

	@RequestMapping(value = "upAndDown", method = RequestMethod.GET)
	@ResponseBody
	public void upAndDownArticle(@RequestParam("isUp") boolean isUp,
			@RequestParam("articleId") Long articleId) {
		articleService.upAndDown(isUp, articleId);
	}

	@RequestMapping(value = "/link/upAndDown", method = RequestMethod.GET)
	@ResponseBody
	public void linkUpAndDownArticle(@RequestParam("isUp") boolean isUp,
			@RequestParam("linkId") Long linkId) {
		linkService.upAndDown(isUp, linkId);
	}

	@RequestMapping(value = "/login/{channelid}", method = RequestMethod.POST)
	public String login(@PathVariable("channelid") Long channelid,
			HttpServletRequest request) {
		User user = userService.login(request);
		if(user == null){
			return "redirect:/fav/" + channelid;
		}
		return "redirect:/fav/myfav/" + channelid;
	}

	@RequestMapping(value = "/logout/{channelid}", method = RequestMethod.GET)
	public String logout(@PathVariable("channelid") Long channelid,
			HttpServletRequest request) {
		request.getSession().setAttribute(Constants.Session.USER, null);
		return "redirect:/fav/" + channelid + "?op=logout";
	}

	/**
	 * 删除文章
	 * 
	 * @return
	 */
	@RequestMapping(value = "/del/{channelid}/{userid}/{tagid}/{articleid}", method = RequestMethod.GET)
	public String delArtice(@PathVariable("channelid") Long channelid,
			@PathVariable("userid") Long userid,
			@PathVariable("tagid") Long tagid,
			@PathVariable("articleid") Long articleid,
			HttpServletRequest request) {
		try {
			User currentUser = UserUtils.getCurrentUser(request);
			if (currentUser != null
					&& currentUser.getId().longValue() == userid.longValue()) {
				Article article = articleService.findOne(articleid);
				if(article != null){
					Tag tag = article.getTag();
					if(tag.getArticleCount() > 0){
						tag.setArticleCount(tag.getArticleCount() - 1);
					    tagService.save(tag);
					}
				}
				articleService.delete(articleid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = "redirect:/fav/userfav/%s/%s/%s/0";
		url = String.format(url, channelid, userid, tagid);
		return url;
	}
	
	
	/**
	 * 清除旧的session数据
	 */
	private void clearOldSessionData(HttpServletRequest request){
		request.getSession().removeAttribute(Constants.Session.LINKS);
		request.getSession().removeAttribute(Constants.Session.DEL_LINK_IDS);
	}
	
	
	/**
	 * 保存用户简介
	 */
	@RequestMapping(value = "/saveIntroduce", method = RequestMethod.POST)
	@ResponseBody
	public void saveIntroduce(HttpServletRequest request){
		String introduce = request.getParameter("introduce");
		if(StringUtils.isEmpty(introduce)){
			return;
		}
		User currentUser = UserUtils.getCurrentUser(request);
		if(currentUser != null){
			currentUser.setIntroduce(introduce);
			userService.save(currentUser);
		}
	}
	
	
	
	/**
	 * 用于qq登录回调
	 * @param request
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value="/qqcallback")
	public String qqLoginCallback(HttpServletRequest request,RedirectAttributes redirectAttributes){
		String channelId = request.getParameter("channelid");
		String tagId = request.getParameter("tagid");
		redirectAttributes.addAttribute("cb", "cb");
		return "forward:/fav/" + channelId + "/" + tagId + "/0";
	}
	
	
	
	/**
	 * 查询
	 * @param key
	 * @return
	 */
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public String search(@RequestParam("channelid")Long channelid,@RequestParam("key")String key,HttpServletRequest request,Model model){
		if(StringUtils.isEmpty(key)){
			return "redirect:/fav/" + channelid;
		}
		Object o = request.getSession().getAttribute(Constants.Session.USER);
		List<Link> links = linkService.findLinks(channelid,key,(User)o);
		Channel channel = channelService.findOne(channelid);
		model.addAttribute("channel", channel);
		model.addAttribute("key", key);
		model.addAttribute("links", links);
		return "fav/searchLink";
	}

}
