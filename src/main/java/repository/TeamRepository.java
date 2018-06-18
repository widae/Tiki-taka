package repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import entity.Team;

public interface TeamRepository extends JpaRepository<Team, Integer>  {
	List<Team> findByName(String name);
}