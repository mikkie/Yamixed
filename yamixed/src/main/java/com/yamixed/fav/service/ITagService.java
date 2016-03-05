/**
 * 标签<br>
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
package com.yamixed.fav.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.yamixed.base.service.ICrudService;
import com.yamixed.fav.entity.Tag;

/**
 * @author Administrator
 *
 */
public interface ITagService extends ICrudService<Tag>{
	
	public List<Tag> findByChannelID(Long channelID);
	
	
	public Page<Tag> findMostPopularTagByChanndelID(final int pageNum,int pageSize,final Long channelID);
	
	
	public List<Tag> findByName(String name);


	public Page<Tag> findTagsByPage(final long channelId,final int pageNum,final int pageSize);
	
}
