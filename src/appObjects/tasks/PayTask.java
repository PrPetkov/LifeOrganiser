package appObjects.tasks;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PayTask extends Task {

    private BigDecimal ammount;

    public PayTask(String taskName, LocalDateTime dateTime, BigDecimal ammount) {
        super(taskName, dateTime);

        this.ammount = ammount;
    }
}
