package by.teachmeskills.spring.veterinary_clinic.controller;

import by.teachmeskills.spring.veterinary_clinic.model.Pet;
import by.teachmeskills.spring.veterinary_clinic.model.Veterinarian;
import by.teachmeskills.spring.veterinary_clinic.model.Visit;
import by.teachmeskills.spring.veterinary_clinic.service.PetService;
import by.teachmeskills.spring.veterinary_clinic.service.VeterinarianService;
import by.teachmeskills.spring.veterinary_clinic.service.VisitService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/visits")
public class VisitController {
    private static final String REDIRECT_TO_VISITS = "redirect:/visits";

    private static final String ATTRIBUTE_VISIT = "visit";
    private static final String ATTRIBUTE_VISITS = "visits";
    private static final String ATTRIBUTE_ALL_PETS = "allPets";
    private static final String ATTRIBUTE_ALL_VETS = "allVets";

    private static final String VIEW_VISITS = "/visits/index";
    private static final String VIEW_VISIT_EDIT_FORM = "/visits/visit-edit-form";

    private final VisitService visitService;
    private final PetService petService;
    private final VeterinarianService veterinarianService;

    @GetMapping({"", "/"})
    public ModelAndView showVisitsTable() {
        ModelAndView modelAndView = new ModelAndView(VIEW_VISITS);
        List<Visit> visits = visitService.findAll();
        modelAndView.addObject(ATTRIBUTE_VISITS, visits);
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView getVisitCreateForm() {
        ModelAndView modelAndView = new ModelAndView(VIEW_VISIT_EDIT_FORM);
        Visit visit = new Visit();
        List<Pet> allPets = petService.findAll();
        List<Veterinarian> allVets = veterinarianService.findAll();
        modelAndView.addObject(ATTRIBUTE_VISIT, visit);
        modelAndView.addObject(ATTRIBUTE_ALL_PETS, allPets);
        modelAndView.addObject(ATTRIBUTE_ALL_VETS, allVets);
        return modelAndView;
    }

    @PostMapping("/save")
    public String createVisit(
            @Valid Visit visit,
            BindingResult bindingResult,
            @RequestParam("petId") Integer petId,
            @RequestParam("veterinarianId") Integer veterinarianId
    ) {
        if (bindingResult.hasErrors()) {
            return VIEW_VISIT_EDIT_FORM;
        }
        Pet pet = petService.findById(Long.valueOf(petId));
        visit.setPet(pet);
        Veterinarian vet = veterinarianService.findById(Long.valueOf(veterinarianId));
        visit.setVeterinarian(vet);
        visitService.save(visit);
        return REDIRECT_TO_VISITS;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getVisitEditForm(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView(VIEW_VISIT_EDIT_FORM);
        Visit visit = visitService.findById(id);
        List<Pet> allPets = petService.findAll();
        List<Veterinarian> allVets = veterinarianService.findAll();
        modelAndView.addObject(ATTRIBUTE_VISIT, visit);
        modelAndView.addObject(ATTRIBUTE_ALL_PETS, allPets);
        modelAndView.addObject(ATTRIBUTE_ALL_VETS, allVets);
        return modelAndView;
    }

    @PostMapping("/update")
    public String updateVisit(
            @Valid Visit visit,
            BindingResult bindingResult,
            @RequestParam("petId") Integer petId,
            @RequestParam("veterinarianId") Integer veterinarianId
    ) {
        if (bindingResult.hasErrors()) {
            return VIEW_VISIT_EDIT_FORM;
        }
        Visit visitFromDB = visitService.findById(visit.getId());
        visitFromDB.setDiagnosis(visit.getDiagnosis());
        visitFromDB.setTreatment(visit.getTreatment());
        visitFromDB.setStatus(visit.isStatus());
        visitFromDB.setStartDateTime(visit.getStartDateTime());
        visitFromDB.setDuration(visit.getDuration());
        Pet pet = petService.findById(Long.valueOf(petId));
        visitFromDB.setPet(pet);
        Veterinarian vet = veterinarianService.findById(Long.valueOf(veterinarianId));
        visitFromDB.setVeterinarian(vet);
        visitService.save(visitFromDB);
        return REDIRECT_TO_VISITS;
    }


    @GetMapping("/delete/{id}")
    public String deleteVisit(@PathVariable Long id) {
        visitService.deleteById(id);
        return REDIRECT_TO_VISITS;
    }
}