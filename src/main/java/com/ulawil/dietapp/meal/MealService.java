package com.ulawil.dietapp.meal;

import com.ulawil.dietapp.food.Food;
import com.ulawil.dietapp.food.Ingredient;
import com.ulawil.dietapp.food.FoodRepository;
import com.ulawil.dietapp.user.User;
import com.ulawil.dietapp.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MealService {

    private final MealRepository mealRepository;
    private final MealEatenRepository mealEatenRepository;
    private final FoodRepository foodRepository;
    private Meal mealToAdd = new Meal();
    private final UserService userService;

    public MealService(MealRepository mealRepository,
                       MealEatenRepository mealEatenRepository,
                       FoodRepository foodRepository, UserService userService) {
        this.mealRepository = mealRepository;
        this.mealEatenRepository = mealEatenRepository;
        this.foodRepository = foodRepository;
        this.userService = userService;
    }

    public Object getMealToAdd() {
        return mealToAdd;
    }

    public List<Meal> findAllMeals() {
        return mealRepository.findAll();
    }

    public List<Meal> findUsersTodaysMeals(int userId) {
        LocalDateTime dayStart = LocalDate.now().atStartOfDay();
        LocalDateTime dayEnd = LocalDate.now().plusDays(1).atStartOfDay();
        return mealRepository.findByUserIdAndDateEatenBetween(userId, dayStart, dayEnd);
    }

    public Double findUsersTodaysTotalKcal(int userId) {
        Double sum = 0.;
        return findUsersTodaysMeals(userService.getLoggedInUser().getId()).stream()
                .flatMap(meal -> meal.getIngredients().stream())
                .map(Ingredient::getKcal).reduce(sum, Double::sum);
    }

    public List<Meal> findUsersMealsByName(String foodName, int userId) {
        return mealRepository.findByNameContainsIgnoreCaseAndUserId(foodName, userId);
    }

    public MealEaten addMealEaten(int mealId, User user) {
        Meal mealToAdd = mealRepository.findById(mealId).orElseThrow(
                () -> new IllegalArgumentException("Meal not found"));
        MealEaten mealEaten = new MealEaten();
        mealEaten.setMeal(mealToAdd);
        mealEaten.setUser(user);
        return  mealEatenRepository.save(mealEaten);
    }

    public void addIngredientToMealToAdd(int foodId, int foodAmount) {
        Food food = foodRepository.findById(foodId).orElseThrow(
                () -> new IllegalArgumentException("Meal not found"));
        mealToAdd.addIngredient(new Ingredient(food, foodAmount));
    }

    public void AddMeal(String mealName, User currentUser) {
        mealToAdd.setName(mealName);
        mealToAdd.setUser(currentUser);
        mealRepository.save(mealToAdd);
        mealToAdd = new Meal();
    }
}
