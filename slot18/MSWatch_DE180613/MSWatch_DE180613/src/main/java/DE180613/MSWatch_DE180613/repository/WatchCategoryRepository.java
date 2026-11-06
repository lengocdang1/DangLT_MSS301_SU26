package DE180613.MSWatch_DE180613.repository;

import DE180613.MSWatch_DE180613.entity.WatchCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchCategoryRepository extends JpaRepository<WatchCategory, Integer> {
}

