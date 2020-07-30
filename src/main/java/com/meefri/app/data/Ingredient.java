package com.meefri.app.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "ingredients")
public class Ingredient {

    @Id
    private String id;

    private String category;

    private String name;


}
