package by.teachmeskills.spring.veterinary_clinic.controller;

import by.teachmeskills.spring.veterinary_clinic.model.Owner;
import by.teachmeskills.spring.veterinary_clinic.model.Pet;
import by.teachmeskills.spring.veterinary_clinic.model.PetType;
import by.teachmeskills.spring.veterinary_clinic.service.OwnerService;
import by.teachmeskills.spring.veterinary_clinic.service.PetService;
import by.teachmeskills.spring.veterinary_clinic.service.PetTypeService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/pets")
public class PetController {
    private static final String REDIRECT_TO_OWNERS = "redirect:/owners";
    private static final String REDIRECT_TO_PETS = "redirect:/pets";

    private static final String ATTRIBUTE_PETS = "pets";
    private static final String ATTRIBUTE_PET = "pet";

    private static final String VIEW_PETS = "/pets/index";
    private static final String VIEW_PET_EDIT_FORM = "/pets/pet-edit-form";

    private final OwnerService ownerService;
    private final PetService petService;
    private final PetTypeService petTypeService;

    @RequestMapping({"", "/"})
    public ModelAndView showPetsTable() {
        ModelAndView modelAndView = new ModelAndView(VIEW_PETS);
        List<Pet> pets = petService.findAll();
        modelAndView.addObject(ATTRIBUTE_PETS, pets);
        return modelAndView;
    }

    @PostMapping("/save")
    public String createPet(
            Pet pet,
            @RequestParam("ownerId") Long ownerId,
            @RequestParam("petTypeName") String petTypeName
    ) {
        @NonNull Owner owner = ownerService.findById(ownerId);
        pet.setOwner(owner);
        petTypeName = petTypeName.trim().toLowerCase();
        PetType petType = petTypeService.findByName(petTypeName);
        if (petType == null) {
            petType = new PetType(petTypeName);
            petTypeService.save(petType);
        }
        pet.setType(petType);
        owner.addPet(pet);
        ownerService.save(owner);
        return REDIRECT_TO_OWNERS;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditPetForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView(VIEW_PET_EDIT_FORM);
        Pet pet = petService.findById(id);
        modelAndView.addObject(ATTRIBUTE_PET, pet);
        return modelAndView;
    }

    @PostMapping("/update")
    public String updatePet(
            Pet pet,
            @RequestParam("petTypeName") String petTypeName
    ) {
        petTypeName = petTypeName.trim().toLowerCase();
        PetType petType = petTypeService.findByName(petTypeName);
        if (petType == null) {
            petType = new PetType(petTypeName);
            petTypeService.save(petType);
        }
        pet.setType(petType);
        petService.updateMainInformation(pet);
        return REDIRECT_TO_PETS;
    }

    @GetMapping("/delete/{id}")
    public String deletePet(@PathVariable Long id) {
        Pet pet = petService.findById(id);
        Owner owner = pet.getOwner();
        owner.removePet(pet);
        ownerService.save(owner);
        petService.delete(pet);
        return REDIRECT_TO_PETS;
    }
}