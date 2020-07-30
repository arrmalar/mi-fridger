package com.meefri.app.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "likes")
public class Like {

    @Id
    private String id;

    private int counter;

    private String recipeID;

}
