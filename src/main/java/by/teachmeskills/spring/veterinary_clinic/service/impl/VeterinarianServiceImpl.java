package by.teachmeskills.spring.veterinary_clinic.service.impl;

import by.teachmeskills.spring.veterinary_clinic.model.Veterinarian;
import by.teachmeskills.spring.veterinary_clinic.repository.VeterinarianRepository;
import by.teachmeskills.spring.veterinary_clinic.service.VeterinarianService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class VeterinarianServiceImpl implements VeterinarianService {
    private final VeterinarianRepository veterinarianRepository;

    @Override
    public List<Veterinarian> findAll() {
        List<Veterinarian> veterinarians = new ArrayList<>();
        veterinarianRepository
                .findAll()
                .forEach(veterinarians::add);
        return veterinarians;
    }

    @Override
    public Veterinarian findById(Long id) {
        return veterinarianRepository
                .findById(id)
                .orElse(null);
    }

    @Override
    public Veterinarian save(Veterinarian entity) {
        return veterinarianRepository.save(entity);
    }

    @Override
    public void delete(Veterinarian entity) {
        veterinarianRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        veterinarianRepository.deleteById(id);
    }
}