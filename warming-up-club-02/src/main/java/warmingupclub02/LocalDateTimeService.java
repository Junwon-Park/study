package warmingupclub02;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LocalDateTimeService {

    public LocalDate getLocalDate(String date) {
        String[] dateString = date.split("-");
        return LocalDate.of(Integer.parseInt(dateString[0]), Integer.parseInt(dateString[1]), Integer.parseInt(dateString[2]));
    }
}
