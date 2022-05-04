package by.teachmeskills.spring.veterinary_clinic.service.impl;

import by.teachmeskills.spring.veterinary_clinic.model.Visit;
import by.teachmeskills.spring.veterinary_clinic.repository.VisitRepository;
import by.teachmeskills.spring.veterinary_clinic.service.VisitService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class VisitServiceImpl implements VisitService {
    private final VisitRepository visitRepository;

    @Override
    public List<Visit> findAll() {
        List<Visit> visits = new ArrayList<>();
        visitRepository
                .findAll()
                .forEach(visits::add);
        return visits;
    }

    @Override
    public Visit findById(Long id) {
        return visitRepository
                .findById(id)
                .orElse(null);
    }

    @Override
    public Visit save(Visit entity) {
        return visitRepository.save(entity);
    }

    @Override
    public void delete(Visit entity) {
        visitRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        visitRepository.deleteById(id);
    }

    @Override
    public List<Visit> findAllByVeterinarianId(Long id) {
        return visitRepository.findAllByVeterinarianId(id);
    }
}