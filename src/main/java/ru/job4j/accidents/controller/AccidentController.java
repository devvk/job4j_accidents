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
    public String create(Model model) {
        model.addAttribute("accident", new Accident());
        return "accidents/create";
    }

    @PostMapping("/create")
    public String save(@ModelAttribute Accident accident) {
        Accident savedAccident = accidentService.save(accident);
        return "redirect:/accidents/" + savedAccident.getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Optional<Accident> accidentOptional = accidentService.getById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("error", "Accident not found");
            return "error/404";
        }
        model.addAttribute("accident", accidentOptional.get());
        return "accidents/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable int id, @ModelAttribute Accident accident, Model model) {
        accident.setId(id);
        boolean isUpdated = accidentService.update(accident);
        if (!isUpdated) {
            model.addAttribute("error", "Accident not found");
            return "error/404";
        }
        return "redirect:/accidents/" + id;
    }

    @GetMapping("/{id}")
    public String details(@PathVariable int id, Model model) {
        Optional<Accident> accidentOptional = accidentService.getById(id);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("error", "Accident not found");
            return "error/404";
        }
        model.addAttribute("accident", accidentOptional.get());
        return "accidents/details";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        boolean isDeleted = accidentService.delete(id);
        if (!isDeleted) {
            model.addAttribute("error", "Accident not found");
            return "error/404";
        }
        return "redirect:/accidents";
    }
}
