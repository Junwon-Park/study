package homework.applylecture.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lectures")
public class LectureController {
    @GetMapping
    public String getLectures() {
        return "Lectures";
    }
}
