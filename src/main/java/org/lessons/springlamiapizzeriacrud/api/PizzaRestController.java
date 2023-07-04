package org.lessons.springlamiapizzeriacrud.api;

import jakarta.validation.Valid;
import org.lessons.springlamiapizzeriacrud.model.Pizza;
import org.lessons.springlamiapizzeriacrud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/pizza")
public class PizzaRestController {

    @Autowired
    PizzaService pizzaService;

    @GetMapping
    public Page<Pizza> page(@RequestParam Optional<String> keyword,
                            @RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return pizzaService.getAll(keyword, pageable);
    }

    @GetMapping("/{id}")
    public Pizza get(@PathVariable Integer id) {

        try {
            return pizzaService.getbyId(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public Pizza create(@Valid @RequestBody Pizza pizza) {

        try {
            return pizzaService.create(pizza);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        pizzaService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Pizza update(@PathVariable Integer id, @Valid @RequestBody Pizza pizza) {
        pizza.setId(id);
        return pizzaService.update(id, pizza);
    }
}
