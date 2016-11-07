/**
 * 
 */
package com.yamixed.chrome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author benjaminl
 *
 */

@Controller
@RequestMapping("/chrome")
public class ChromeController {
	
	@RequestMapping("/view")
	public String view(){
		return "/chrome/chrome";
	}

}
