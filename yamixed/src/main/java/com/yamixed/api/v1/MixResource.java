/**
 * Mix API 服务
 */
package com.yamixed.api.v1;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.yamixed.base.entity.Mix;
import com.yamixed.base.util.JsonUtil;
import com.yamixed.mix.service.IMixService;

/**
 * @author xiaxue
 *
 */
@Component
@Path("/mix")
public class MixResource {
	
	@Autowired
	private IMixService mixService;
	
	@POST
	@Path("/new")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public String newMix(String jsonParam){
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

}
