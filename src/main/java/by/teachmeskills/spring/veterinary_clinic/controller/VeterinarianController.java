package by.teachmeskills.spring.veterinary_clinic.controller;


import by.teachmeskills.spring.veterinary_clinic.model.Specialization;
import by.teachmeskills.spring.veterinary_clinic.model.Veterinarian;
import by.teachmeskills.spring.veterinary_clinic.service.SpecializationService;
import by.teachmeskills.spring.veterinary_clinic.service.VeterinarianService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Controller
@RequestMapping("/veterinarians")
public class VeterinarianController {
    private static final String REDIRECT_TO_VETERINARIANS = "redirect:/veterinarians";

    private static final String ATTRIBUTE_VETERINARIAN = "vet";
    private static final String ATTRIBUTE_VETERINARIANS = "veterinarians";
    private static final String ATTRIBUTE_ALL_SPECIALIZATIONS = "allSpecializations";

    private static final String VIEW_VETERINARIANS = "/veterinarians/index";
    private static final String VIEW_VETERINARIAN_EDIT_FORM = "/veterinarians/vet-edit-form";

    private final VeterinarianService veterinarianService;
    private final SpecializationService specializationService;

    @GetMapping({"", "/"})
    public ModelAndView showVeterinariansTable() {
        ModelAndView modelAndView = new ModelAndView(VIEW_VETERINARIANS);
        List<Veterinarian> veterinarians = veterinarianService.findAll();
        modelAndView.addObject(ATTRIBUTE_VETERINARIANS, veterinarians);
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView createVeterinarianForm() {
        ModelAndView modelAndView = new ModelAndView(VIEW_VETERINARIAN_EDIT_FORM);
        modelAndView.addObject(ATTRIBUTE_VETERINARIAN, new Veterinarian());
        List<Specialization> allSpecializations = specializationService.findAll();
        modelAndView.addObject(ATTRIBUTE_ALL_SPECIALIZATIONS, allSpecializations);
        return modelAndView;
    }

    @PostMapping("/save")
    public String createVeterinarian(
            Veterinarian veterinarian,
            @RequestParam(value = "specializationIds", required = false) List<Integer> specializationIds
    ) {
        Set<Specialization> selectedSpecializations = new HashSet<>();
        if (specializationIds != null) {
            specializationIds.stream()
                    .mapToLong(Long::valueOf)
                    .mapToObj(specializationService::findById)
                    .forEach(selectedSpecializations::add);
        }
        veterinarian.setSpecializations(selectedSpecializations);
        veterinarianService.save(veterinarian);
        return REDIRECT_TO_VETERINARIANS;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditVeterinarianForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView(VIEW_VETERINARIAN_EDIT_FORM);
        Veterinarian veterinarian = veterinarianService.findById(id);
        modelAndView.addObject(ATTRIBUTE_VETERINARIAN, veterinarian);
        List<Specialization> allSpecializations = specializationService.findAll();
        modelAndView.addObject(ATTRIBUTE_ALL_SPECIALIZATIONS, allSpecializations);
        return modelAndView;
    }

    @PostMapping("/update")
    public String updateVeterinarian(
            Veterinarian veterinarian,
            @RequestParam(value = "specializationIds", required = false) List<Integer> specializationIds
    ) {
        Veterinarian vetFromDB = veterinarianService.findById(veterinarian.getId());
        vetFromDB.setName(veterinarian.getName());
        vetFromDB.setSurname(veterinarian.getSurname());
        vetFromDB.setExperience(veterinarian.getExperience());
        Set<Specialization> selectedSpecializations = new HashSet<>();
        if (specializationIds != null) {
            specializationIds.stream()
                    .mapToLong(Long::valueOf)
                    .mapToObj(specializationService::findById)
                    .forEach(selectedSpecializations::add);
        }
        vetFromDB.setSpecializations(selectedSpecializations);
        veterinarianService.save(vetFromDB);
        return REDIRECT_TO_VETERINARIANS;
    }

    @GetMapping("/delete/{id}")
    public String deleteVeterinarian(@PathVariable Long id) {
        veterinarianService.deleteById(id);
        return REDIRECT_TO_VETERINARIANS;
    }
}