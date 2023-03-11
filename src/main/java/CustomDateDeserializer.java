import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class CustomDateDeserializer extends JsonDeserializer<LocalDate> {
    private final DateTimeFormatter dateFormat;

    public CustomDateDeserializer(String pattern) {
        this.dateFormat = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext ctxt) throws IOException {
        String dateString = parser.getText();
        return LocalDate.parse(dateString, dateFormat);
    }
}