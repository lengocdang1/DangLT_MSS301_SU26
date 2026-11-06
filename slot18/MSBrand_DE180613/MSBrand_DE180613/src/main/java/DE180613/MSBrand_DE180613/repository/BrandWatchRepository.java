package DE180613.MSBrand_DE180613.repository;

import DE180613.MSBrand_DE180613.entity.BrandWatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// [TODO-5.1]
@Repository
public interface BrandWatchRepository extends JpaRepository<BrandWatch, Long> {

    Optional<BrandWatch> findByWatchId(Integer watchId);

    long countByBrandId(Integer brandId);

    List<BrandWatch> findByBrandIdOrderByWatchIdAsc(Integer brandId);
}

