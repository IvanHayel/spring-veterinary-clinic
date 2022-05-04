package by.teachmeskills.spring.veterinary_clinic.service.impl;

import by.teachmeskills.spring.veterinary_clinic.model.Owner;
import by.teachmeskills.spring.veterinary_clinic.repository.OwnerRepository;
import by.teachmeskills.spring.veterinary_clinic.service.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class OwnerServiceImpl implements OwnerService {
    private final OwnerRepository ownerRepository;

    @Override
    public List<Owner> findAll() {
        List<Owner> owners = new ArrayList<>();
        ownerRepository
                .findAll()
                .forEach(owners::add);
        return owners;
    }

    @Override
    public Owner findById(Long id) {
        return ownerRepository
                .findById(id)
                .orElse(null);
    }

    @Override
    public Owner save(Owner entity) {
        return ownerRepository.save(entity);
    }

    @Override
    public void delete(Owner entity) {
        ownerRepository.delete(entity);
    }

    @Override
    public void deleteById(Long id) {
        ownerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateMainInformation(Owner owner) {
        Optional<Owner> optional = ownerRepository.findById(owner.getId());
        if (optional.isPresent()) {
            Owner ownerFromDB = optional.get();
            ownerFromDB.setName(owner.getName());
            ownerFromDB.setSurname(owner.getSurname());
            ownerFromDB.setPhone(owner.getPhone());
            ownerFromDB.setEmail(owner.getEmail());
            ownerRepository.save(ownerFromDB);
        }
    }
}