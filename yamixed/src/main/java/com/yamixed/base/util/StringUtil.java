/**
 * 
 */
package com.yamixed.base.util;

import org.springframework.util.StringUtils;

/**
 * @author xiaxue
 *
 */
public class StringUtil {

	public static boolean hasEmpty(String... strs){
		for(String str : strs){
			if(StringUtils.isEmpty(str)){
				return true;
			}
		}
		return false;
	}
	
}
