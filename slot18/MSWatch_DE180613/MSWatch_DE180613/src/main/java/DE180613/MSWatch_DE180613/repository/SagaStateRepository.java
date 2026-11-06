package DE180613.MSWatch_DE180613.repository;

import DE180613.MSWatch_DE180613.entity.SagaState;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SagaStateRepository extends JpaRepository<SagaState, Long> {
    Optional<SagaState> findBySagaId(String sagaId);
    List<SagaState> findAllByOrderByIdDesc(Pageable pageable);
}

