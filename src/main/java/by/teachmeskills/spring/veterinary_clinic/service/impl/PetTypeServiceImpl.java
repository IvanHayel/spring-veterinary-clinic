package by.teachmeskills.spring.veterinary_clinic.service.impl;

import by.teachmeskills.spring.veterinary_clinic.model.PetType;
import by.teachmeskills.spring.veterinary_clinic.repository.PetTypeRepository;
import by.teachmeskills.spring.veterinary_clinic.service.PetTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PetTypeServiceImpl implements PetTypeService {
    private final PetTypeRepository petTypeRepository;

    @Override
    public List<PetType> findAll() {
        List<PetType> petTypes = new ArrayList<>();
        petTypeRepository
                .findAll()
                .forEach(petTypes::add);
        return petTypes;
    }

    @Override
    public PetType findById(Long id) {
        return petTypeRepository
                .findById(id)
                .orElse(null);
    }

    @Override
    public PetType save(PetType entity) {
        return petTypeRepository.save(entity);
    }

    @Override
    public void delete(PetType entity) {
        petTypeRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        petTypeRepository.deleteById(id);
    }

    @Override
    public PetType findByName(String name) {
        return petTypeRepository.findByName(name);
    }
}