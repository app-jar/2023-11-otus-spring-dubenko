package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        // Использовать CsvToBean
        // https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings
        // Использовать QuestionReadException
        // Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/

        final var fileName = fileNameProvider.getTestFileName();
        try (final var iStream = getInputStream(fileName)) {
            final var data = readData(iStream);
            return convertToDomain(data);
        } catch (Exception ex) {
            throw new QuestionReadException("Error while reading questions", ex);
        }
    }

    private List<QuestionDto> readData(InputStream iStream) {
         return new CsvToBeanBuilder<QuestionDto>(new InputStreamReader(iStream))
                 .withType(QuestionDto.class)
                 .withSkipLines(1)
                 .withSeparator(';')
                 .build().parse();
    }

    private InputStream getInputStream(String fileName) {
        return getClass().getClassLoader().getResourceAsStream(fileName);
    }

    private List<Question> convertToDomain(List<QuestionDto> data) {
        return data.stream().map(QuestionDto::toDomainObject).toList();
    }
}
