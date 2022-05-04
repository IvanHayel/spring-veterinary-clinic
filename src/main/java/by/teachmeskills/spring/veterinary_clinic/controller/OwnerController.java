package by.teachmeskills.spring.veterinary_clinic.controller;

import by.teachmeskills.spring.veterinary_clinic.model.Owner;
import by.teachmeskills.spring.veterinary_clinic.model.Pet;
import by.teachmeskills.spring.veterinary_clinic.service.OwnerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/owners")
public class OwnerController {
    private static final String REDIRECT_TO_OWNERS = "redirect:/owners";
    private static final String REDIRECT_TO_EDIT_OWNER = "redirect:/owners/edit/";

    private static final String ATTRIBUTE_OWNER = "owner";
    private static final String ATTRIBUTE_OWNERS = "owners";
    private static final String ATTRIBUTE_PET = "pet";

    private static final String VIEW_OWNERS = "/owners/index";
    private static final String VIEW_OWNER_EDIT_FORM = "/owners/owner-edit-form";
    private static final String VIEW_PET_EDIT_FORM = "/pets/pet-edit-form";

    private final OwnerService ownerService;

    @GetMapping({"", "/"})
    public ModelAndView showOwnersTable() {
        ModelAndView modelAndView = new ModelAndView(VIEW_OWNERS);
        List<Owner> owners = ownerService.findAll();
        modelAndView.addObject(ATTRIBUTE_OWNERS, owners);
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView getOwnerCreateForm() {
        ModelAndView modelAndView = new ModelAndView(VIEW_OWNER_EDIT_FORM);
        modelAndView.addObject(ATTRIBUTE_OWNER, new Owner());
        return modelAndView;
    }

    @PostMapping("/save")
    public String createOwner(@Valid Owner owner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return VIEW_OWNER_EDIT_FORM;
        }
        ownerService.save(owner);
        return REDIRECT_TO_OWNERS;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditOwnerForm(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView(VIEW_OWNER_EDIT_FORM);
        Owner owner = ownerService.findById(id);
        modelAndView.addObject(ATTRIBUTE_OWNER, owner);
        return modelAndView;
    }

    @PostMapping("/update")
    public String updateOwner(@Valid Owner owner, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return VIEW_OWNER_EDIT_FORM;
        }
        ownerService.updateMainInformation(owner);
        return REDIRECT_TO_OWNERS;
    }

    @GetMapping("/delete/{id}")
    public String deleteOwner(@PathVariable("id") Long id) {
        ownerService.deleteById(id);
        return REDIRECT_TO_OWNERS;
    }

    @GetMapping("/{id}/add-pet")
    public ModelAndView getAddPetForm(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView(VIEW_PET_EDIT_FORM);
        Owner owner = ownerService.findById(id);
        Pet pet = new Pet();
        pet.setOwner(owner);
        modelAndView.addObject(ATTRIBUTE_PET, pet);
        return modelAndView;
    }
}