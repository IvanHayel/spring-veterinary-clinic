package by.teachmeskills.spring.veterinary_clinic.service;

import by.teachmeskills.spring.veterinary_clinic.model.BaseEntity;

import java.util.List;

public interface CrudService<E extends BaseEntity, T extends Long> {
    List<E> findAll();

    E findById(T id);

    E save(E entity);

    void delete(E entity);

    void deleteById(T id);
}
