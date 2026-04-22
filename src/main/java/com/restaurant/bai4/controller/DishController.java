package com.restaurant.bai4.controller;

import com.restaurant.bai4.service.DishService;
import com.restaurant.common.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/bai4")
public class DishController {
    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService){
        this.dishService = dishService;
    }

    @GetMapping("/dishes")
    public String showDishList(Model model){
        List<Dish> dishes = dishService.getAllDishes();

        model.addAttribute("dishes", dishes);

        return "dish-list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(
            @PathVariable("id") int id,
            Model model,
            RedirectAttributes redirectAttributes
    ){
        Dish dish = dishService.getDishById(id);
        if (dish == null){
            redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy món ăn yêu cầu !");

            return "redirect:/bai4/dishes";
        }

        model.addAttribute("dish", dish);
        return "edit-dish";
    }

    @PostMapping("/edit")
    public String saveDish(@ModelAttribute("dish") Dish dish) {
        dishService.updateDish(dish);
        return "redirect:/bai4/dishes";
    }
}
