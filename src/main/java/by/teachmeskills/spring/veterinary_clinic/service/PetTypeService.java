package by.teachmeskills.spring.veterinary_clinic.service;

import by.teachmeskills.spring.veterinary_clinic.model.PetType;

public interface PetTypeService extends CrudService<PetType, Long> {
    PetType findByName(String petType);
}
