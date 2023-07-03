package org.lessons.springlamiapizzeriacrud.controller;


import jakarta.validation.Valid;
import org.lessons.springlamiapizzeriacrud.model.Ingredient;
import org.lessons.springlamiapizzeriacrud.model.Pizza;
import org.lessons.springlamiapizzeriacrud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String index(Model model,
                        @RequestParam("edit") Optional<Integer> ingredientId) {
        List<Ingredient> ingredientList = ingredientRepository.findAll();
        model.addAttribute("ingredients", ingredientList);

        Ingredient ingredientObj;
        if (ingredientId.isPresent()) {
            Optional<Ingredient> ingredientDb = ingredientRepository.findById(ingredientId.get());
            ingredientObj = ingredientDb.orElseGet(Ingredient::new);
        } else {
            ingredientObj = new Ingredient();
        }
        model.addAttribute("ingredientObj", ingredientObj);
        return "/ingredients/index";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("ingredientObj") Ingredient formIngredient,
                       BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientRepository.findAll());
            return "/ingredients/index";
        }
        ingredientRepository.save(formIngredient);
        return "redirect:/ingredients";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Optional<Ingredient> result = ingredientRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Ingredient ingredientToDelete = result.get();
        for (Pizza pizza : ingredientToDelete.getPizzas()) {
            pizza.getIngredients().remove(ingredientToDelete);
        }

        ingredientRepository.deleteById(id);
        return "redirect:/ingredients";
    }
}

