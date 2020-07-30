package com.meefri.app.data;

import com.meefri.app.repositories.CustomRecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecipeRepositoryImpl implements CustomRecipeRepository {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public RecipeRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<Recipe> findDoableRecipeByAvaliableIngredients(List<Ingredient> ingredientList) {
        Query query = new Query();
        Criteria criteriaMain = new Criteria();
        Criteria[] criteriaTab = new Criteria[ingredientList.size()];
        List<Criteria> criteriaList = new ArrayList<>();
        int index = 0;

        for(Ingredient i : ingredientList){
            criteriaTab[index++] = Criteria.where("listOfIngredients").in(i);
        }

        criteriaMain.orOperator(criteriaTab);

        query.addCriteria(criteriaMain);
        return mongoTemplate.find(query, Recipe.class);
    }
}
