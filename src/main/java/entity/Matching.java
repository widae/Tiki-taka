package entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

@Entity
public class Matching {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date start;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date end;
	@ManyToOne
	@JoinColumn(name="homeTeamId")
	private Team homeTeam;
	@ManyToOne
	@JoinColumn(name="awayTeamId")
	private Team awayTeam;
	private int playerNumber;
	private int price;
	@ManyToOne
	@JoinColumn(name="groundId")
	private Ground ground;
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="matchingId")
	private List<Apply> applies;
	@CreatedDate
	private Date createdDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Team getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(Team homeTeam) {
		this.homeTeam = homeTeam;
	}

	public Team getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(Team awayTeam) {
		this.awayTeam = awayTeam;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Ground getGround() {
		return ground;
	}

	public void setGround(Ground ground) {
		this.ground = ground;
	}

	public List<Apply> getApplies() {
		return applies;
	}

	public void setApplies(List<Apply> applies) {
		this.applies = applies;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
