import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CatalogTest {

    @Test
    void readExercises() throws IOException, ParseException {
        Catalog c = new Catalog();
        assertEquals(31, c.getExerciseList().size());
    }

    @Test
    void getExercise() throws IOException, ParseException {
        Catalog c = new Catalog();
        Exercise ex = c.getExercise(14);
        assertAll(
                () -> assertEquals(14, ex.getId()),
                () -> assertEquals("Fondos", ex.getName()),
                () -> assertEquals(1, ex.getLocation()),
                () -> assertEquals(0, ex.getBodyPart1()),
                () -> assertEquals("Brazo", ex.getBodyPart2()),
                () -> assertEquals("Tríceps", ex.getBodyPart3()),
                () -> assertEquals("https://www.youtube.com/watch?v=o2qX3Zb5mvg", ex.getLink())
        );
    }

    @Test
    void checkFilter() throws IOException, ParseException {
        Catalog c = new Catalog();
        List<Exercise> filter1 = c.checkFilter("Parte superior", c.getExerciseList());
        List<Exercise> filter2 = c.checkFilter("Abajo", c.getExerciseList());
        List<Exercise> filter3 = c.checkFilter("Brazo", c.getExerciseList());
        assertAll(
                () -> assertEquals(0, filter1.get(0).getBodyPart1()),
                () -> assertEquals(1, filter2.get(0).getBodyPart1()),
                () -> assertEquals("Brazo", filter3.get(0).getBodyPart2())
        );
    }

    @Test
    void filterLocation() throws IOException, ParseException {
        Catalog c = new Catalog();
        List<Exercise> filter1 = c.filterLocation(1);
        assertEquals(1, filter1.get(0).getLocation());
    }

}