/**
 * 频道服务类<br>
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
package com.yamixed.fav.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yamixed.base.service.impl.CrudServiceImpl;
import com.yamixed.fav.dao.IChannelDao;
import com.yamixed.fav.entity.Channel;
import com.yamixed.fav.service.IChannelService;

/**
 * @author Administrator
 *
 */

@Service
@Transactional
public class ChannelServiceImpl extends CrudServiceImpl<Channel, IChannelDao> implements IChannelService{

	
	@Override
	@Autowired
	public void setDao(IChannelDao dao) {
		this.dao = dao;
	}
	
	
	public List<Channel> findAllChannels() {
		Iterable<Channel> all = dao.findAll();
		if(all != null){
			List<Channel> channels = new ArrayList<Channel>();
			Iterator<Channel> it = all.iterator();
			while(it.hasNext()){
				channels.add(it.next());
			}
			return channels;
		}
		return null;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Channel> getAllChannels(HttpServletRequest request) {
		ServletContext servletContext = request.getServletContext();
		Object allchannels = servletContext.getAttribute("allChannels");
		if(!(allchannels instanceof List)){
			List<Channel> channels = findAllChannels();
			servletContext.setAttribute("allChannels", channels);
			return channels;
		}
		return (List<Channel>)allchannels;
	}

}
