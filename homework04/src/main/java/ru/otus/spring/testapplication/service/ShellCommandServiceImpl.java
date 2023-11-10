package ru.otus.spring.testapplication.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.testapplication.config.LocaleProvider;

@Service
@AllArgsConstructor
public class ShellCommandServiceImpl implements ShellCommandService {
    private final TestRunnerServiceImpl testRunnerService;

    private final IOService ioService;

    private final LocaleProvider localeProvider;

    public void runApplication() {
        testRunnerService.run();
    }

    public void changeLocale() {
        var possibleLocales = localeProvider.getPossibleLocales();
        for (int i = 1; i <= possibleLocales.size(); i++) {
            ioService.printFormattedLine("%s.%s", i, possibleLocales.get(i - 1));
        }

        int localeNumber = ioService.readIntForRangeWithPrompt(1, 2, "Choose the language you want: ",
                "You enter incorrect option");
        localeProvider.setCurrentLocale(possibleLocales.get(localeNumber - 1));
        ioService.printFormattedLine("You choose %s locale%n", localeProvider.getCurrentLocale());
    }
}
