import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class CustomTimeDeserializer extends JsonDeserializer<LocalTime> {
    private final DateTimeFormatter timeFormat;

    public CustomTimeDeserializer(String pattern) {
        this.timeFormat = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public LocalTime deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        String timeString = parser.getText();
        return LocalTime.parse(timeString, timeFormat);
    }
}