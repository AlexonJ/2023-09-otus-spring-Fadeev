package ru.otus.spring.testapplication.config;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.testapplication.service.IOService;
import ru.otus.spring.testapplication.service.TestRunnerServiceImpl;

@ShellComponent
@RequiredArgsConstructor
public class ShellConfig {

    private final TestRunnerServiceImpl testRunnerService;

    private final IOService ioService;

    private final LocaleProvider localeProvider;

    @ShellMethod("Start testing")
    public void start() {
        testRunnerService.run();
    }

    @ShellMethod("Change language")
    public void lang() {
        var possibleLocales = localeProvider.getPossibleLocales();
        for (int i = 1; i <= possibleLocales.size(); i++){
            ioService.printFormattedLine("%s.%s", i, possibleLocales.get(i-1));
        }

        int localeNumber = ioService.readIntForRangeWithPrompt(1,2, "Choose the language you want: ", "You enter incorrect option");
        localeProvider.setCurrentLocale(possibleLocales.get(localeNumber - 1));
        ioService.printFormattedLine("You choose %s locale%n", localeProvider.getCurrentLocale());

    }
}
