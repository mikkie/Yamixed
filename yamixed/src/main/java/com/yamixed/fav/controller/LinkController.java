/**
 * 链接控制类<br>
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
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yamixed.base.constants.Constants;
import com.yamixed.mix.service.ILinkService;

/**
 * @author Administrator
 *
 */

@RequestMapping("/link")
@Controller
public class LinkController {
	
	
	@Autowired
	private ILinkService linkService;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/del/{id}",method=RequestMethod.GET)
	@ResponseBody
	public void del(@PathVariable("id")Long id,HttpServletRequest request){
		try {
			Object object = request.getSession().getAttribute(Constants.Session.DEL_LINK_IDS);
			if(!(object instanceof List)){
				object = new ArrayList<Long>();
				request.getSession().setAttribute(Constants.Session.DEL_LINK_IDS, object);
			}
			List<Long> ids = (List<Long>)object;
			ids.add(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
