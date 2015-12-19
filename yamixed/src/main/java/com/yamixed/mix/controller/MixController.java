/**
 * Mix控制类<br>
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
package com.yamixed.mix.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yamixed.base.entity.Category;
import com.yamixed.base.entity.Mix;
import com.yamixed.base.service.impl.CommonServiceUtil;
import com.yamixed.mix.service.ICategoryService;
import com.yamixed.mix.service.ICommentService;
import com.yamixed.mix.service.IMixService;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/mix")
public class MixController {
	
	private static final String NO_IMAGE = "static/images/no_image.jpg";
	
	@Autowired
	private IMixService mixService;
	
	@Autowired
	private ICommentService commentService;
	
	@Autowired
	private ServletContext context;
	
	@Autowired
	private CommonServiceUtil commonService;
	
	@Autowired
	private ICategoryService categoryService;
	
	
	@RequestMapping("new")
	public String newMix(@RequestParam("mixUrl") String mixUrl,Model model,HttpServletRequest request){
		Mix mix = new Mix();
		String temp = mixUrl.toLowerCase();
		if(!temp.startsWith("http")){
			mixUrl = "http://" + mixUrl;
		}
		mix.setUrl(mixUrl);
		boolean res = mixService.parseMixUrl(mix, mixUrl);
		if(res){
			model.addAttribute("newMix", mix);
			commonService.initModelData(model, request);
			return "mix/newMix";
		}
		return "redirect:/";
	}
	
	
	@RequestMapping(value="save",method=RequestMethod.POST)
	public String saveMix(HttpServletRequest request,@ModelAttribute("newMix")Mix mix){
		String url = request.getParameter("url");
		String title = request.getParameter("title");
		String desc = request.getParameter("desc");
		String imageUrl = request.getParameter("imageUrl");
		String cate = request.getParameter("category");
		if(StringUtils.isEmpty(imageUrl)){
			imageUrl = request.getRequestURL().toString();
			imageUrl = imageUrl.replace("mix/save", NO_IMAGE);
		}
		mix.setUrl(url);
		mix.setTitle(title);
		mix.setDescription(desc);
		mix.setPreviewImgUrl(imageUrl);
		mix.setCreateTime(new Date());
		Category category = buildCategory(cate);
		mix.setCate(category);
		mixService.save(mix);
		return "redirect:/";
	}
	
	
	
	@RequestMapping(value="update/{pageNum}",method=RequestMethod.POST)
	public String updateMix(HttpServletRequest request,@PathVariable("pageNum")int pageNum){
		String mixId = request.getParameter("mixId");
		String title = request.getParameter("title");
		String desc = request.getParameter("desc");
		String cate = request.getParameter("category");
		Mix mix = mixService.findOne(Long.valueOf(mixId));
		if(mix != null){
			mix.setTitle(title);
			mix.setDescription(desc);
			Category category = buildCategory(cate);
			mix.setCate(category);
			mixService.save(mix);
		}
		return "redirect:/mix/dashboard/" + pageNum;
	}
	
	
	
	/**
	 * 创建关联目录
	 * @param cate
	 */
	private Category buildCategory(String cate) {
		Long cateId = 1L;
		if(!StringUtils.isEmpty(cate)){
			cateId = Long.parseLong(cate);
		}
		Category category = new Category();
		category.setId(cateId);
		return category;
	}


	@RequestMapping(value="upAndDown",method=RequestMethod.GET)
	@ResponseBody
	public void upAndDownMix(@RequestParam("isUp")boolean isUp,@RequestParam("mixId")Long mixId){
		mixService.upAndDown(isUp,mixId);
	}
	
	
	@RequestMapping("top")
	public String viewTopMix(Model model,HttpServletRequest request){
		List<Mix> hotMixs = mixService.queryHotMix();
		model.addAttribute("mixs", hotMixs);
		commonService.initModelData(model, request);
		return "top/top";
	}
	
	@RequestMapping("history/{pageNum}")
	public String viewHistoryMix(Model model,@PathVariable("pageNum")int pageNum,HttpServletRequest request){
		if(pageNum < 1){
			pageNum = 1;
		}
		Page<Mix> historyMixs = mixService.queryHistoryMix(pageNum);
		if(historyMixs == null || CollectionUtils.isEmpty(historyMixs.getContent())){
			historyMixs = mixService.queryHistoryMix(1);
			pageNum = 1;
		}
		model.addAttribute("mixs", historyMixs.getContent());
		model.addAttribute("pages", historyMixs.getTotalPages() - 1);
		model.addAttribute("pageNum", pageNum);
		commonService.initModelData(model, request);
		return "history/history";
	}
	
	
	@RequestMapping("dashboard/{pageNum}")
	public String viewDashboard(Model model,@PathVariable("pageNum")int pageNum,HttpServletRequest request){
		if(pageNum < 0){
			pageNum = 0;
		}
		Page<Mix> allMixs = mixService.queryDashBoard(pageNum);
		if(allMixs == null || CollectionUtils.isEmpty(allMixs.getContent())){
			allMixs = mixService.queryDashBoard(0);
			pageNum = 0;
		}
		model.addAttribute("mixs", allMixs.getContent());
		model.addAttribute("pages", allMixs.getTotalPages() - 1);
		model.addAttribute("pageNum", pageNum);
		commonService.initModelData(model, request);
		return "dashboard/dashboard";
	}
	
	
	@RequestMapping("cateMix/{cate}/{pageNum}")
	public String viewMixsByCategory(Model model,@PathVariable("cate")Long cate,@PathVariable("pageNum")int pageNum,HttpServletRequest request){
		if(pageNum < 0){
			pageNum = 0;
		}
		Page<Mix> allMixs = mixService.queryMixByCate(cate,pageNum);
		Category category = categoryService.getCategoryById(cate);
		model.addAttribute("cate", category);
		model.addAttribute("mixs", allMixs.getContent());
		model.addAttribute("pages", allMixs.getTotalPages() - 1);
		model.addAttribute("pageNum", pageNum);
		commonService.initModelData(model, request);
		return "cateMix/cateMix";
	}
	
	
	
	
	@RequestMapping("delMix")
	public String delMix(@RequestParam("mixId")Long mixId,@RequestParam("pageNum")int pageNum){
		commentService.delCommentByMix(mixId);
		mixService.delMix(mixId);
		return "redirect:/mix/dashboard/" + pageNum;
	}
	
	
	@RequestMapping("hitMix")
	@ResponseBody
	public void hitMix(@RequestParam("mixId")Long mixId){
		mixService.hitMix(mixId);
	}
	

}
