/**
 * 目录服务接口<br>
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
package com.yamixed.mix.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.yamixed.base.entity.Category;

/**
 * @author Administrator
 *
 */
public interface ICategoryService {
	
	public List<Category> findAllCategorys();
	
	
	public Category getCategoryById(Long cate);
	
	
	public List<Category> getAllCates(HttpServletRequest request);
	

}
