/**
 * Mix服务接口<br>
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

import org.springframework.data.domain.Page;

import com.yamixed.base.entity.Mix;
import com.yamixed.base.service.ICrudService;

/**
 * @author Administrator
 *
 */
public interface IMixService extends ICrudService<Mix>{
	
	/**
	 * 解析url
	 * @param mix
	 * @param url
	 */
	public boolean parseMixUrl(Mix mix,String url);
	
	
	/**
	 * 查询当周mix
	 * @return
	 */
	public List<Mix> queryDayMix();
	
	
	/**
	 * 热门mix
	 * @return
	 */
	public List<Mix> queryHotMix();
	
	
	
	/**
	 * 历史mix
	 * @return
	 */
	public Page<Mix> queryHistoryMix(int pageNum);
	
	
	/**
	 * 仪表盘
	 * @return
	 */
	public Page<Mix> queryDashBoard(int pageNum);


	/**
	 * 顶和踩
	 * @param isUp
	 * @param mixId
	 */
	public void upAndDown(boolean isUp, Long mixId);
	
	
	
	/**
	 * 统计评论数
	 * @param mixId
	 */
	public void addCommentsCount(Long mixId);


	/**
	 * 删除
	 * @param mixId
	 */
	public void delMix(Long mixId);


	
	/**
	 * 点击
	 * @param mixId
	 */
	public void hitMix(Long mixId);


	
	/**
	 * 根据分类获取Mix
	 * @param cate
	 * @param pageNum
	 * @return
	 */
	public Page<Mix> queryMixByCate(Long cate, int pageNum);
	

}
