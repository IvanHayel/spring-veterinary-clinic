package by.teachmeskills.spring.veterinary_clinic.service;

import by.teachmeskills.spring.veterinary_clinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {
    void updateMainInformation(Owner owner);
}
