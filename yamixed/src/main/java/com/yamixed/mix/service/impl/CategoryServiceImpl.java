/**
 * 目录服务类<br>
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
package com.yamixed.mix.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.yamixed.base.entity.Category;
import com.yamixed.base.service.impl.CrudServiceImpl;
import com.yamixed.mix.dao.ICategoryDao;
import com.yamixed.mix.service.ICategoryService;

/**
 * @author Administrator
 *
 */

@Service
@Transactional
public class CategoryServiceImpl extends CrudServiceImpl<Category, ICategoryDao> implements ICategoryService{

	@Autowired
	private HttpServletRequest request;
	
	
	@Autowired
	@Override
	public void setDao(ICategoryDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Category> findAllCategorys() {
		Iterable<Category> all = dao.findAll();
		if(all != null){
			List<Category> cates = new ArrayList<Category>();
			Iterator<Category> it = all.iterator();
			while(it.hasNext()){
				cates.add(it.next());
			}
			return cates;
		}
		return null;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public Category getCategoryById(Long cate) {
		ServletContext servletContext = request.getServletContext();
		Object allCates = servletContext.getAttribute("allCates");
		if(!(allCates instanceof List)){
			allCates = findAllCategorys();
			servletContext.setAttribute("allCates", allCates);
		}
		List<Category> cates = (List<Category>)allCates;
		if(CollectionUtils.isEmpty(cates)){
			return null;
		}
		for(Category category : cates){
			if(category.getId().longValue() == cate.longValue()){
				return category;
			}
		}
		return null;
	}

	
	
	/**
	 * 获取所有的分类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAllCates(HttpServletRequest request) {
		ServletContext servletContext = request.getServletContext();
		Object allCates = servletContext.getAttribute("allCates");
		if(!(allCates instanceof List)){
			List<Category> categorys = findAllCategorys();
			servletContext.setAttribute("allCates", categorys);
			return categorys;
		}
		return (List<Category>)allCates;
	}
}
