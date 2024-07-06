package ru.yandex.practicum.filmorate.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.AgeRatingDto;
import ru.yandex.practicum.filmorate.services.film.AgeRatingService;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class AgeRatingController {
    private final AgeRatingService ageRatingService;

    @GetMapping("{id}")
    public AgeRatingDto getAgeRatingById(@PathVariable("id") int ageRatingId) {
        return ageRatingService.getAgeRatingById(ageRatingId);
    }

    @GetMapping
    public Collection<AgeRatingDto> findAllAgeRatings() {
        return ageRatingService.findAllAgeRatings();
    }
}
