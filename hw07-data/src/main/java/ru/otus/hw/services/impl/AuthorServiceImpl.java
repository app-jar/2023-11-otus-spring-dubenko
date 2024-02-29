package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.mapper.AuthorMapper;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.services.AuthorService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> findAll() {
        return repository.findAll().stream()
                .map(AuthorMapper::toDto)
                .toList();
    }
}
