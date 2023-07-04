package org.lessons.springlamiapizzeriacrud.service;

import org.lessons.springlamiapizzeriacrud.model.Pizza;
import org.lessons.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PizzaService {

    @Autowired
    PizzaRepository pizzaRepository;

    public Page<Pizza> getAll(Optional<String> keywordOpt, Pageable pageable) {
        if (keywordOpt.isEmpty()) {
            return pizzaRepository.findAll(pageable);
        } else {
            return pizzaRepository.findByNameContainingIgnoreCase(keywordOpt.get(), pageable);
        }
    }

    public Pizza getbyId(Integer id) {
        Optional<Pizza> pizzaOpt = pizzaRepository.findById(id);
        if (pizzaOpt.isPresent()) {
            return pizzaOpt.get();
        } else {
            throw new RuntimeException();
        }
    }

    public Pizza create(Pizza pizza) {
        Pizza pizzaToPersist = new Pizza();
        pizzaToPersist.setName(pizza.getName());
        pizzaToPersist.setDescription(pizza.getDescription());
        pizzaToPersist.setImgUrl(pizza.getImgUrl());
        pizzaToPersist.setPrice(pizza.getPrice());
        pizzaToPersist.setIngredients(pizza.getIngredients());
        pizzaToPersist.setDiscounts(pizza.getDiscounts());

        return pizzaRepository.save(pizzaToPersist);
    }

    public void deleteById(Integer id) {
        if (pizzaRepository.existsById(id)) {
            pizzaRepository.deleteById(id);
        } else {
            throw new RuntimeException();
        }
    }

    public Pizza update(Integer id, Pizza updatedPizza) {
        Optional<Pizza> pizzaOpt = pizzaRepository.findById(id);
        if (pizzaOpt.isPresent()) {
            Pizza pizza = pizzaOpt.get();
            pizza.setName(updatedPizza.getName());
            pizza.setDescription(updatedPizza.getDescription());
            pizza.setImgUrl(updatedPizza.getImgUrl());
            pizza.setPrice(updatedPizza.getPrice());
            pizza.setIngredients(updatedPizza.getIngredients());
            pizza.setDiscounts(updatedPizza.getDiscounts());
            return pizzaRepository.save(pizza);
        } else {
            throw new RuntimeException();
        }
    }


}
