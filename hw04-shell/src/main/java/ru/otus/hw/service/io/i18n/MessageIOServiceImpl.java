package ru.otus.hw.service.io.i18n;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.service.io.IOService;

@Component
@RequiredArgsConstructor
public class MessageIOServiceImpl implements MessageIOService {

    private final MessageService messageService;

    private final IOService ioService;

    @Override
    public void printMessage(String key, Object params) {
        ioService.printLine(messageService.get(key, params));
    }

    @Override
    public void printMessage(String key) {
        ioService.printLine(messageService.get(key));
    }
}
