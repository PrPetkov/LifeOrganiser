package Models.tasks;


import Interfaces.IShoppingEntry;

public class ShoppingEntry implements IShoppingEntry {

    private String productName;
    private double quantity;
    private boolean isBought;

    public ShoppingEntry(String productName) {

        this.setProductName(productName);

    }

    public boolean isBought() {
        return isBought;
    }

    private void setIsBought(boolean isBought) {
        this.isBought = isBought;
    }

    public double getQuantity() {
        return quantity;
    }

    private void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    private void setProductName(String productName) {
        this.productName = productName;
    }
}
