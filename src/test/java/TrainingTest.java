import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrainingTest {

    @Test
    void addExercise() {
        // Create a training and add an exercise
        Training test = new Training("Test", 0);
        Exercise ex = new Exercise(0, "Aperturas", 1, 0, "Pectoral", "Pectoral", "https://www.youtube.com/watch?v=xyHdY99F640");
        test.addExercise(ex);
        assertAll(
                () -> assertEquals(1, test.getNExercises()),
                () -> assertEquals(ex.getId(), test.getExercises().get(test.getNExercises()-1).getId())
        );
    }

    @Test
    void addDetailsExercise() {
        // Create a training and add an exercise
        Training test = new Training("Test", 0);
        Exercise ex = new Exercise(0, "Aperturas", 1, 0, "Pectoral", "Pectoral", "https://www.youtube.com/watch?v=xyHdY99F640");
        test.addExercise(ex);
        test.addDetailsExercise(0, 12.5, 4, 12);
        assertAll(() -> assertEquals(12.5, test.getExercises().get(0).getWeight()),
                () -> assertEquals(4, test.getExercises().get(0).getSeries()),
                () -> assertEquals(12, test.getExercises().get(0).getRepes()));
    }

    @Test
    void setNExercises() {
        Training test = new Training("Test", 0);
        test.setNExercises(1);
        test.setNExercises(0);
        assertEquals(0, test.getNExercises());
    }

    @Test
    void addExerciseRead() {
        Training test = new Training("Test", 0);
        Exercise ex = new Exercise(0, "Aperturas", 1, 0, "Pectoral", "Pectoral", "https://www.youtube.com/watch?v=xyHdY99F640");
        ex.setWeight(12.5);
        ex.setSeries(4);
        ex.setRepes(12);
        test.addExerciseRead(ex);
        assertAll( () -> assertEquals(0, test.getExercises().get(0).getId()),
                () -> assertEquals(12.5, test.getExercises().get(0).getWeight()),
                () -> assertEquals(4, test.getExercises().get(0).getSeries()),
                () -> assertEquals(12, test.getExercises().get(0).getRepes()));
    }

    @Test
    void setId() {
        Training test = new Training("Test", 0);
        test.setId(0);
        assertEquals(0, test.getId());
    }

    @Test
    void changeName() {
        Training test = new Training("Test", 0);
        test.changeName("Nuevo nombre");
        assertEquals("Nuevo nombre", test.getName());
    }

    @Test
    void changeDate() {
        Training test = new Training("Test", 0);
        test.changeDate(2022, 6, 17);
        assertEquals("17-6-2022", test.getPrintDate());
    }
}