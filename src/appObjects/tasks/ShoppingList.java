package appObjects.tasks;


import Interfaces.IShoppingEntry;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShoppingList extends Task {

    private List<IShoppingEntry> entries;

    public ShoppingList(String taskName, LocalDateTime date){
        super(taskName, date);

        this.entries = new ArrayList<IShoppingEntry>();

    }

    public void addEntry(IShoppingEntry entry){
        if (entry == null){
            return;
        }

        this.entries.add(entry);
    }

    public void removeEntry(IShoppingEntry entry){
        if (entry == null){
            return;
        }

        this.entries.remove(entry);
    }

    public Iterable<IShoppingEntry> getShoppingListEnties(){
        return this.entries;
    }

}
