package by.teachmeskills.spring.veterinary_clinic.service.impl;

import by.teachmeskills.spring.veterinary_clinic.model.Pet;
import by.teachmeskills.spring.veterinary_clinic.repository.OwnerRepository;
import by.teachmeskills.spring.veterinary_clinic.repository.PetRepository;
import by.teachmeskills.spring.veterinary_clinic.service.PetService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    @Override
    public List<Pet> findAll() {
        List<Pet> pets = new ArrayList<>();
        petRepository
                .findAll()
                .forEach(pets::add);
        return pets;
    }

    @Override
    public Pet findById(Long id) {
        return petRepository
                .findById(id)
                .orElse(null);
    }

    @Override
    public Pet save(Pet entity) {
        return petRepository.save(entity);
    }

    @Override
    public void delete(Pet entity) {
        petRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        petRepository.deleteById(id);
    }

    @Override
    public Pet findByName(String name) {
        return petRepository.findByName(name);
    }

    @Override
    public void updateMainInformation(Pet pet) {
        Optional<Pet> optional = petRepository.findById(pet.getId());
        if (optional.isPresent()) {
            Pet petFromDB = optional.get();
            petFromDB.setName(pet.getName());
            petFromDB.setType(pet.getType());
            petRepository.save(petFromDB);
        }
    }
}