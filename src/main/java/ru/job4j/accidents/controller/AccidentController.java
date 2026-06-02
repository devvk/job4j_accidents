package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {

    private final AccidentService accidentService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("accidents", accidentService.getAll());
        model.addAttribute("user", Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal());
        return "accidents/list";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model) {
        model.addAttribute("accident", new Accident());
        model.addAttribute("types", accidentService.getAccidentTypes());
        model.addAttribute("rules", accidentService.getRules());
        return "accidents/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Accident accident,
                         @RequestParam(name = "ruleIds", required = false) List<Integer> ruleIds) {
        Accident savedAccident = accidentService.save(accident, ruleIds);
        return "redirect:/accidents/" + savedAccident.getId();
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable Integer id, Model model) {
        Optional<Accident> accidentOptional = accidentService.getById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("error", "Accident not found");
            return "error/404";
        }
        Accident accident = accidentOptional.get();
        model.addAttribute("accident", accident);
        model.addAttribute("types", accidentService.getAccidentTypes());
        model.addAttribute("rules", accidentService.getRules());
        model.addAttribute("selectedRuleIds", accidentService.getSelectedRuleIds(accident));
        return "accidents/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id,
                         @ModelAttribute Accident accident,
                         @RequestParam(name = "ruleIds", required = false) List<Integer> ruleIds,
                         Model model) {
        accident.setId(id);
        boolean isUpdated = accidentService.update(accident, ruleIds);
        if (!isUpdated) {
            model.addAttribute("error", "Accident not found");
            return "error/404";
        }
        return "redirect:/accidents/" + id;
    }

    @GetMapping("/{id}")
    public String getDetails(@PathVariable Integer id, Model model) {
        Optional<Accident> accidentOptional = accidentService.getById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("error", "Accident not found");
            return "error/404";
        }
        model.addAttribute("accident", accidentOptional.get());
        return "accidents/details";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        boolean isDeleted = accidentService.delete(id);
        if (!isDeleted) {
            model.addAttribute("error", "Accident not found");
            return "error/404";
        }
        return "redirect:/accidents";
    }
}
