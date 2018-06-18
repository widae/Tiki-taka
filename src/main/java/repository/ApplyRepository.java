package repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import entity.Apply;
import entity.Ground;

public interface ApplyRepository extends JpaRepository<Apply, Integer>{
	List<Apply> findByMatchingIdAndTeamId(int matchingId, int teamId);
}