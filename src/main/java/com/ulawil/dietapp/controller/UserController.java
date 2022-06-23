package com.ulawil.dietapp.controller;

import com.ulawil.dietapp.model.DayOfEating;
import com.ulawil.dietapp.model.Meal;
import com.ulawil.dietapp.model.MealRecord;
import com.ulawil.dietapp.model.User;
import com.ulawil.dietapp.repository.DayOfEatingRepository;
import com.ulawil.dietapp.repository.MealRecordRepository;
import com.ulawil.dietapp.repository.MealRepository;
import com.ulawil.dietapp.repository.UserRepository;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final MealRecordRepository mealRecordRepository;
    private final DayOfEatingRepository dayOfEatingRepository;

    public UserController(UserRepository userRepository,
                          MealRepository mealRepository,
                          MealRecordRepository mealRecordRepository,
                          DayOfEatingRepository dayOfEatingRepository) {
        this.userRepository = userRepository;
        this.mealRepository = mealRepository;
        this.mealRecordRepository = mealRecordRepository;
        this.dayOfEatingRepository = dayOfEatingRepository;
    }

    @GetMapping()
    String showUserPage(Model model) {
        model.addAttribute("todaysMeals", todaysMeals());
        return "user";
    }

    @PostMapping(params = {"searchMeal"})
    String searchFoods(@RequestParam("mealName") String foodName, Model model) {
        List<Meal> foundMeals = mealRepository.findByNameContainsIgnoreCase(foodName);
        model.addAttribute("todaysMeals", todaysMeals());
        model.addAttribute("foundMeals", foundMeals);
        return "user";
    }

    @PostMapping(params = {"addMeal"},
            produces = MediaType.TEXT_HTML_VALUE
    )
    String addIngredient(@RequestParam("addMeal") int mealId, Model model) {
        // todo: put this all in service and test
        Meal mealToAdd = mealRepository.findById(mealId).get(); // always present bc it's taken from the db
        MealRecord mealRecord = new MealRecord();
        mealRecord.setMeal(mealToAdd);
        // get current user, id=1 atm
        User current = userRepository.findById(1).get();
        if(current.getDoes().stream().noneMatch(doe -> doe.getDate().equals(LocalDate.now()))) { // no meals eaten today
            // create a new day of eating
            DayOfEating currentDayOfEating = new DayOfEating();
            currentDayOfEating.setUser(current);
            currentDayOfEating.setDate(LocalDate.now());
            // add a new meal record
            currentDayOfEating.getMealsEaten().add(mealRecord);
            mealRecord.setDayOfEating(currentDayOfEating);
            dayOfEatingRepository.save(currentDayOfEating);
        }
        else {
            Optional<DayOfEating> currentDayOfEating = dayOfEatingRepository.findByDateIs(LocalDate.now());
            currentDayOfEating.ifPresent(dayOfEating -> {
                dayOfEating.getMealsEaten().add(mealRecord);
                mealRecord.setDayOfEating(dayOfEating);
                dayOfEatingRepository.save(dayOfEating);
            });
        }
        model.addAttribute("todaysMeals", todaysMeals());
        return "user";
    }

    @ModelAttribute
    List<MealRecord> todaysMeals() {
        // todo: get rid of n+1
        LocalDateTime dayStart = LocalDate.now().atStartOfDay();
        LocalDateTime dayEnd = LocalDate.now().plusDays(1).atStartOfDay();
        return mealRecordRepository.findByDateEatenBetween(dayStart, dayEnd);
    }
}
