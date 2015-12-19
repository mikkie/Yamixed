/**
 * 文章链接<br>
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

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.util.StringUtils;

import com.yamixed.base.entity.IdEntity;

/**
 * @author Administrator
 *
 */
@Entity
public class Link extends IdEntity{
	
	//临时id,不要持久化
	private Integer tempID;
	
	private String url;
	
	private String title;
	
	private String description;
	
	private String previewImgUrl;
	
	private Date createTime;
	
	private int up;
	
	private int down;
	
	private int hit;
	
	private Article article;
	
	private Boolean privated;
	
	private List<String> imageUrls;
	
	@Transient
	public List<String> getImageUrls() {
		return imageUrls;
	}

	public void setImageUrls(List<String> imageUrls) {
		this.imageUrls = imageUrls;
	}

	@Column(columnDefinition = "text")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(columnDefinition = "text")
	public String getTitle() {
		return StringUtils.isEmpty(title) ? "" : title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(columnDefinition = "text")
	public String getDescription() {
		return StringUtils.isEmpty(description) ? "" : description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(columnDefinition = "MEDIUMTEXT")
	public String getPreviewImgUrl() {
		return StringUtils.isEmpty(previewImgUrl) ? "" : previewImgUrl;
	}

	public void setPreviewImgUrl(String previewImgUrl) {
		this.previewImgUrl = previewImgUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	
	@ManyToOne
	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	@Transient
	public Integer getTempID() {
		return tempID;
	}

	public void setTempID(Integer tempID) {
		this.tempID = tempID;
	}

	
	@Column(columnDefinition="TINYINT")
	public Boolean getPrivated() {
		return privated;
	}

	public void setPrivated(Boolean privated) {
		this.privated = privated;
	}
	
	
}
