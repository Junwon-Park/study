package warmingupclub02.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import warmingupclub02.LocalDateTimeService;
import warmingupclub02.dto.request.FruitRequest;
import warmingupclub02.dto.request.SumRequest;
import warmingupclub02.dto.response.CalculatorResponse;
import warmingupclub02.dto.response.LocalDateTimeResponse;

import java.time.LocalDate;
import java.util.Arrays;

@RestController
@RequestMapping("/api/v1")
public class Work01Controller {

    private final LocalDateTimeService localDateTimeService;

    @Autowired
    public Work01Controller(LocalDateTimeService localDateTimeService) {
        this.localDateTimeService = localDateTimeService;
    }

    @GetMapping("/calc")
    public CalculatorResponse calculator(@RequestParam int num1, @RequestParam int num2) {
        return new CalculatorResponse(num1, num2);
    }

    @GetMapping("/day-of-the-week")
    public LocalDateTimeResponse localDateTime(@RequestParam String date) {
        LocalDate localDateDayOfTheWeek = localDateTimeService.getLocalDate(date);

        return new LocalDateTimeResponse(localDateDayOfTheWeek.getDayOfWeek().toString());
    }

    @PostMapping("/sum")
    public ResponseEntity<Integer> sum(@RequestBody SumRequest request) {
        return new ResponseEntity<>(Arrays.stream(request.getNumbers()).sum(), HttpStatus.OK);
    }

    @PostMapping("/fruit")
    public ResponseEntity<String> saveFruit(@RequestBody FruitRequest request) {
        System.out.println(request.getName());

        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
