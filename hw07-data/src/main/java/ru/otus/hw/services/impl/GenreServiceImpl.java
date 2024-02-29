package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.dto.mapper.GenreMapper;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.services.GenreService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<GenreDto> findAll() {
        return repository.findAll().stream()
                .map(GenreMapper::toDto)
                .toList();
    }
}
