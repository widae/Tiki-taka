package repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import entity.Matching;

public interface MatchingRepository extends JpaRepository<Matching, Integer>{
	
	@Query(
			"SELECT count(*) "
		  + "FROM Matching m "
		  + "WHERE m.start < :end "
		  + "AND m.end > :start "
		  + "AND groundId = :groundId"
	)
	int getNumOfDuplicated(@Param("start")Date date, @Param("end")Date date2, @Param("groundId")int groundId);
	
	@Query(
			"SELECT m "
		  + "FROM Matching m "
		  + "WHERE m.awayTeam = null "
		  + "ORDER BY m.start DESC "
	)
	List<Matching> getMatches(Pageable pageable);
	
	@Query(
			"SELECT DISTINCT(m) "
		  + "FROM Matching AS m "
		  + "LEFT JOIN Apply AS a ON m.id = a.matching.id "
		  + "WHERE (m.homeTeam.id = :teamId OR a.team.id = :teamId) "
		  + "ORDER BY m.start DESC"
	)
	List<Matching> getRelatedMatches(@Param("teamId") int teamId, Pageable pageable);
	
}