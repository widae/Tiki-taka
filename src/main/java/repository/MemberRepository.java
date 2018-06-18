package repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import entity.Member;
import entity.Team;
import entity.User;

public interface MemberRepository extends JpaRepository<Member, Integer>  {
	List<Member> findByUserId(int userId);
	List<Member> findByTeamId(int teamId);
	List<Member> findByUserIdAndTeamId(int userId, int teamId);

	@Query(
			"SELECT mem.team "
		  + "FROM Member mem "
		  + "WHERE mem.user = :user"
	)
	List<Team> getMyTeams(@Param("user")User user);
	
}