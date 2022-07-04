package com.ulawil.dietapp.food.meal;

import com.ulawil.dietapp.user.User;
import com.ulawil.dietapp.user.UserService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MealServiceTest {

    @Test
    void shouldAddMealWhenUserPresent() {
        // given
        User user = new User();
        UserService mockUserService = mock(UserService.class);
        when(mockUserService.getCurrentUser()).thenReturn(Optional.of(user));
        // and
        Meal meal = new Meal();
        meal.setName("foo");
        InMemoryMealRepo mockMealRepo = new InMemoryMealRepo();
        int countBeforeAdd = mockMealRepo.count();
        // system under test
        MealService toTest = new MealService(mockMealRepo, mockUserService);
        // when
        toTest.addMeal(meal);
        // then
        assertThat(mockMealRepo.count()).isEqualTo(countBeforeAdd + 1);
        assertThat(mockMealRepo.findById(1)).isPresent();
        assertThat(mockMealRepo.findById(1)).get().hasFieldOrPropertyWithValue("name", "foo");
        assertThat(mockMealRepo.findById(1)).get().hasFieldOrPropertyWithValue("user", user);
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
            return Optional.ofNullable(data.get(id));
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