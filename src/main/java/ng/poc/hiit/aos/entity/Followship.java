package ng.poc.hiit.aos.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

/**
 * Entity object which represents a follower
 * 
 * @author aos
 *
 */
@Entity
public class Followship {
	@Id
	@GeneratedValue
	private Long id;
	private Long followerId;
	private Long followingId;
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@PrePersist
	private void onCreate() {
		createdAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFollowerId() {
		return followerId;
	}

	public void setFollowerId(Long followerId) {
		this.followerId = followerId;
	}

	public Long getFollowingId() {
		return followingId;
	}

	public void setFollowingId(Long followingId) {
		this.followingId = followingId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
