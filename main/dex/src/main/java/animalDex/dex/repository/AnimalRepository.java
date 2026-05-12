package animalDex.dex.repository;

import animalDex.dex.model.Animal;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>{

    Animal findByScientificName(String scientificName);

}
