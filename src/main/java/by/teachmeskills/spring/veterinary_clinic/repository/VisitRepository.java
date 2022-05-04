package by.teachmeskills.spring.veterinary_clinic.repository;

import by.teachmeskills.spring.veterinary_clinic.model.Visit;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VisitRepository extends CrudRepository<Visit, Long> {
    List<Visit> findAllByVeterinarianId(Long id);
}