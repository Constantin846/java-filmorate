package ru.yandex.practicum.filmorate.adapters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class InstantSerializer extends JsonSerializer<Instant> {
    @Override
    public void serialize(Instant instant, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        LocalDate localDate = LocalDate.ofInstant(instant, ZoneId.of("UTC0"));
        jsonGenerator.writeRawValue(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
