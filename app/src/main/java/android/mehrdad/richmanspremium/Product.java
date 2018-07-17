package android.mehrdad.richmanspremium;

import java.util.ArrayList;
import java.util.List;

public class Product {
    String name;
    String price;
    int ID;
    List<Integer> images;
    String description;

    public Product() {
        images = new ArrayList<>();
    }
}
