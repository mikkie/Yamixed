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
package com.yamixed.fav.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.util.CollectionUtils;

import com.yamixed.base.entity.IdEntity;

/**
 * @author Administrator
 *
 */
@Entity
public class Tag extends IdEntity{
	
	private String name;
	
	private Channel channel;
	
	private List<User> users;
	
	private Long articleCount;
	
	private int hit;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	@ManyToOne
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	
	/**
	 * 关系维护端
	 * @return
	 */
	@ManyToMany(cascade={CascadeType.REFRESH})
    @JoinTable(name="t_tag_user",
            inverseJoinColumns=@JoinColumn(name="user_id"),
            joinColumns=@JoinColumn(name="tag_id"))
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	
	public Long getArticleCount() {
		return articleCount == null ? 0L : articleCount;
	}

	
	public void setArticleCount(Long articleCount) {
		this.articleCount = articleCount;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}
	
	
	@Transient
	public void addUser(User user){
		if(users == null){
		   users = new ArrayList<User>();
		}
		users.add(user);
	}
	
	
	@Transient
	public boolean containsUser(User user){
		if(CollectionUtils.isEmpty(users)){
			return false;
		}
		return users.contains(user);
	}
	
	
	

}
