import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseTest {

    @Test
    void setWeight() {
        Exercise ex = new Exercise(0, "Aperturas", 1, 0, "Pectoral", "Pectoral", "https://www.youtube.com/watch?v=xyHdY99F640");
        ex.setWeight(10);
        assertEquals(10, ex.getWeight());
    }

    @Test
    void setSeries() {
        Exercise ex = new Exercise(0, "Aperturas", 1, 0, "Pectoral", "Pectoral", "https://www.youtube.com/watch?v=xyHdY99F640");
        ex.setSeries(4);
        assertEquals(4, ex.getSeries());
    }

    @Test
    void setRepes() {
        Exercise ex = new Exercise(0, "Aperturas", 1, 0, "Pectoral", "Pectoral", "https://www.youtube.com/watch?v=xyHdY99F640");
        ex.setRepes(12);
        assertEquals(12, ex.getRepes());
    }

    @Test
    void addDetails() {
        Exercise ex = new Exercise(0, "Aperturas", 1, 0, "Pectoral", "Pectoral", "https://www.youtube.com/watch?v=xyHdY99F640");
        ex.addDetails(10, 4, 12);
        assertAll(() -> assertEquals(10, ex.getWeight()),
                () -> assertEquals(4, ex.getSeries()),
                () -> assertEquals(12, ex.getRepes()));
    }
}