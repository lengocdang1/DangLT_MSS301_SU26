package DE180613.MSBrand_DE180613.repository;

import DE180613.MSBrand_DE180613.entity.WatchBrand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// [TODO-5.1]
@Repository
public interface BrandRepository extends JpaRepository<WatchBrand, Integer> {
}

