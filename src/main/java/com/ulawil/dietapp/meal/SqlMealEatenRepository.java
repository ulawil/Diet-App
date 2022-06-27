package com.ulawil.dietapp.meal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
interface SqlMealEatenRepository extends MealEatenRepository, JpaRepository<MealEaten, Integer> {

    List<MealEaten> findByUserIdIsAndDateEatenBetween(@Param("userId") int userId,
                                                      @Param("dayBefore") LocalDateTime dayBefore,
                                                      @Param("dayAfter") LocalDateTime dayAfter);

    void deleteById(Integer id);
}
