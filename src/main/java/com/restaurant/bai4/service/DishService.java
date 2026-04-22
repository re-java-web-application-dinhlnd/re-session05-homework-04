package com.restaurant.bai4.service;

import com.restaurant.bai4.repository.DishRepository;
import com.restaurant.common.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {
    private final DishRepository dishRepository;

    @Autowired
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public List<Dish> getAllDishes(){
        return dishRepository.findAllDishes();
    }

    public Dish getDishById(int id){
        return dishRepository.findDishById(id);
    }

    public void updateDish(Dish dish){
        dishRepository.updateDish(dish);
    }
}
