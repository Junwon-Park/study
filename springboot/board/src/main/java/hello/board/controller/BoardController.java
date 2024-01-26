package hello.board.controller;

import hello.board.dto.BoardDto;
import hello.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDto boardDto) {
        System.out.println("boardDto = " + boardDto);
        boardService.save(boardDto);

        return "index";
    }

    @GetMapping("/")
    public String findAll(Model model) {
        List<BoardDto> boardDtoList = boardService.findAll();
        model.addAttribute("boardList", boardDtoList);

        return "list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        log.info("Here");
        boardService.updateHits(id);
        BoardDto boardDto = boardService.findbyId(id);
        model.addAttribute("board", boardDto);
        log.info("Model={}", model);
        return "detail";
    }

    @GetMapping("/update/{id}")
    public String updatForm(@PathVariable("id") Long id, Model model) {
        BoardDto boardDto = boardService.findbyId(id);
        model.addAttribute("boardUpdate", boardDto);

        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDto boardDto, Model model) {
        BoardDto board = boardService.update(boardDto);
        model.addAttribute("board", board);

        return "detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.delete(id);

        return "redirect:/board/";
    }
}
