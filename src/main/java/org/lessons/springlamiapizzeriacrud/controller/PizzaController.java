package org.lessons.springlamiapizzeriacrud.controller;

import org.lessons.springlamiapizzeriacrud.model.Pizza;
import org.lessons.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizza")
public class PizzaController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String list(
            @RequestParam(name = "keyword", required = false) String searchString,
            Model model) {
        List<Pizza> pizzas;

        if (searchString == null || searchString.isBlank()) {
            pizzas = pizzaRepository.findAll();
        } else {
            pizzas = pizzaRepository.findByNameContainingIgnoreCase(searchString);
        }

        model.addAttribute("pizzasList", pizzas);
        model.addAttribute("searchInput", searchString == null ? "" : searchString);
        return "/pizza/pizzas_list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {

        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {

            model.addAttribute("pizza", result.get());
            return "/pizza/pizza_detail";

        } else {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pizza with id " + id + " not found");
        }
    }
}