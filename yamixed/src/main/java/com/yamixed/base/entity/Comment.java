/**
 * 评论<br>
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
package com.yamixed.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * @author Administrator
 * 
 */
@Entity
public class Comment extends IdEntity implements Comparable<Comment> {

	private String content;

	private Date createTime;

	private Mix mix;

	private Comment parent;

	// 位置
	private int pos;

	// 引用评论位置
	private int parent_pos;

	
	@Column(columnDefinition = "text")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@ManyToOne
	public Mix getMix() {
		return mix;
	}

	public void setMix(Mix mix) {
		this.mix = mix;
	}

	@ManyToOne
	public Comment getParent() {
		return parent;
	}

	public void setParent(Comment parent) {
		this.parent = parent;
	}

	@Transient
	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	@Transient
	public int getParent_pos() {
		return parent_pos;
	}

	public void setParent_pos(int parent_pos) {
		this.parent_pos = parent_pos;
	}

	@Override
	public int compareTo(Comment o) {
		if (parent == null) {
			if (o.getParent() == null) {
               return id.compareTo(o.getId());  
			} else {
               return -1; 
			}
		} else {
			if (o.getParent() == null) {
               return 1;
			} else {
               return parent.getId().compareTo(o.getParent().getId());
			}
		}
	}

}
