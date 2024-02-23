package warmingupclub02.dto.request;

import java.time.LocalDate;

public class FruitRequest {
    String name;
    LocalDate warehousingDate;
    long price;

    public String getName() {
        return name;
    }

    public LocalDate getWarehousingDate() {
        return warehousingDate;
    }

    public long getPrice() {
        return price;
    }
}
