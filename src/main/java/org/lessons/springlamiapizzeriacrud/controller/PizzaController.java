package org.lessons.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.lessons.springlamiapizzeriacrud.messages.AlertMessage;
import org.lessons.springlamiapizzeriacrud.messages.AlertMessageType;
import org.lessons.springlamiapizzeriacrud.model.Pizza;
import org.lessons.springlamiapizzeriacrud.repository.IngredientRepository;
import org.lessons.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizza")
public class PizzaController {
    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

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

        Pizza pizza = getPizzaById(id);
        model.addAttribute("pizza", pizza);
        return "/pizza/pizza_detail";

    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredientList", ingredientRepository.findAll());
        return "/pizza/pizza_edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Model model) {

        if (bindingResult.hasErrors()) {
            return "/pizza/pizza_edit";
        }
        pizzaRepository.save(formPizza);
        redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.SUCCESS, "Pizza " + formPizza.getName() + " creata!"));

        model.addAttribute("ingredientList", ingredientRepository.findAll());
        return "redirect:/pizza";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {

        Pizza pizza = getPizzaById(id);
        model.addAttribute("pizza", pizza);
        model.addAttribute("ingredientList", ingredientRepository.findAll());
        return "pizza/pizza_edit";

    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id,
                         @Valid @ModelAttribute("pizza") Pizza formPizza,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        Pizza pizza = getPizzaById(id);


        if (bindingResult.hasErrors()) {
            return "/pizza/pizza_edit";
        }
        formPizza.setId(pizza.getId());
        pizzaRepository.save(formPizza);
        redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.SUCCESS, "Pizza " + formPizza.getName() + " modificata!"));

        model.addAttribute("ingredientList", ingredientRepository.findAll());
        return "redirect:/pizza";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

        Pizza pizza = getPizzaById(id);
        pizzaRepository.delete(pizza);
        redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.SUCCESS, "Pizza " + pizza.getName() + " cancellata!"));
        return "redirect:/pizza";

    }


    private Pizza getPizzaById(Integer id) {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pizza con id " + id + " non trovata");
        }
        return result.get();
    }
}