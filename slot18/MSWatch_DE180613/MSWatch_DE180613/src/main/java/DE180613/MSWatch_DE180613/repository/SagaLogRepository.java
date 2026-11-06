package DE180613.MSWatch_DE180613.repository;

import DE180613.MSWatch_DE180613.entity.SagaLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SagaLogRepository extends JpaRepository<SagaLog, Long> {
    List<SagaLog> findBySagaIdOrderByIdAsc(String sagaId);
}

