package ru.yandex.practicum.filmorate.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.yandex.practicum.filmorate.model.film.AgeRating;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AgeRatingDto {
    int id;
    String name;

    public void setName(String name) {
       switch (AgeRating.valueOf(name)) {
           case PG_13 -> this.name = "PG-13";
           case NC_17 -> this.name = "NC-17";
           default -> this.name = name;
       }
    }

    /*public String getName() {
        return switch (this.name) {
            case "PG-13" -> "PG_13";
            case "NC-17" -> "NC_17";
            default -> this.name;
        };
    }*/
}
