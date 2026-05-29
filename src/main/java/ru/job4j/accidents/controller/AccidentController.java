package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/accidents")
public class AccidentController {

    private final AccidentService accidentService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("accidents", accidentService.getAll());
        return "accidents/list";
    }

    @GetMapping("/create")
    public String getCreateForm(Model model) {
        model.addAttribute("accident", new Accident());
        model.addAttribute("types", accidentService.getAccidentTypes());
        return "accidents/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Accident accident) {
        Accident savedAccident = accidentService.save(accident);
        return "redirect:/accidents/" + savedAccident.getId();
    }

    @GetMapping("/edit/{id}")
    public String getEditForm(@PathVariable Integer id, Model model) {
        Optional<Accident> accidentOptional = accidentService.getById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("error", "Accident not found");
            return "error/404";
        }
        model.addAttribute("accident", accidentOptional.get());
        model.addAttribute("types", accidentService.getAccidentTypes());
        return "accidents/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, @ModelAttribute Accident accident, Model model) {
        accident.setId(id);
        boolean isUpdated = accidentService.update(accident);
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
