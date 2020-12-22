package com.h3c.vdi.viewscreen.config;

import com.google.gson.*;
import com.h3c.vdi.viewscreen.constant.Constant;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author lgq
 * @since 2020/6/28 10:45
 */
public class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    @Override
    public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern(Constant.DateTimeFormat.DATE_TIME_PATTERN)));
    }


    @Override
    public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(),DateTimeFormatter.ofPattern(Constant.DateTimeFormat.DATE_TIME_PATTERN));
        //下面这个也行，并且当时间带时区时就必须用下面这个
        //return ZonedDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString()).toLocalDateTime();
    }


}
