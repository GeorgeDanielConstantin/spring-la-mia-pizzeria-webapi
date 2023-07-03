package org.lessons.springlamiapizzeriacrud.controller;

import jakarta.validation.Valid;
import org.lessons.springlamiapizzeriacrud.model.Discount;
import org.lessons.springlamiapizzeriacrud.model.Pizza;
import org.lessons.springlamiapizzeriacrud.repository.DiscountRepository;
import org.lessons.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Controller
@RequestMapping("/discount")
public class DiscountController {


    @Autowired
    PizzaRepository pizzaRepository;
    @Autowired
    DiscountRepository discountRepository;

    @GetMapping("/create")
    public String create(@RequestParam("pizzaId") Integer pizzaId, Model model) {
        Discount discount = new Discount();
        Optional<Pizza> pizza = pizzaRepository.findById(pizzaId);
        if (pizza.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Pizza con id " + pizzaId + " non trovata");
        }
        discount.setPizza(pizza.get());
        model.addAttribute("discount", discount);
        return "/discount/form";
    }


    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("discount") Discount formDiscount,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/discount/form";
        }
        discountRepository.save(formDiscount);
        return "redirect:/pizza/" + formDiscount.getPizza().getId();
    }


    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Discount> discount = discountRepository.findById(id);
        if (discount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("discount", discount.get());
        return "/discount/form";
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id,
                         @Valid @ModelAttribute("discount") Discount formDiscount,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/discount/form";
        }
        Optional<Discount> discount = discountRepository.findById(id);
        if (discount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        formDiscount.setId(id);
        discountRepository.save(formDiscount);
        return "redirect:/pizza/" + formDiscount.getPizza().getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Optional<Discount> discount = discountRepository.findById(id);
        if (discount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        discountRepository.delete(discount.get());
        return "redirect:/pizza/" + discount.get().getPizza().getId();
    }
}
