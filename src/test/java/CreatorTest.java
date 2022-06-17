import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CreatorTest {

    @Test
    void fillCatalog() throws IOException, ParseException {
        Creator c = new Creator();
        c.fillCatalog(0);
        assertAll(
                ()-> assertEquals(3, c.getM_catalog().getExercise(0).getSeries()),
                ()-> assertEquals(5, c.getM_catalog().getExercise(0).getRepes())
        );
    }

    @Test
    void createTraining() throws IOException, ParseException {
        Creator c = new Creator();
        Training test = c.createTraining(1, 1, 1);
        assertEquals(5, test.getNExercises());
    }
}