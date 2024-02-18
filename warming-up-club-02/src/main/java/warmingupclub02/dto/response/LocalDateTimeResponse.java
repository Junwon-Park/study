package warmingupclub02.dto.response;

public class LocalDateTimeResponse {
    String dayOfTheWeek;

    public LocalDateTimeResponse(String dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }
}
