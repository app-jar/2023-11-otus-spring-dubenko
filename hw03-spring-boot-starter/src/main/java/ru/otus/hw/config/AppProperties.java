package ru.otus.hw.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@Data
public class AppProperties implements TestConfig, TestFileNameProvider, MessageConfig {
    @Value("${test.rightAnswersCountToPass:3}")
    private int rightAnswersCountToPass;

    @Value("${test.fileName:questions}")
    private String testFileName;

    @Value("${test.file.root}")
    private String testFileRoot;

    @Value("${test.file.ext:csv}")
    private String testFileExt;

    @Value("${test.showErrors:false}")
    private boolean showErrors;

    @Value("${locale}")
    private String locale;

    public String getTestFileName() {
        return this.testFileRoot + File.separator + this.testFileName
                + "_" + this.locale + "." +  this.testFileExt;
    }
}
