import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.cloud.firestore.*;
import com.google.cloud.firestore.v1.*;
import com.google.firebase.*;
import com.google.firebase.auth.*;
import org.json.JSONException;
import org.json.simple.parser.ParseException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) throws JSONException, IOException, ParseException, InterruptedException, ExecutionException {
        //filterTest();
        //trainingsTest();
        //editTrainingTest();
        //creatorTest();
        //dateTest();
        //jsonRawTest();
        //manyTrainingsTest();
        //exceptionTest();
        //jsonExerciseRawTest();
        //spaceTest();
        //decimalTest();
        //getTrainingRawTest();
        //dateFormatTest();
        postTest();
    }

    // This test is used to see if the filter method works properly
    public static void filterTest() throws IOException, ParseException {
        Catalog c = new Catalog();
        List<Exercise> filter = c.checkFilter("Bíceps", c.getExerciseList());
        c.showFilterList(filter);
        System.out.println("Number of exercises: " + filter.size());
    }

    // This test is used to check the if the training are created properly
    public static void trainingsTest() throws IOException, ParseException {
        Catalog c = new Catalog();
        CreateFile cf = new CreateFile();
        IdGenerator ig = new IdGenerator();
        // Create new Trainings and set their id's
        Training t = new Training("Entrenamiento 1", 0);
        t.setId(ig.generateId());
        Training t2 = new Training("Entreno 2", 1);
        t2.setId(ig.generateId());
        t.changeDate(2022, 4, 26);
        t2.changeDate(2022, 4, 27);
        // Add 3 exercises to training t
        for (int i=0; i<3; i++){
            t.addExercise(c.getExercise(i));
        }

        // Add 4 exercises to training t2
        for (int i=3; i<7; i++){
            t2.addExercise(c.getExercise(i));
        }

        // Add details to some exercises
        t.addDetailsExercise(0, 200, 4, 8);
        t2.addDetailsExercise(4, 210, 4, 8);

        // Write both trainings into the JSON
        cf.addTraining(t);
        cf.addTraining(t2);

        // Check if the method showTrainings() is commented on the constructor
        ReadFile rf  = new ReadFile();
    }

    // This test is used to edit a training by changing his name and see the changes in the terminal
    // and also in the JSON file.
    public static void editTrainingTest() throws IOException, ParseException {
        Catalog c = new Catalog();
        CreateFile cf = new CreateFile();
        IdGenerator ig = new IdGenerator();
        // Create new Trainings and set their id's
        Training t = new Training("Entrenamiento 1", 0);
        t.setId(ig.generateId());
        Training t2 = new Training("Entreno 2", 1);
        t2.setId(ig.generateId());

        // Add 3 exercises to training t
        for (int i=0; i<3; i++){
            t.addExercise(c.getExercise(i));
        }

        // Add 4 exercises to training t2
        for (int i=3; i<7; i++){
            t2.addExercise(c.getExercise(i));
        }

        // Add details to some exercises
        t.addDetailsExercise(0, 200, 4, 8);
        t2.addDetailsExercise(4, 210, 4, 8);

        // Write both trainings into the JSON
        cf.addTraining(t);
        cf.addTraining(t2);

        // Check if the method showTrainings() is commented on the constructor
        ReadFile rf  = new ReadFile();

        // Edit trainings
        System.out.println("-----------------------------");
        rf.editTraining(0, "Entreno Lunes", null);
        cf = new CreateFile();
        for (Training tr : rf.getM_listTrainings()){
            cf.addTraining(tr);
        }
        rf = new ReadFile();
    }

    public static void creatorTest() throws IOException, ParseException {
        Creator c = new Creator();
        System.out.println("---------------------");
        c.createTraining(0, 1, 2).showDetails();
    }

    public static void dateTest() throws IOException, ParseException {
        Catalog c = new Catalog();
        CreateFile cf = new CreateFile();
        IdGenerator ig = new IdGenerator();
        Training t = new Training("Entrenamiento 1", 0);
        t.setId(ig.generateId());
        t.changeDate(2022, 4, 26);
        // Add 1 exercises to training t
        t.addExercise(c.getExercise(0));
        cf.addTraining(t);
        ReadFile rf  = new ReadFile();
    }

    public static void jsonRawTest() throws IOException, ParseException {
        Creator c = new Creator();
        CreateFile cf = new CreateFile();
        cf.addTraining(c.createTraining(0, 1, 0));
        ReadFile rf = new ReadFile();
        String text = rf.getJSONRaw();
        System.out.println(text);
    }

    public static void manyTrainingsTest() throws IOException, ParseException {
        Catalog c = new Catalog();
        CreateFile cf = new CreateFile();
        IdGenerator ig = new IdGenerator();
        // Create new Trainings and set their id's
        Training t = new Training("Entrenamiento 1", 0);
        t.setId(ig.generateId());
        Training t2 = new Training("Entreno 2", 1);
        t2.setId(ig.generateId());
        Training t3 = new Training("E3", 0);
        t3.setId(ig.generateId());
        Training t4 = new Training("Pierna", 1);
        t4.setId(ig.generateId());
        Training t5 = new Training("Pecho tricpes", 0);
        t5.setId(ig.generateId());
        Training t6 = new Training("Muerte", 1);
        t6.setId(ig.generateId());
        Training t7 = new Training("Cardio", 0);
        t7.setId(ig.generateId());
        Training t8 = new Training("Entreno10", 1);
        t8.setId(ig.generateId());


        t.changeDate(2022, 4, 26);
        t2.changeDate(2022, 4, 27);
        t3.changeDate(2022, 4, 28);
        t4.changeDate(2022, 4, 29);
        t5.changeDate(2022, 4, 30);
        t6.changeDate(2022, 5, 1);
        t7.changeDate(2022, 5, 2);
        t8.changeDate(2022, 5, 3);
        // Add 3 exercises to training t
        for (int i=0; i<3; i++){
            t.addExercise(c.getExercise(i));
            t3.addExercise(c.getExercise(i));
            t5.addExercise(c.getExercise(i));
            t7.addExercise(c.getExercise(i));
        }

        // Add 4 exercises to training t2
        for (int i=3; i<7; i++){
            t2.addExercise(c.getExercise(i));
            t4.addExercise(c.getExercise(i));
            t6.addExercise(c.getExercise(i));
            t8.addExercise(c.getExercise(i));
        }

        // Add details to some exercises
        t.addDetailsExercise(0, 200, 4, 8);
        t2.addDetailsExercise(4, 210, 4, 8);

        // Write both trainings into the JSON
        cf.addTraining(t);
        cf.addTraining(t2);
        cf.addTraining(t3);
        cf.addTraining(t4);
        cf.addTraining(t5);
        cf.addTraining(t6);
        cf.addTraining(t7);
        cf.addTraining(t8);

        // Check if the method showTrainings() is commented on the constructor
        ReadFile rf  = new ReadFile();
    }

    public static void exceptionTest() throws IOException, ParseException {
        CreateFile cf = new CreateFile();
        Training t = new Training("New", 0);
        t.changeDate(2002, 04, 30);
        cf.addTraining(t);
        System.out.println("0");
        ReadFile rf = new ReadFile();
        cf.addTraining(t);
        System.out.println("1");
        rf = new ReadFile();
        cf.addTraining(t);
        System.out.println("3");
        rf = new ReadFile();
        cf.addTraining(t);
        System.out.println("4");
        rf = new ReadFile();
        cf.addTraining(t);
        System.out.println("4");
        rf = new ReadFile();
        cf.addTraining(t);
        System.out.println("5");
        rf = new ReadFile();
        cf.addTraining(t);
        System.out.println("6");
        rf = new ReadFile();
        cf.addTraining(t);
        System.out.println("7");
        rf = new ReadFile();
    }

    public static void jsonExerciseRawTest() throws IOException, ParseException {
        ReadFile rf = new ReadFile();
        System.out.println(rf.getExercisesJSONRaw());
    }

    public static void spaceTest(){
        String name = "Nuevo%20entrenamiento";
        String res = "";
        if(name.contains("%20")) {
            res = name.replace("%20", " ");
        }

        System.out.println(res);

    }

    public static void decimalTest() throws IOException, ParseException {
        CreateFile cf = new CreateFile();
        ReadFile rf = new ReadFile();
        Catalog c = new Catalog();
        Training t = new Training("decimalTest", 1);
        t.addExercise(c.getExercise(0));
        t.getExercises().get(0).addDetails(14.5, 4, 12);
        cf.addTraining(t);
        System.out.println(rf.getJSONRaw());

        cf.refresh();
        Training t2 = new Training("test2", 1);
        t2.addExercise(c.getExercise(0));
        t2.getExercises().get(0).addDetails(17.5, 4, 12);
        cf.addTraining(t2);
        System.out.println(rf.getJSONRaw());
    }

    public static void getTrainingRawTest() throws IOException, ParseException, InterruptedException {
        CreateFile cf = new CreateFile();
        ReadFile rf = new ReadFile();
        Catalog c = new Catalog();
        Training t = new Training("test", 1);
        t.changeDate(2022, 05, 22);
        for (int i = 0; i<2; i++){
            t.addExercise(c.getExerciseList().get(i));
        }
        /////////////////
        cf.createTrainingFile();
        cf.addTraining2(t);
        System.out.println(rf.getTrainingRaw());
        Files.deleteIfExists(Paths.get("./src/training_help.json"));
    }

    public static void dateFormatTest(){
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HHmmssSS");
        String formattedDate = myDateObj.format(myFormatObj);
        System.out.println(formattedDate);
    }

    public static void postTest() throws IOException, ParseException, ExecutionException, InterruptedException {
        // training
        CreateFile cf = new CreateFile();
        ReadFile rf = new ReadFile();
        Catalog c = new Catalog();
        Training t = new Training("test", 1);
        t.changeDate(2022, 05, 22);
        for (int i = 0; i<2; i++){
            t.addExercise(c.getExerciseList().get(i));
        }
        /////////////////
        cf.createTrainingFile();
        cf.addTraining2(t);
        String training = rf.getTrainingRaw();
        Files.deleteIfExists(Paths.get("./src/training_help.json"));
        Firebase fb = new Firebase();

        // id
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HHmmssSS");
        String formattedDate = myDateObj.format(myFormatObj);

        fb.addTraining(formattedDate, training);
    }
}
