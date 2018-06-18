package entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Ground {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	private String address;
	private String url;
	private Integer indoor;
	private Integer park;
	private Integer light;
	private String latitude;
	private String longitude;
	
	private String phone;
	private int unit;
	@OneToMany
	@JoinColumn(name="gid")
	List<GroundImage> groundImages;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getIndoor() {
		return indoor;
	}
	public void setIndoor(Integer indoor) {
		this.indoor = indoor;
	}
	public Integer getPark() {
		return park;
	}
	public void setPark(Integer park) {
		this.park = park;
	}
	public Integer getLight() {
		return light;
	}
	public void setLight(Integer light) {
		this.light = light;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getUnit() {
		return unit;
	}
	public void setUnit(int unit) {
		this.unit = unit;
	}
	public List<GroundImage> getGroundImages() {
		return groundImages;
	}
	public void setGroundImages(List<GroundImage> groundImages) {
		this.groundImages = groundImages;
	}
}
