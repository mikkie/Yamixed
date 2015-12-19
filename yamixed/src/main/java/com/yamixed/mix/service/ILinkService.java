/**
 * 链接服务接口<br>
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

import com.yamixed.base.service.ICrudService;
import com.yamixed.fav.entity.Link;

/**
 * @author Administrator
 *
 */
public interface ILinkService extends ICrudService<Link>{

	/**
	 * 解析URL
	 * @param url
	 * @return
	 */
	public Link parseURL(String url);

	public void upAndDown(boolean isUp, Long linkId);

}
