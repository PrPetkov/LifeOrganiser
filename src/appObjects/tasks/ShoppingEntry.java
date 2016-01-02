package appObjects.tasks;


import Interfaces.IShoppingEntry;

public class ShoppingEntry implements IShoppingEntry {

    private String productName;
    private String description;
    private double quantity;
    private boolean isBought;

    public ShoppingEntry(String productName) {

        this.setProductName(productName);

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setIsBought(boolean isBought) {
        this.isBought = isBought;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    private void setProductName(String productName) {
        this.productName = productName;
    }
}
