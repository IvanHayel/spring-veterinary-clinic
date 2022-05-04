package by.teachmeskills.spring.veterinary_clinic.service.impl;

import by.teachmeskills.spring.veterinary_clinic.model.Specialization;
import by.teachmeskills.spring.veterinary_clinic.repository.SpecializationRepository;
import by.teachmeskills.spring.veterinary_clinic.service.SpecializationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class SpecializationServiceImpl implements SpecializationService {
    private final SpecializationRepository specializationRepository;

    @Override
    public List<Specialization> findAll() {
        List<Specialization> specializations = new ArrayList<>();
        specializationRepository
                .findAll()
                .forEach(specializations::add);
        return specializations;
    }

    @Override
    public Specialization findById(Long id) {
        return specializationRepository
                .findById(id)
                .orElse(null);
    }

    @Override
    public Specialization save(Specialization entity) {
        return specializationRepository.save(entity);
    }

    @Override
    public void delete(Specialization entity) {
        specializationRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        specializationRepository.deleteById(id);
    }
}