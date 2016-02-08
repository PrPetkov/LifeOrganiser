package Models.tasks;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RecieveMoneyTask extends Task {

    private BigDecimal ammount;

    public RecieveMoneyTask(String taskName, LocalDateTime dateTime, BigDecimal ammount) {
        super(taskName, dateTime);

        this.setAmmount(ammount);
    }

    public BigDecimal getAmmount() {
        return ammount;
    }

    private void setAmmount(BigDecimal ammount) {
        if (ammount != null && ammount.compareTo(new BigDecimal(0)) >= 0) {
            this.ammount = ammount;
        }
    }
}
