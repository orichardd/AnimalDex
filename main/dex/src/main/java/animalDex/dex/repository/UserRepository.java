package animalDex.dex.repository;

import animalDex.dex.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByUsername(String username);

    User getUserById(Long id);

    Optional<User> findByUsername(String username);
}
