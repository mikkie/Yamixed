/**
 * 评论控制类<br>
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

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yamixed.base.entity.Comment;
import com.yamixed.base.entity.Mix;
import com.yamixed.mix.service.ICategoryService;
import com.yamixed.mix.service.ICommentService;
import com.yamixed.mix.service.IMixService;

/**
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/comment")
public class CommentController {
	
	
	@Autowired
	private ICommentService commentService;
	
	@Autowired
	private IMixService mixService;
	
	@Autowired
	private  ICategoryService categoryService; 
	
	
	@RequestMapping("view")
	public String viewCommnet(@RequestParam("mixId")Long mixId,Model model,HttpServletRequest request){
		Mix mix = mixService.findOne(mixId);
		List<Comment> comments = commentService.findCommentByMix(mixId);
		model.addAttribute("mix", mix);
		model.addAttribute("comments", comments);
		model.addAttribute("allCates", categoryService.getAllCates(request));
		return "comment/comment";
	}
	
	
	@RequestMapping("new")
	public String saveNewComment(HttpServletRequest request){
		Long mixId = Long.valueOf(request.getParameter("mixId"));
	    String commentIdstr = request.getParameter("commentId");
	    Long commentId = StringUtils.isEmpty(commentIdstr) ? null : Long.valueOf(commentIdstr);
	    String content = request.getParameter("content");
	    try {
	    	commentService.createNewComment(mixId,commentId,content);
	        mixService.addCommentsCount(mixId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return "redirect:/comment/view?mixId=" + mixId;
	} 

}
