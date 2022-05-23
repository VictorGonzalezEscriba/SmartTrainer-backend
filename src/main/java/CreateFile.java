import java.io.FileWriter;
import java.io.*;
import java.io.IOException;
import org.json.*;
import com.fasterxml.jackson.databind.ObjectMapper;


// This class is used for reading the trainings.json file
public class CreateFile {
    private final String m_filePath = "./src/main/java/trainings.json";
    ObjectMapper m_mapper = new ObjectMapper();
    JSONObject m_object = new JSONObject();
    JSONObject m_object2 = new JSONObject();
    // Array that contains all the trainings
    JSONArray m_trainings = new JSONArray();


    public CreateFile() throws JSONException {
        // To create the file
        try {
            File f = new File(this.m_filePath);
            if (f.createNewFile()){
                FileWriter file = new FileWriter(f);
                this.m_object.put("trainings", m_trainings);
                file.write(this.m_mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.m_mapper.readTree(this.m_object.toString())));
                file.flush();
                file.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createTrainingFile(){
        try {
            File f = new File("src/training_help.json");
            FileWriter file = new FileWriter(f);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTraining2(Training t) throws JSONException{
        JSONObject newTraining = new JSONObject();
        newTraining.put("tName", t.getName());
        newTraining.put("type", t.getType());
        newTraining.put("nExercises", t.getNExercises());
        newTraining.put("tId", t.getId());
        newTraining.put("date", t.getPrintDate());
        JSONArray exercises = new JSONArray();
        for (Exercise e : t.getExercises()){
            JSONObject ex = new JSONObject();
            ex.put("id", e.getId());
            ex.put("name", e.getName());
            ex.put("weight", e.getWeight());
            ex.put("series", e.getSeries());
            ex.put("repes", e.getRepes());
            ex.put("location", e.getLocation());
            ex.put("bodyPart1", e.getBodyPart1());
            ex.put("bodyPart2", e.getBodyPart2());
            ex.put("bodyPart3", e.getBodyPart3());
            ex.put("link", e.getLink());
            exercises.put(ex);
        }
        newTraining.put("exercises", exercises);

        //Write JSON file
        try {
            FileWriter file = new FileWriter("src/training_help.json", false);
            //We can write any JSONArray or JSONObject instance to the file
            file.write(this.m_mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.m_mapper.readTree(newTraining.toString())));
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addTraining(Training t) throws JSONException{
        JSONObject newTraining = new JSONObject();
        newTraining.put("tName", t.getName());
        newTraining.put("type", t.getType());
        newTraining.put("nExercises", t.getNExercises());
        newTraining.put("tId", t.getId());
        newTraining.put("date", t.getPrintDate());
        JSONArray exercises = new JSONArray();
        for (Exercise e : t.getExercises()){
            JSONObject ex = new JSONObject();
            ex.put("id", e.getId());
            ex.put("name", e.getName());
            ex.put("weight", e.getWeight());
            ex.put("series", e.getSeries());
            ex.put("repes", e.getRepes());
            ex.put("location", e.getLocation());
            ex.put("bodyPart1", e.getBodyPart1());
            ex.put("bodyPart2", e.getBodyPart2());
            ex.put("bodyPart3", e.getBodyPart3());
            ex.put("link", e.getLink());
            exercises.put(ex);
        }
        newTraining.put("exercises", exercises);
        this.m_trainings.put(newTraining);

        //Write JSON file
        try {
            FileWriter file = new FileWriter(this.m_filePath, false);
            //We can write any JSONArray or JSONObject instance to the file
            file.write(this.m_mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.m_mapper.readTree(this.m_object.toString())));
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refresh() throws JSONException {
        this.m_object = new JSONObject();
        // Array that contains all the trainings
        this.m_trainings = new JSONArray();
        this.m_object.put("trainings", m_trainings);
    }

    public void writeEmpty() throws JSONException {
        //Write JSON file
        try {
            FileWriter file = new FileWriter(this.m_filePath, false);
            //We can write any JSONArray or JSONObject instance to the file
            file.write(this.m_mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this.m_mapper.readTree(this.m_object.toString())));
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
