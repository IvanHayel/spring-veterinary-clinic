package by.teachmeskills.spring.veterinary_clinic.repository;

import by.teachmeskills.spring.veterinary_clinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
}