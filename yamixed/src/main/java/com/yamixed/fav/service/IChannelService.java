/**
 * 频道服务接口<br>
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

import javax.servlet.http.HttpServletRequest;

import com.yamixed.base.service.ICrudService;
import com.yamixed.fav.entity.Channel;

/**
 * @author Administrator
 *
 */
public interface IChannelService extends ICrudService<Channel>{

	
	public List<Channel> getAllChannels(HttpServletRequest request);

}
