package com.ulawil.dietapp.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface FoodRepositorySql extends FoodRepository, JpaRepository<Food100g, Integer> {

    @Override
    List<Food100g> findByNameContainsIgnoreCase(String name);

    @Override
    @Query("select f from Food100g f where f.name like %:name% and (f.user.id=:id or f.user is null)")
    List<Food100g> findUsersAndCommonFoodsByName(@Param("name") String name, @Param("id") int userId);
}
