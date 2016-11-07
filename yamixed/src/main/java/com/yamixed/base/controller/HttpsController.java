/**
 * 
 */
package com.yamixed.base.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author benjaminl
 *
 */
@Controller
@RequestMapping("/.well-known")
public class HttpsController {
	
	@ResponseBody
	@RequestMapping("/acme-challenge/EWpaSDPluUecDpyLPsGis3tHuWThfwKc8Is2SeT77Q8")
	public String forHttps(){
		return "EWpaSDPluUecDpyLPsGis3tHuWThfwKc8Is2SeT77Q8.SjOIxZUKLwzUPvfL9FwXxYroc-W1ja9aUO492SCNAnI";
	}

}
