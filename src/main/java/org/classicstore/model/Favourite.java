package org.classicstore.model;

public class Favourite {
    private User user;
    private Product product;
    private boolean isLiked;

    public Favourite() {
    }

    public Favourite(User user, Product product, boolean isLiked) {
        this.user = user;
        this.product = product;
        this.isLiked = isLiked;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
