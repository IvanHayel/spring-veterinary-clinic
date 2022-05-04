package by.teachmeskills.spring.veterinary_clinic.repository;

import by.teachmeskills.spring.veterinary_clinic.model.PetType;
import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {
    PetType findByName(String name);
}