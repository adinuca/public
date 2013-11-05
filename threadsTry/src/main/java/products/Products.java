package products;

public enum Products {
    NUTS(3000), FLOUR(5000), CHOCOLATE_CAKE(10), BUTTER(3000), CHOCOLATE(2000), MILK(5000), SUGAR(5000), BISCUITS(1000), VANILLA(150), PACK_OF_BISCUITS(10), WRAPPING_PAPER(10);

    private final int maximumQuantity;

    private Products(int quantity) {
        this.maximumQuantity = quantity;
    }

    public int getMaximumQuantity() {
        return maximumQuantity;
    }
}
