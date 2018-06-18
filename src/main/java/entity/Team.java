package entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class Team {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private String mainArea;
	private String level;
	private String ageGroup;
	private String description;
	private String pictureUrl;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMainArea() {
		return mainArea;
	}
	public void setMainArea(String mainArea) {
		this.mainArea = mainArea;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getAgeGroup() {
		return ageGroup;
	}
	public void setAgeGroup(String ageGroup) {
		this.ageGroup = ageGroup;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPictureUrl(){
		return pictureUrl;
	} 
	public void setPictureUrl(String pictureUrl){
		this.pictureUrl = pictureUrl;
	}
	
}
