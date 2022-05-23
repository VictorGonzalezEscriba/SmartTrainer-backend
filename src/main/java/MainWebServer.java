import org.json.simple.parser.ParseException;
import java.io.IOException;

public class MainWebServer {
    public static void main(String[] args) throws IOException, ParseException {
        webServer();
    }

    public static void webServer() throws IOException, ParseException {
        // Aqui inicias el backend, las estructuras de datos que necesites tener cargadas. És el constructor del servidor
        new WebServer();
    }
}
