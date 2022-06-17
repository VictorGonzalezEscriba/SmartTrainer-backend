import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.io.FileReader;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ReadFile {
    private final List<Training> m_listTrainings;

    public ReadFile() {
        this.m_listTrainings = new LinkedList<>();
        this.readTrainings();
        // this.showTrainings();
    }

    public void showTrainings(){
        for(Training t : this.m_listTrainings){
            t.showDetails();
        }
    }

    public void readTrainings() {
        try {
            JSONParser jsonParser = new JSONParser();
            try (FileReader reader = new FileReader("./src/main/java/trainings.json")){
                Object obj = jsonParser.parse(reader);
                JSONObject tobj = (JSONObject) obj;
                JSONArray trainingsList = (JSONArray) tobj.get("trainings");
                trainingsList.forEach( t -> createTrainings( (JSONObject) t ) );
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e2){
            e2.printStackTrace();
        }
    }

    public String getTrainingRaw() throws IOException {
        String data = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("./src/training_help.json"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public Training getImportedTraining() {
        try {
            JSONParser jsonParser = new JSONParser();
            try (FileReader reader = new FileReader("./src/main/java/training_help.json")){
                Object obj = jsonParser.parse(reader);
                JSONObject tobj = (JSONObject) obj;
                return createTrainings2(tobj);
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e2){
            e2.printStackTrace();
        }
        return null;
    }

    public String getJSONRaw() {
        String data = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("./src/main/java/trainings.json"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            data = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public String getExercisesJSONRaw() {
        String data = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader("./src/main/java/exercises.json"));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            data = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public Training createTrainings2(JSONObject training) {
        String trainingName = (String) training.get("tName");
        Long idL = (Long) training.get("tId");
        int id = idL.intValue();
        Long i = (Long) training.get("nExercises");
        int nExercises = i.intValue();
        Long typeL = (Long) training.get("type");
        int type = typeL.intValue();
        Training tr = new Training(trainingName, type);
        tr.setNExercises(nExercises);
        tr.setId(id);
        String date = (String) training.get("date");
        tr.readDate(date);
        JSONArray exercisesList = (JSONArray) training.get("exercises");
        exercisesList.forEach(ex -> createExercise( (JSONObject) ex,  tr));
        return tr;
    }

    public void createTrainings(JSONObject training) {
        String trainingName = (String) training.get("tName");
        Long idL = (Long) training.get("tId");
        int id = idL.intValue();
        Long i = (Long) training.get("nExercises");
        int nExercises = i.intValue();
        Long typeL = (Long) training.get("type");
        int type = typeL.intValue();
        Training tr = new Training(trainingName, type);
        tr.setNExercises(nExercises);
        tr.setId(id);
        String date = (String) training.get("date");
        tr.readDate(date);
        JSONArray exercisesList = (JSONArray) training.get("exercises");
        exercisesList.forEach(ex -> createExercise( (JSONObject) ex,  tr));
        this.m_listTrainings.add(tr);
    }

    public void createExercise(JSONObject exercise, Training t){
        Long idL = (Long) exercise.get("id");
        int id = idL.intValue();
        String name = (String) exercise.get("name");
        Long locationT = (Long) exercise.get("location");
        int location = locationT.intValue();
        Long bodyPart1T = (Long) exercise.get("bodyPart1");
        int bodyPart1 = bodyPart1T.intValue();
        String bodyPart2 = (String) exercise.get("bodyPart2");
        String bodyPart3 = (String) exercise.get("bodyPart3");
        String link = (String) exercise.get("link");
        Object weightT = exercise.get("weight");
        double weight;
        if (weightT instanceof Long) {
            weight = ((Long) weightT).doubleValue();
        }
        else {
            weight = (double) weightT;
        }

        // double weight = weightT.doubleValue();
        Long seriesT = (Long) exercise.get("series");
        int series = seriesT.intValue();
        Long repesT = (Long) exercise.get("repes");
        int repes = repesT.intValue();
        Exercise e = new Exercise(id, name, location, bodyPart1, bodyPart2, bodyPart3, link);
        e.addDetails(weight, series, repes);
        t.addExerciseRead(e);
    }

    public void editTraining(int id, String name, Calendar date){
        for (Training t : this.m_listTrainings){
            if(t.getId() == id){
                if (!Objects.equals(name, "")) {
                    t.changeName(name);
                }
                if (date != null) {
                    t.changeDate(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DATE));
                }
            }
        }
    }

    public List<Training> getM_listTrainings(){
        return this.m_listTrainings;
    }

}