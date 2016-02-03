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

        this.entries.add(entry);

    }

    public void removeEntry(IShoppingEntry entry){

        //TODO remove specific entry
        throw new NotImplementedException();

    }

    public Iterable<IShoppingEntry> getShoppingListEnties(){

        return this.entries;

    }

}
