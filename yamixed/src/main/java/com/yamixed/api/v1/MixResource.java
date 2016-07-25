/**
 * Mix API 服务
 */
package com.yamixed.api.v1;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.yamixed.base.entity.Category;
import com.yamixed.base.entity.Mix;
import com.yamixed.base.util.JsonUtil;
import com.yamixed.base.util.StringUtil;
import com.yamixed.mix.service.IMixService;

/**
 * @author xiaxue
 *
 */
@Component
@Path("/mix")
public class MixResource {
	
	private static final String NO_IMAGE = "static/images/no_image.jpg";
	
	@Autowired
	private IMixService mixService;
	
	@POST
	@Path("/parse")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public String parseLink(String jsonParam){
		Map<String, Object> map = JsonUtil.parseJson(jsonParam);
		if(map == null || map.get("mixUrl") == null){
			return JsonUtil.outPutError(APIExceptionCode.Client,APIExceptionCode.PARAMS_MISSING, "mixUrl is missing");
		}
		String mixUrl = map.get("mixUrl").toString();
		if(StringUtils.isEmpty(mixUrl)){
			return JsonUtil.outPutError(APIExceptionCode.Client,APIExceptionCode.PARAMS_MISSING, "mixUrl is missing");
		}
		Mix mix = new Mix();
		String temp = mixUrl.toLowerCase();
		if(!temp.startsWith("http")){
			mixUrl = "http://" + mixUrl;
		}
		mix.setUrl(mixUrl);
		boolean res = mixService.parseMixUrl(mix, mixUrl);
		if(res){
			return JsonUtil.outPutSuccess(JsonUtil.parseJsonToString(mix));
		}
		return JsonUtil.outPutError(APIExceptionCode.Server,APIExceptionCode.PARSE_URL_ERROR, "can not parse mixUrl");
	}
	
	private String getStringParam(Map<String, Object> map,String key){
		Object object = map.get(key);
		if(object == null){
			return null;
		}
		return object.toString();
	}
	
	@POST
	@Path("/new")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public String newMix(@Context HttpServletRequest request,String jsonParam){
		Map<String, Object> map = JsonUtil.parseJson(jsonParam);
		if(map == null){
			return JsonUtil.outPutError(APIExceptionCode.Client,APIExceptionCode.PARAMS_MISSING, "params is missing");
		}
		String url = getStringParam(map, "url");
		String title = getStringParam(map, "title");
		String desc = getStringParam(map, "desc");
		String category = getStringParam(map, "category");
		if(StringUtil.hasEmpty(url,title,desc,category)){
			return JsonUtil.outPutError(APIExceptionCode.Client,APIExceptionCode.PARAMS_MISSING, "params is missing");
		}
		String imageUrl = getStringParam(map, "imageUrl");
		if(StringUtils.isEmpty(imageUrl)){
			imageUrl = request.getRequestURL().toString();
			imageUrl = imageUrl.replace("rest/api/v1/mix/new", NO_IMAGE);
		}
		Mix mix = new Mix();
		mix.setUrl(url);
		mix.setTitle(title);
		mix.setDescription(desc);
		mix.setPreviewImgUrl(imageUrl);
		mix.setCreateTime(new Date());
		try {
			Category cate = buildCategory(category);
			mix.setCate(cate);
			mixService.save(mix);
			String json = JsonUtil.parseJsonToString(mix);
			return JsonUtil.outPutSuccess(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return JsonUtil.outPutError(APIExceptionCode.Server,APIExceptionCode.CREATE_MIX_ERROR, "save mix failed");
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

}
