/**
 * 首页<br>
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yamixed.base.constants.Constants;
import com.yamixed.base.entity.Mix;
import com.yamixed.base.service.impl.CommonServiceUtil;
import com.yamixed.mix.service.IMixService;

/**
 * @author Administrator
 *
 */

@Controller
public class IndexController {
	
	@Autowired
	private IMixService mixService;
	
	@Autowired
	private CommonServiceUtil commonService;
	
	@RequestMapping("/")
	public String index(Model model,HttpServletRequest request){
		List<Mix> dayMixs = mixService.queryDayMix();
		model.addAttribute("mixs", dayMixs);
		commonService.initModelData(model, request);
		return "index";
	}
	
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request){
		request.getSession().setAttribute(Constants.Session.USER, null);
		return "redirect:/";
	}

}
