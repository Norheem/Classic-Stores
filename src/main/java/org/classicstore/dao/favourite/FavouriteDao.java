package org.classicstore.dao.favourite;

import org.classicstore.model.Favourite;
import org.classicstore.model.Product;
import org.classicstore.model.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FavouriteDao {
    private final Map<Product, Favourite> favouriteItems = new HashMap<>();

    public String addToFavourite(User user, Product product) {
        if (favouriteItems.containsKey(product)) {
            return product.getProductName() + " is already in your favorites!";
        }
        Favourite favourite = new Favourite(user, product, true);
        favouriteItems.put(product, favourite);
        return product.getProductName() + " added to your favorites!";
    }

    public void removeFromFavourite(User user, Product product) {
        favouriteItems.remove(product);
    }

    public List<Favourite> getFavouriteItems() {
      return new ArrayList<>(favouriteItems.values());
    }
}
