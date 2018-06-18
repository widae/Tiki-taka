package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="ground_thumnail")
public class GroundImage {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(name="url")
	private String pictureUrl;
	@Column(name="gid")
	private Integer groundId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public Integer getGroundId() {
		return groundId;
	}
	public void setGroundId(Integer groundId) {
		this.groundId = groundId;
	}
	
	
}
