package model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class Order {

    private ArrayList<String> ingredients;

    public Order(ArrayList<String> id) {
        this.ingredients = id;
    }



}
