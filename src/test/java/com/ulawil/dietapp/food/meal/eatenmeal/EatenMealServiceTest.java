package com.ulawil.dietapp.food.meal.eatenmeal;

import com.ulawil.dietapp.food.Food;
import com.ulawil.dietapp.food.FoodRepository;
import com.ulawil.dietapp.food.meal.Meal;
import com.ulawil.dietapp.food.meal.MealRepository;
import com.ulawil.dietapp.user.User;
import com.ulawil.dietapp.user.UserService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EatenMealServiceTest {

    @Test
    void addEatenMeal_shouldThrowIllegalState_whenNoUserLoggedIn() {
        // given
        UserService mockUserService = mock(UserService.class);
        when(mockUserService.getCurrentUser()).thenReturn(Optional.empty());
        // system under test
        EatenMealService toTest = new EatenMealService(null, null, null, mockUserService);
        // when
        Throwable exception = catchThrowable(() -> toTest.addEatenMeal(1, 1.));
        // then
        assertThat(exception).isInstanceOf(IllegalStateException.class);
        assertThat(exception).hasMessageContaining("logged in");
    }

    @Test
    void addEatenMeal_shouldThrowIllegalArgException_whenMealNotFound() {
        // given
        UserService mockUserService = mock(UserService.class);
        when(mockUserService.getCurrentUser()).thenReturn(Optional.of(new User()));
        // and
        MealRepository mockMealRepo = mock(MealRepository.class);
        when(mockMealRepo.findById(anyInt())).thenReturn(Optional.empty());
        // and
        InMemoryEatenRepoMeal inMemoryEatenMealRepo = new InMemoryEatenRepoMeal();
        // system under test
        EatenMealService toTest = new EatenMealService(inMemoryEatenMealRepo, mockMealRepo, null, mockUserService);
        // when
        Throwable exception = catchThrowable(() -> toTest.addEatenMeal(1, 1.));
        // then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessageContaining("not found");
    }

    @Test
    void addEatenMeal_shouldAddMeal() {
        // given
        User user = new User();
        UserService mockUserService = mock(UserService.class);
        when(mockUserService.getCurrentUser()).thenReturn(Optional.of(user));
        // and
        Meal meal = new Meal();
        MealRepository mockMealRepo = mock(MealRepository.class);
        when(mockMealRepo.findById(anyInt())).thenReturn(Optional.of(meal));
        // and
        InMemoryEatenRepoMeal inMemoryEatenMealRepo = new InMemoryEatenRepoMeal();
        int countBeforeAdd = inMemoryEatenMealRepo.count();
        // system under test
        EatenMealService toTest = new EatenMealService(inMemoryEatenMealRepo, mockMealRepo, null, mockUserService);
        // when
        toTest.addEatenMeal(1, 1.);
        // then
        assertThat(inMemoryEatenMealRepo.count()).isEqualTo(countBeforeAdd + 1);
        assertThat(inMemoryEatenMealRepo.findById(1)).isPresent();
        assertThat(inMemoryEatenMealRepo.findById(1)).get().hasFieldOrPropertyWithValue("meal", meal);
        assertThat(inMemoryEatenMealRepo.findById(1)).get().hasFieldOrPropertyWithValue("user", user);
    }

    @Test
    void addEatenFood_shouldAddFood() {
        // given
        UserService mockUserService = mock(UserService.class);
        when(mockUserService.getCurrentUser()).thenReturn(Optional.of(new User()));
        // and
        MealRepository mockMealRepo = mock(MealRepository.class);
        // and
        FoodRepository mockFoodRepo = mock(FoodRepository.class);
        when(mockFoodRepo.findById(anyInt())).thenReturn(Optional.of(new Food()));
        // and
        InMemoryEatenRepoMeal inMemoryEatenMealRepo = new InMemoryEatenRepoMeal();
        int countBeforeAdd = inMemoryEatenMealRepo.count();
        // system under test
        EatenMealService toTest = new EatenMealService(inMemoryEatenMealRepo, mockMealRepo, mockFoodRepo, mockUserService);
        // when
        toTest.addEatenFood(1, 1.);
        // then
        assertThat(inMemoryEatenMealRepo.count()).isEqualTo(countBeforeAdd + 1);
    }

    @Test
    void deleteEatenMeal_shouldThrowIllegalArgException_whenMealNotFound() {
        // given
        InMemoryEatenRepoMeal inMemoryEatenMealRepo = new InMemoryEatenRepoMeal();
        // system under test
        EatenMealService toTest = new EatenMealService(inMemoryEatenMealRepo, null, null, null);
        // when
        Throwable exception = catchThrowable(() -> toTest.deleteEatenMeal(1));
        // then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessageContaining("not found");
    }

    @Test
    void deleteEatenMeal_shouldDeleteMeal() {
        // given
        EatenMeal eatenMeal = new EatenMeal();
        // and
        InMemoryEatenRepoMeal inMemoryEatenMealRepo = new InMemoryEatenRepoMeal();
        inMemoryEatenMealRepo.save(eatenMeal);
        int countBeforeDelete = inMemoryEatenMealRepo.count();
        // system under test
        EatenMealService toTest = new EatenMealService(inMemoryEatenMealRepo, null, null, null);
        // when
        toTest.deleteEatenMeal(1);
        // then
        assertThat(inMemoryEatenMealRepo.count()).isEqualTo(countBeforeDelete - 1);
        assertThat(inMemoryEatenMealRepo.findById(1)).isNotPresent();
    }

    private static class InMemoryEatenRepoMeal implements EatenMealRepository {
        private int index = 0;
        private final Map<Integer, EatenMeal> data = new HashMap<>();

        public int count() {
            return data.size();
        }

        @Override
        public EatenMeal save(EatenMeal eatenMeal) {
            if(eatenMeal.getId()==0) {
                eatenMeal.setId(++index);
            }
            data.put(index, eatenMeal);
            return eatenMeal;
        }

        @Override
        public Optional<EatenMeal> findById(Integer id) {
            return Optional.ofNullable(data.get(id));
        }

        @Override
        public void deleteById(Integer id) {
            data.remove(id);
        }

        @Override
        public List<EatenMeal> findByUserIdAndDateEaten(Integer id, LocalDate date) {
            return null;
        }
    }
}