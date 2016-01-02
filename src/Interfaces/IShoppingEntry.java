package Interfaces;


public interface IShoppingEntry {

    String getDescription();

    void setDescription(String description);

    boolean isBought();

    void setIsBought(boolean isBought);

    double getQuantity();

    void setQuantity(double quantity);

    String getProductName();

}
