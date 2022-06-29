package com.ulawil.dietapp.service;

import com.ulawil.dietapp.meal.*;
import com.ulawil.dietapp.user.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MealServiceTest {

    @Test
    void addMealEaten_shouldThrowIllegalArgException_MealIdNotFound() {
        // given
        MealRepository mealRepo = mock(MealRepository.class);
        when(mealRepo.findById(anyInt())).thenReturn(Optional.empty());
        // and
        InMemoryMealEatenRepo mealEatenRepo = new InMemoryMealEatenRepo();
        int countBeforeAdd = mealEatenRepo.count();
        // system under test
        //MealService toTest = new MealService(mealRepo, mealEatenRepo, null, null);
        // when
        //Throwable exception = catchThrowable(() -> toTest.addMealEaten(1, new User()));
        // then
        //assertThat(exception).isInstanceOf(IllegalArgumentException.class);
        //assertThat(exception).message().isEqualTo("Meal not found");
    }

    @Test
    void addMealEaten_shouldAddMealEatenWhenMealIdAndUserPresent() {
        // given
        Meal mealToAdd = new Meal();
        // and
        MealRepository mealRepo = mock(MealRepository.class);
        when(mealRepo.findById(anyInt())).thenReturn(Optional.of(mealToAdd));
        // and
        InMemoryMealEatenRepo mealEatenRepo = new InMemoryMealEatenRepo();
        int countBeforeAdd = mealEatenRepo.count();
        // system under test
        //MealService toTest = new MealService(mealRepo, mealEatenRepo, null, null);
        // when
        //MealEaten result = toTest.addMealEaten(1, new User());
        // then
        assertThat(mealEatenRepo.count()).isEqualTo(countBeforeAdd + 1);
    }

    @Test
    void shouldAddMealWhenUserPresent() {
        // given
        InMemoryMealRepo mealRepo = new InMemoryMealRepo();
        int countBeforeAdd = mealRepo.count();
        // system under test
       // MealService toTest = new MealService(mealRepo, null, null, null);
        // when
        //toTest.AddMeal("foo", new User());
        // then
        assertThat(mealRepo.count()).isEqualTo(countBeforeAdd+1);
    }

    private static class InMemoryMealEatenRepo implements MealEatenRepository {
        private int index = 0;
        private final Map<Integer, MealEaten> data = new HashMap<>();

        public int count() {
            return data.size();
        }

        @Override
        public MealEaten save(MealEaten mealEaten) {
            if(mealEaten.getId()==0) {
                mealEaten.setId(++index);
            }
            data.put(index, mealEaten);
            return mealEaten;
        }

        @Override
        public Optional<MealEaten> findById(Integer id) {
            return Optional.empty();
        }

        @Override
        public void deleteById(Integer id) {

        }

        @Override
        public List<MealEaten> findByUserIdAndDateEaten(Integer id, LocalDate date) {
            return null;
        }

        @Override
        public List<Object[]> findUsersTotalFoodStatsByDate(Integer id, LocalDate date) {
            return null;
        }
    }

    private static class InMemoryMealRepo implements MealRepository {
        private int index = 0;
        private final Map<Integer, Meal> data = new HashMap<>();

        public int count() {
            return data.size();
        }

        @Override
        public List<Meal> findAll() {
            return null;
        }

        @Override
        public Optional<Meal> findById(Integer id) {
            return Optional.empty();
        }

        @Override
        public List<Meal> findByNameContainsIgnoreCaseAndUserId(String name, Integer id) {
            return null;
        }

        @Override
        public Meal save(Meal mealToAdd) {
            if(mealToAdd.getId()==0) {
                mealToAdd.setId(++index);
            }
            data.put(index, mealToAdd);
            return mealToAdd;
        }
    }
}
