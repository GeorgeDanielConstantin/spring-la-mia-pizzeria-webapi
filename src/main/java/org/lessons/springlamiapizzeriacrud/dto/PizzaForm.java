package org.lessons.springlamiapizzeriacrud.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.lessons.springlamiapizzeriacrud.model.Ingredient;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PizzaForm {

    private Integer id;

    @NotBlank(message = "Il nome non può essere nullo")
    private String name;

    @NotBlank(message = "La descrizione non può essere nulla")
    private String description;


    private MultipartFile imgUrl;

    @NotNull(message = "Il prezzo non può essere nullo")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;


    private List<Ingredient> ingredients = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(MultipartFile imgUrl) {
        this.imgUrl = imgUrl;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
