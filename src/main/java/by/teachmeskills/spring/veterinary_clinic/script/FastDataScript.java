package by.teachmeskills.spring.veterinary_clinic.script;

import by.teachmeskills.spring.veterinary_clinic.model.*;
import by.teachmeskills.spring.veterinary_clinic.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class for loading example data to database
 *
 * @author Ivan Hayel
 * @version 0.0.1
 * @since 1.8
 * <p>
 * ENABLE FOR DEVELOPMENT PURPOSES ONLY
 */

@Component
@AllArgsConstructor
@Slf4j
public class FastDataScript implements CommandLineRunner {
    private static final boolean ENABLED = false;

    private final OwnerService ownerService;
    private final PetService petService;
    private final PetTypeService petTypeService;
    private final SpecializationService specializationService;
    private final VeterinarianService veterinarianService;
    private final VisitService visitService;

    @Override
    public void run(String... args) {
        if (ENABLED) {
            loadData();
        }
    }

    private void loadData() {
        log.warn("STARTED LOADING DATA BY {}", this.getClass().getSimpleName());

        // Pet types
        final String PET_TYPE_LOG_MESSAGE = "Pet type {} was saved";

        PetType dog = new PetType("dog");
        PetType cat = new PetType("cat");
        PetType parrot = new PetType("parrot");

        PetType savedDogPetType = petTypeService.save(dog);
        PetType savedCatPetType = petTypeService.save(cat);
        PetType savedParrotPetType = petTypeService.save(parrot);

        log.debug(PET_TYPE_LOG_MESSAGE, savedDogPetType.getName());
        log.debug(PET_TYPE_LOG_MESSAGE, savedCatPetType.getName());
        log.debug(PET_TYPE_LOG_MESSAGE, savedParrotPetType.getName());

        Set<PetType> allPetTypes = new HashSet<>();
        allPetTypes.add(savedDogPetType);
        allPetTypes.add(savedCatPetType);
        allPetTypes.add(savedParrotPetType);

        // Specializations
        final String SPECIALIZATION_LOG_MESSAGE = "Specialization {} was saved";

        Specialization surgery = new Specialization();
        surgery.setName("surgery");
        surgery.setDescription("surgery description");
        surgery.setPetTypes(allPetTypes);
        Specialization therapist = new Specialization();
        therapist.setName("therapist");
        therapist.setDescription("therapist description");
        therapist.setPetTypes(allPetTypes);

        Specialization savedSurgerySpecialization = specializationService.save(surgery);
        Specialization savedTherapistSpecialization = specializationService.save(therapist);

        log.debug(SPECIALIZATION_LOG_MESSAGE, savedSurgerySpecialization.getName());
        log.debug(SPECIALIZATION_LOG_MESSAGE, savedTherapistSpecialization.getName());

        // Owners and pets
        final String OWNER_WITH_PETS_LOG_MESSAGE = "Owner {} was saved. Pets added.";

        Owner firstOwner = new Owner();
        firstOwner.setName("John");
        firstOwner.setSurname("Doe");
        firstOwner.setPhone("+380505050505");
        firstOwner.setEmail("john_doe@example.org");
        Owner secondOwner = new Owner();
        secondOwner.setName("Jane");
        secondOwner.setSurname("Doe");
        secondOwner.setPhone("+380303030303");
        secondOwner.setEmail("jane_doe@example.org");

        Owner savedFirstOwner = ownerService.save(firstOwner);
        Owner savedSecondOwner = ownerService.save(secondOwner);

        Pet firstOwnerFirstPet = new Pet();
        firstOwnerFirstPet.setName("Barsik");
        firstOwnerFirstPet.setType(savedCatPetType);
        firstOwnerFirstPet.setOwner(savedFirstOwner);
        Pet firstOwnerSecondPet = new Pet();
        firstOwnerSecondPet.setName("Jack");
        firstOwnerSecondPet.setType(savedDogPetType);
        firstOwnerSecondPet.setOwner(savedFirstOwner);
        Pet secondOwnerFirstPet = new Pet();
        secondOwnerFirstPet.setName("Gosha");
        secondOwnerFirstPet.setType(savedParrotPetType);
        secondOwnerFirstPet.setOwner(savedSecondOwner);

        savedFirstOwner.addPet(firstOwnerFirstPet);
        savedFirstOwner.addPet(firstOwnerSecondPet);
        savedSecondOwner.addPet(secondOwnerFirstPet);

        ownerService.save(savedFirstOwner);
        ownerService.save(savedSecondOwner);

        List<Pet> allPets = petService.findAll();

        log.debug(OWNER_WITH_PETS_LOG_MESSAGE, savedFirstOwner.getName());
        log.debug(OWNER_WITH_PETS_LOG_MESSAGE, savedSecondOwner.getName());

        // Veterinarians
        final String VETERINARIAN_LOG_MESSAGE = "Veterinarian {} was saved";

        Set<Specialization> allSpecializations = new HashSet<>(specializationService.findAll());

        Veterinarian firstVeterinarian = new Veterinarian();
        firstVeterinarian.setName("John");
        firstVeterinarian.setSurname("Kramer");
        firstVeterinarian.setExperience(3);
        firstVeterinarian.setSpecializations(allSpecializations);
        Veterinarian secondVeterinarian = new Veterinarian();
        secondVeterinarian.setName("Jane");
        secondVeterinarian.setSurname("Kramer");
        secondVeterinarian.setExperience(5);
        secondVeterinarian.setSpecializations(allSpecializations);

        Veterinarian savedFirstVeterinarian = veterinarianService.save(firstVeterinarian);
        Veterinarian savedSecondVeterinarian = veterinarianService.save(secondVeterinarian);

        log.debug(VETERINARIAN_LOG_MESSAGE, savedFirstVeterinarian.getName());
        log.debug(VETERINARIAN_LOG_MESSAGE, savedSecondVeterinarian.getName());

        // Visits
        final String VISIT_LOG_MESSAGE = "Visit {} was saved";

        Visit firstVisit = new Visit();
        firstVisit.setStartDateTime(LocalDateTime.now());
        firstVisit.setDuration(LocalTime.of(1, 20));
        firstVisit.setStatus(false);
        firstVisit.setPet(
                allPets.stream()
                        .filter(pet -> pet.getName().equals(firstOwnerFirstPet.getName()))
                        .findFirst()
                        .orElse(null)
        );
        firstVisit.setDiagnosis("Diagnosis");
        firstVisit.setTreatment("Treatment");
        firstVisit.setVeterinarian(savedFirstVeterinarian);
        Visit secondVisit = new Visit();
        secondVisit.setStartDateTime(LocalDateTime.now());
        secondVisit.setDuration(LocalTime.of(6, 30));
        secondVisit.setStatus(false);
        secondVisit.setPet(
                allPets.stream()
                        .filter(pet -> pet.getName().equals(secondOwnerFirstPet.getName()))
                        .findFirst()
                        .orElse(null)
        );
        secondVisit.setDiagnosis("Diagnosis");
        secondVisit.setTreatment("Treatment");
        secondVisit.setVeterinarian(savedSecondVeterinarian);

        Visit savedFirstVisit = visitService.save(firstVisit);
        Visit savedSecondVisit = visitService.save(secondVisit);

        log.debug(VISIT_LOG_MESSAGE, savedFirstVisit.getId());
        log.debug(VISIT_LOG_MESSAGE, savedSecondVisit.getId());

        log.warn("DATA WAS LOADED BY {}", this.getClass().getSimpleName());
    }
}