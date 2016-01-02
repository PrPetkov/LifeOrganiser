package states;


import appObjects.User;
import appObjects.tasks.ShoppingList;
import appObjects.tasks.Task;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ShopingListState extends TaskState {


    public ShopingListState(User user, ShoppingList shoppingList) {
        super(user, shoppingList);
    }

    @Override
    public void draw() {
        throw new NotImplementedException();
    }
}
