package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.AgeRatingDto;
import ru.yandex.practicum.filmorate.services.film.AgeRatingService;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class AgeRatingController {
    private final AgeRatingService ageRatingService;

    @GetMapping("/{id}")
    public AgeRatingDto getById(@PathVariable("id") int ageRatingId) {
        return ageRatingService.getAgeRatingDtoById(ageRatingId);
    }

    @GetMapping
    public Set<AgeRatingDto> findAll() {
        TreeSet<AgeRatingDto> sortedAgeRatingDto = new TreeSet<>(Comparator.comparingInt(AgeRatingDto::getId));
        sortedAgeRatingDto.addAll(ageRatingService.findAllAgeRatingDto());
        return sortedAgeRatingDto;
    }
}
