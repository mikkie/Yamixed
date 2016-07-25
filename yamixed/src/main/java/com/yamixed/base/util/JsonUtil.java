/**
 * json工具类<br>
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
package com.yamixed.base.util;

import java.io.IOException;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yamixed.api.v1.APIExceptionCode;

/**
 * @author Administrator
 *
 */
public class JsonUtil {
	
	public static String parseJsonToString(Object o){
		try {
			return new ObjectMapper().writeValueAsString(o);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static Map<String, Object> parseJson(String jsonString){
		if(StringUtils.isEmpty(jsonString)){
			return null;
		}
		try {
			return new ObjectMapper().readValue(jsonString, new TypeReference<Map<String, Object>>(){});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String outPutError(APIExceptionCode type,String code,String message){
		return String.format("{\"error\":{\"code\":\"%s\",\"message\":\"%s\"}}", type.getErrorCode(code),message);
	}
	
	public static String outPutSuccess(String json){
		return String.format("{\"success\":%s}", json);
	}

}
