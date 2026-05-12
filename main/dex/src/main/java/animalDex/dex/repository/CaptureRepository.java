package animalDex.dex.repository;

import animalDex.dex.model.Capture;
import animalDex.dex.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaptureRepository extends JpaRepository<Capture, Long> {
    List<Capture> getCapturesByUser(User user);
}
