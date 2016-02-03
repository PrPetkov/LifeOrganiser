package appObjects.tasks;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RecieveMoneyTask extends Task {

    private BigDecimal ammount;

    public RecieveMoneyTask(String taskName, LocalDateTime dateTime, BigDecimal ammount) {
        super(taskName, dateTime);

        this.ammount = ammount;
    }
}
