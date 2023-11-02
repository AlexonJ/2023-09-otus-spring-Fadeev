package ru.otus.spring.testapplication.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.testapplication.service.ShellCommandService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {

    private final ShellCommandService shellService;

    @ShellMethod("Start testing")
    public void start() {
        shellService.runApplication();
    }

    @ShellMethod("Change language")
    public void lang() {
        shellService.changeLocale();
    }
}
