/**
 * 用户<br>
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
package com.yamixed.fav.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yamixed.base.entity.IdEntity;

/**
 * @author Administrator
 *
 */

@Entity
public class User extends IdEntity{
	
	private String name;
	
	private String introduce;
	
	private String avatar;
	
	private String openId;
	
	private String accessToken;
	
	private List<Tag> tags;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * 多对多关系被维护端
	 * @return
	 */
	@JsonIgnore
	@ManyToMany(cascade=CascadeType.REFRESH,mappedBy="users")
	public List<Tag> getTags() {
		return tags;
	}
	
	
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
	
	/**
	 * 分页获取标签
	 * @return
	 */
	@Transient
	@JsonIgnore
	public Page<Tag> getTagsByPage(Long tagId,int pageNum,int pageSize){
		if(CollectionUtils.isEmpty(tags)){
			return new PageImpl<Tag>(new ArrayList<Tag>(),new PageRequest(0, pageSize, new Sort(Direction.DESC, "id")),0); 
		}
		//有标签id则计算页数
		if(tagId != null){
			for(int i = 0; i < tags.size(); i++){
				if(tags.get(i).getId().longValue() == tagId){
					pageNum = i /40;
				}
			}
		}
		PageRequest pr = new PageRequest(pageNum, pageSize, new Sort(Direction.DESC, "id"));
		int start = pageNum * pageSize;
		List<Tag> result = new ArrayList<Tag>();
		for(int i = start; i < start + pageSize && i < tags.size(); i++){
			result.add(tags.get(i));
		}
		return new PageImpl<Tag>(result,pr,tags.size()); 
	}
	
	
}
