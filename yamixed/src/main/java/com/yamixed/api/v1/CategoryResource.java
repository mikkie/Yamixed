/**
 * 
 */
package com.yamixed.api.v1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yamixed.base.util.JsonUtil;
import com.yamixed.mix.service.ICategoryService;

/**
 * @author benjaminl
 *
 */

@Component
@Path("/category")
public class CategoryResource {
	
	@Autowired
	private ICategoryService categoryService;
	
	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllCategories(){
		return JsonUtil.parseJsonToString(categoryService.findAllCategorys());
	}

}
