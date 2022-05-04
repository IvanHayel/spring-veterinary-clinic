package by.teachmeskills.spring.veterinary_clinic.repository;

import by.teachmeskills.spring.veterinary_clinic.model.Specialization;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface SpecializationRepository extends CrudRepository<Specialization, Long> {
    @Override
    @NonNull
    @EntityGraph(value = "graph.PetType.petTypes")
    Iterable<Specialization> findAll();

    @Override
    @NonNull
    @EntityGraph(value = "graph.PetType.petTypes")
    Optional<Specialization> findById(Long aLong);
}
