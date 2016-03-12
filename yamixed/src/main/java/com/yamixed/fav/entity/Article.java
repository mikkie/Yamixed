/**
 * 收藏夹<br>
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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yamixed.base.entity.IdEntity;
import com.yamixed.base.util.ImageUtil;

/**
 * @author Administrator
 * 
 */
@Entity
public class Article extends IdEntity {

	private String content;

	private Tag tag;

	private User user;

	private Date createTime;

	private int up;

	private int down;

	private List<Link> links;
	
	private Boolean privated;

	
	@Column(columnDefinition = "text")
	public String getContent() {
		if(StringUtils.isEmpty(content)){
			if(!CollectionUtils.isEmpty(links)){
				return links.get(0).getDescription();
			}
		}
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	@ManyToOne
	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "article")
	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Transient
	public List<String> getPreviewImgUrls() {
		List<String> urls = new ArrayList<String>(4);
		if (!CollectionUtils.isEmpty(links)) {
			Collections.reverse(links);
			int size = links.size();
			int len = size > 4 ? 4 : size; 
			for(int i = 0; i < len; i++){
				Link link = links.get(i);
				if(!StringUtils.isEmpty(link.getPreviewImgUrl())){
					urls.add(link.getPreviewImgUrl());
				}
			}
		}
		if(urls.size() < 4){
			for(int i = urls.size(); i < 4; i++){
				urls.add(ImageUtil.getDefaultImage());
			}
		}
		return urls;
	}

	public int getUp() {
		return up;
	}

	public void setUp(int up) {
		this.up = up;
	}

	public int getDown() {
		return down;
	}

	public void setDown(int down) {
		this.down = down;
	}

	@Column(columnDefinition="TINYINT")
	public Boolean getPrivated() {
		return privated;
	}

	public void setPrivated(Boolean privated) {
		this.privated = privated;
	}
	

}
