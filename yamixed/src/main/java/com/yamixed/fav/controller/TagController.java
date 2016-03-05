/**
 * 标签控制器
 */
package com.yamixed.fav.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yamixed.fav.entity.Tag;
import com.yamixed.fav.service.ITagService;

/**
 * @author xiaxue
 *
 */
@Controller
@RequestMapping("/tag")
public class TagController { 
	
	@Autowired
	private ITagService tagService;
	
	@ResponseBody
	@RequestMapping("/findTagsByPage/{channelId}/{pageNum}/{pageSize}")
	public String findTagsByPage(@PathVariable("channelId")long channelId,@PathVariable("pageNum")int pageNum,@PathVariable("pageSize")int pageSize){
		Page<Tag> page = tagService.findTagsByPage(channelId,pageNum,pageSize);
		try {
			return new ObjectMapper().writeValueAsString(page);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "{\"error\":\"data_parse_error\"}";
	}

}
