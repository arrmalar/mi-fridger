package com.meefri.app.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.meefri.app.data.Ingredient;
import com.meefri.app.data.Member;

import java.util.List;

@Repository
public interface IngredientRepository extends MongoRepository<Ingredient, String> {

    List<Ingredient> findAllByCategory();
}
