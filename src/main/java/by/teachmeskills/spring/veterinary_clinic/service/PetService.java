package by.teachmeskills.spring.veterinary_clinic.service;

import by.teachmeskills.spring.veterinary_clinic.model.Pet;

public interface PetService extends CrudService<Pet, Long> {
    Pet findByName(String name);

    void updateMainInformation(Pet pet);
}
