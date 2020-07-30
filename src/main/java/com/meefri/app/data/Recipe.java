package com.meefri.app.data;

import com.vaadin.flow.component.html.Image;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@Document(collection = "recipes")
public class Recipe {

    @Id
    private String id;

    private String name;

    private String content;

    private String userId;

    private List<Ingredient> listOfIngredients;

    private List<byte[]> listOfImages;

    private Date date;

    private int numberOfLikes;

}
