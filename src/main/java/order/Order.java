package order;

import java.util.ArrayList;

public class Order {

    private static final String fluorescentBunR2D3 = "61c0c5a71d1f82001bdaaa6c";
    private static final String FilletOfLuminescentTetraodontimform = "61c0c5a71d1f82001bdaaa6e";
    private final ArrayList<Object> ingredients;



    public Order(ArrayList<Object> ingredients) {
        this.ingredients = ingredients;
    }

    public static Order getOrder() {
        ArrayList<Object> order = new ArrayList<>();
        order.add(fluorescentBunR2D3);
        order.add(FilletOfLuminescentTetraodontimform);
        return new Order(order);
    }

    public static Order getIncorrectOrder() {
        ArrayList<Object> order = new ArrayList<>();
        order.add("incorrect1");
        order.add("incorrect2");
        return new Order(order);
    }
}
