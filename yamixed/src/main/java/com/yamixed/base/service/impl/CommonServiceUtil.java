/**
 * 通用服务类<br>
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
package com.yamixed.base.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.yamixed.fav.service.IChannelService;
import com.yamixed.mix.service.ICategoryService;

/**
 * @author Administrator
 *
 */
@Service
public class CommonServiceUtil {

	
	@Autowired
	private ICategoryService categoryService; 
	
	@Autowired
	private IChannelService channelService; 
	
	
	/**
	 * 初始化 model数据
	 * @param model
	 * @param request
	 */
	public void initModelData(Model model,HttpServletRequest request){
		model.addAttribute("allCates", categoryService.getAllCates(request));
		model.addAttribute("allChannels", channelService.getAllChannels(request));
	}
	
	
}
