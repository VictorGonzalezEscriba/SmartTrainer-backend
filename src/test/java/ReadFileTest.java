import com.google.api.services.storage.Storage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadFileTest {

    @Test
    void readTrainings() {
        // For this test you must delete the trainings.json file
        CreateFile cf = new CreateFile();
        Training test = new Training("Test", 0);
        test.changeDate(2022, 6, 17);
        Exercise ex = new Exercise(0, "Aperturas", 1, 0, "Pectoral", "Pectoral", "https://www.youtube.com/watch?v=xyHdY99F640");
        test.addExercise(ex);
        cf.addTraining(test);

        ReadFile rf = new ReadFile();
        assertAll(
                () -> assertEquals(1, rf.getM_listTrainings().size()),
                () -> assertEquals(0, rf.getM_listTrainings().get(0).getId()),
                () -> assertEquals("Test", rf.getM_listTrainings().get(0).getName()),
                () -> assertEquals(0, rf.getM_listTrainings().get(0).getExercises().get(0).getId()),
                () -> assertEquals("Aperturas", rf.getM_listTrainings().get(0).getExercises().get(0).getName())
        );
    }

    @Test
    void getTrainingRaw() {

    }

    @Test
    void getJSONRaw() {
        ReadFile rf = new ReadFile();
        // change this string with the content of training.json test and add an extra \n at the end
        String test = "{\n" +
                "  \"trainings\" : [ {\n" +
                "    \"date\" : \"17-6-2022\",\n" +
                "    \"nExercises\" : 1,\n" +
                "    \"exercises\" : [ {\n" +
                "      \"series\" : 0,\n" +
                "      \"name\" : \"Aperturas\",\n" +
                "      \"link\" : \"https://www.youtube.com/watch?v=xyHdY99F640\",\n" +
                "      \"weight\" : 0,\n" +
                "      \"location\" : 1,\n" +
                "      \"id\" : 0,\n" +
                "      \"repes\" : 0,\n" +
                "      \"bodyPart3\" : \"Pectoral\",\n" +
                "      \"bodyPart2\" : \"Pectoral\",\n" +
                "      \"bodyPart1\" : 0\n" +
                "    } ],\n" +
                "    \"tName\" : \"Test\",\n" +
                "    \"type\" : 0,\n" +
                "    \"tId\" : 0\n" +
                "  } ]\n" +
                "}\n"; // add here the extra \n

        assertEquals(test, rf.getJSONRaw());
    }
}