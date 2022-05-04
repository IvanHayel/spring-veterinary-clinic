package by.teachmeskills.spring.veterinary_clinic.service;

import by.teachmeskills.spring.veterinary_clinic.model.Visit;

import java.util.List;

public interface VisitService extends CrudService<Visit, Long> {
    List<Visit> findAllByVeterinarianId(Long id);
}
