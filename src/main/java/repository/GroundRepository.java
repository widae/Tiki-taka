package repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import entity.Ground;
import entity.Team;
import entity.User;

public interface GroundRepository extends JpaRepository<Ground, Integer>  {
	List<Ground> findByAddress(String address);
	@Query(
			"SELECT g "
		  + "FROM Ground g "
		  + "WHERE g.name LIKE %:text% "
		  + "OR g.address LIKE %:text% "
	)
	List<Ground> getGrounds(Pageable pageable, @Param("text") String text);
}