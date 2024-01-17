package ru.otus.spring.bookstore.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.bookstore.dtos.AuthorDto;
import ru.otus.spring.bookstore.services.AuthorService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(path = {"/authors/list", "/authors"})
    public String authorList(Model model) {
        List<AuthorDto> authorDtos = authorService.findAll();
        model.addAttribute("authors", authorDtos);
        return "author-list";
    }

    @GetMapping("/authors/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        AuthorDto author;
        if (!(id == 0)) {
            author = authorService.findById(id);
        } else {
            author = new AuthorDto();
        }
        model.addAttribute("author", author);
        return "author-edit";
    }

    @PostMapping("/authors/edit")
    public String saveAuthor(@Valid @ModelAttribute("author") AuthorDto author,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "author-edit";
        }

        authorService.update(author.getId(), author.getFullName());
        return "redirect:/authors";
    }

    @GetMapping("/authors/delete")
    public String deleteAuthor(@RequestParam("id") long id) {
        authorService.deleteById(id);
        return "redirect:/authors";
    }
}
