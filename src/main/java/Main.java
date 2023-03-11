import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        TicketService ts = new TicketService();
        List<Ticket> tickets = ts.convertJSONToObject("tickets.json");
        ts.averageFlightTime(tickets, "Тель-Авив", "Владивосток");
        ts.percentile(tickets, 90, "Тель-Авив", "Владивосток");
    }
}