package DE180613.MSWatch_DE180613.repository;

import DE180613.MSWatch_DE180613.entity.Watch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchRepository extends JpaRepository<Watch, Integer> {
    List<Watch> findAllByOrderByWatchIdDesc();
}

