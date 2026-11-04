package DE180613.MSAccount_DE180613.repository;

import DE180613.MSAccount_DE180613.entity.StoreAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// [TODO-3.1: repository cho StoreAccounts]
@Repository
public interface AccountRepository extends JpaRepository<StoreAccount, Integer> {
    Optional<StoreAccount> findByUsername(String username);
}

