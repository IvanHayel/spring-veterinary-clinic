package by.teachmeskills.spring.veterinary_clinic.controller;

import by.teachmeskills.spring.veterinary_clinic.model.PetType;
import by.teachmeskills.spring.veterinary_clinic.model.Specialization;
import by.teachmeskills.spring.veterinary_clinic.service.PetTypeService;
import by.teachmeskills.spring.veterinary_clinic.service.SpecializationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Controller
@RequestMapping("/specializations")
public class SpecializationController {
    private static final String REDIRECT_TO_SPECIALIZATIONS = "redirect:/specializations";

    private static final String ATTRIBUTE_SPECIALIZATIONS = "specializations";
    private static final String ATTRIBUTE_SPECIALIZATION = "specialization";
    private static final String ATTRIBUTE_ALL_PET_TYPES = "allPetTypes";

    private static final String VIEW_SPECIALIZATIONS = "/specializations/index";
    private static final String VIEW_SPECIALIZATION_EDIT_FORM= "/specializations/specialization-edit-form";

    private final SpecializationService specializationService;
    private final PetTypeService petTypeService;

    @RequestMapping({"", "/"})
    public ModelAndView showSpecializationsTable() {
        ModelAndView modelAndView = new ModelAndView(VIEW_SPECIALIZATIONS);
        List<Specialization> specializations = specializationService.findAll();
        modelAndView.addObject(ATTRIBUTE_SPECIALIZATIONS, specializations);
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView createSpecializationForm() {
        ModelAndView modelAndView = new ModelAndView(VIEW_SPECIALIZATION_EDIT_FORM);
        modelAndView.addObject(ATTRIBUTE_SPECIALIZATION, new Specialization());
        List<PetType> allPetTypes = petTypeService.findAll();
        modelAndView.addObject(ATTRIBUTE_ALL_PET_TYPES, allPetTypes);
        return modelAndView;
    }

    @PostMapping("/save")
    public String createSpecialization(
            Specialization specialization,
            @RequestParam(value = "petTypeIds", required = false) List<Integer> petTypeIds
    ) {
        Set<PetType> selectedPetTypes = new HashSet<>();
        if (petTypeIds != null) {
            petTypeIds.stream()
                    .mapToLong(Long::valueOf)
                    .mapToObj(petTypeService::findById)
                    .forEach(selectedPetTypes::add);
        }
        specialization.setPetTypes(selectedPetTypes);
        specializationService.save(specialization);
        return REDIRECT_TO_SPECIALIZATIONS;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditSpecializationForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView(VIEW_SPECIALIZATION_EDIT_FORM);
        Specialization specialization = specializationService.findById(id);
        modelAndView.addObject(ATTRIBUTE_SPECIALIZATION, specialization);
        List<PetType> allPetTypes = petTypeService.findAll();
        modelAndView.addObject(ATTRIBUTE_ALL_PET_TYPES, allPetTypes);
        return modelAndView;
    }

    @PostMapping("/update")
    public String updateSpecialization(
            Specialization specialization,
            @RequestParam(value = "petTypeIds", required = false) List<Integer> petTypeIds
    ) {
        Specialization specializationFromDB = specializationService.findById(specialization.getId());
        specializationFromDB.setName(specialization.getName());
        specializationFromDB.setDescription(specialization.getDescription());
        Set<PetType> selectedPetTypes = new HashSet<>();
        if (petTypeIds != null) {
            petTypeIds.stream()
                    .mapToLong(Long::valueOf)
                    .mapToObj(petTypeService::findById)
                    .forEach(selectedPetTypes::add);
        }
        specializationFromDB.setPetTypes(selectedPetTypes);
        specializationService.save(specializationFromDB);
        return REDIRECT_TO_SPECIALIZATIONS;
    }

    @GetMapping("/delete/{id}")
    public String deleteSpecialization(@PathVariable Long id) {
        specializationService.deleteById(id);
        return REDIRECT_TO_SPECIALIZATIONS;
    }
}