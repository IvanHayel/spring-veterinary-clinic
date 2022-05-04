package by.teachmeskills.spring.veterinary_clinic.repository;

import by.teachmeskills.spring.veterinary_clinic.model.Veterinarian;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface VeterinarianRepository extends CrudRepository<Veterinarian, Long> {
    @Override
    @NonNull
    @EntityGraph(value = "graph.Specialization.specializations")
    Iterable<Veterinarian> findAll();

    @Override
    @NonNull
    @EntityGraph(value = "graph.Specialization.specializations")
    Optional<Veterinarian> findById(Long aLong);
}