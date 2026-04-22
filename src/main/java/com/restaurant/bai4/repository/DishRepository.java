package com.restaurant.bai4.repository;

import com.restaurant.common.model.Dish;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DishRepository {
    private List<Dish> dishes;

    public DishRepository() {
        this.dishes = new ArrayList<>();

        dishes.add(new Dish(1, "Thịt nướng BBQ", 150000, true));
        dishes.add(new Dish(2, "Lẩu Thái Tomyum", 300000, false));
        dishes.add(new Dish(3, "Salad cá ngừ", 75000, true));
        dishes.add(new Dish(4, "Gà rán ủ muối", 180000, false));
    }

    public List<Dish> findAllDishes() {
        return dishes;
    }

    public Dish findDishById(int id){
        return dishes.stream()
                .filter(dish -> dish.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void updateDish(Dish updatedDish){
        for(int i = 0; i < dishes.size(); i++){
            if(dishes.get(i).getId() == updatedDish.getId()){
                dishes.set(i, updatedDish);
                return;
            }
        }
    }
}
