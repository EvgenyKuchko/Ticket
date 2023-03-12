import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketService {

    public List<Ticket> convertJSONToObject(String filename) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/" + filename);

        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(LocalDate.class, new CustomDateDeserializer("dd.MM.yy"));
        module.addDeserializer(LocalTime.class, new CustomTimeDeserializer("H:mm"));
        objectMapper.registerModule(module);
        TicketList list = objectMapper.readValue(inputStream, TicketList.class);

        return list.getTickets();
    }

    public void averageFlightTime(List<Ticket> tickets, String cityOne, String cityTwo) {
        Duration totalDuration = Duration.ZERO;
        for (Ticket t : tickets) {
            if (t.getOrigin_name().equals(cityOne) && t.getDestination_name().equals(cityTwo) ||
                    t.getOrigin_name().equals(cityTwo) && t.getDestination_name().equals(cityOne)) {
                totalDuration = totalDuration.plus(Duration.between(t.getDeparture_time(), t.getArrival_time()));
            }
        }

        Duration averageDuration = totalDuration.dividedBy(tickets.size());

        LocalTime averageFlightTime = LocalTime.MIDNIGHT.plus(averageDuration);
        System.out.println("Среднее время полета: " + averageFlightTime);
    }

    public void percentile(List<Ticket> tickets, int per, String cityOne, String cityTwo) {
        List<LocalTime> timeList = new ArrayList<>();
        for (Ticket t : tickets) {
            if (t.getOrigin_name().equals(cityOne) && t.getDestination_name().equals(cityTwo) ||
                    t.getOrigin_name().equals(cityTwo) && t.getDestination_name().equals(cityOne)) {
                Duration totalDuration = Duration.ZERO;
                totalDuration = totalDuration.plus(Duration.between(t.getDeparture_time(), t.getArrival_time()));
                LocalTime time = LocalTime.MIDNIGHT.plus(totalDuration);
                timeList.add(time);
            }
        }

        Collections.sort(timeList);
        LocalTime percent = timeList.get((timeList.size() * per / 100) - 1);
        System.out.println("90-й перцентиль: " + percent);
    }
}