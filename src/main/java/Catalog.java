import java.util.List;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


// This class is used for reading the exercises.json and is where we get the exercise from
public class Catalog {

    private final List<Exercise> m_listExercises;

    public Catalog() throws IOException, ParseException {
        this.m_listExercises = new LinkedList<>();
        this.readExercises();
        // this.showExercises();
    }

    public void readExercises() throws  IOException, ParseException {
        // Reads the data if possible
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("./src/main/java/exercises.json")){
            Object obj = jsonParser.parse(reader);
            JSONObject eobj = (JSONObject) obj;
            JSONArray trainingsList = (JSONArray) eobj.get("exercises");
            trainingsList.forEach( e -> createExercises( (JSONObject) e ) );
        }
    }

    public void createExercises(JSONObject exercise) {
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
        Exercise e = new Exercise(id, name, location, bodyPart1, bodyPart2, bodyPart3, link);
        this.m_listExercises.add(e);
    }

    public Exercise getExercise(int id){
        if ((id >= 0) && (id <=this.m_listExercises.size())){
            for (Exercise e : this.m_listExercises) {
                if (e.getId() == id){
                    return e;
                }
            }
        }
        return null;
    }

    public List<Exercise> checkFilter(String f, List<Exercise> list){
        // if is an integer we are looking for bodyPart1
        if ((Objects.equals(f, "Parte superior")) ||  (Objects.equals(f, "Arriba")) || (Objects.equals(f, "Parte de arriba"))){
            return filterPart1(0, list);
        } // bodyPart2 or bodyPart3
        else{
            if ((Objects.equals(f, "Parte inferior")) ||  (Objects.equals(f, "Abajo")) || (Objects.equals(f, "Parte de abajo"))){
                return filterPart1(1, list);
            }
            else{
                if ((Objects.equals(f, "Brazo")) ||  (Objects.equals(f, "Trapezio")) || (Objects.equals(f, "Espalda")) || (Objects.equals(f, "Pectoral")) || (Objects.equals(f, "Abdomen")) || (Objects.equals(f, "Pierna"))){
                    return filterPart2(f, list);
                }
                else{
                    return filterPart3(f, list);
                }
            }
        }
    }

    public List<Exercise> filterPart1(int filter, List<Exercise> list){
        List<Exercise> filterList = new LinkedList<>();
        for (Exercise e : list){
            if (e.getBodyPart1() == filter) {
                    filterList.add(e);
            }
        }
        return filterList;
    }

    public List<Exercise> filterPart2(String filter, List<Exercise> list){
        List<Exercise> filterList = new LinkedList<>();
        for (Exercise e : list){
            if (Objects.equals(e.getBodyPart2(), filter)) {
                filterList.add(e);
            }
        }
        return filterList;
    }

    public List<Exercise> filterPart3(String filter, List<Exercise> list){
        List<Exercise> filterList = new LinkedList<>();
        for (Exercise e : list){
            if (Objects.equals(e.getBodyPart3(), filter)) {
                filterList.add(e);
            }
        }
        return filterList;
    }

    public List<Exercise> filerLocation(int location) {
        List<Exercise> filter = new LinkedList<>();
        if (location == 1){
            for (Exercise e : this.m_listExercises){
                if (e.getLocation() == location) {
                    filter.add(e);
                }
            }
        }
        else {
            filter.addAll(this.m_listExercises);
        }
        return filter;
    }

    public void showFilterList(List<Exercise> list){
        for (Exercise e : list){
            e.showExercise();
        }
    }

    public List<Exercise> getExerciseList(){
        return this.m_listExercises;
    }

    Exercise findExercise(int id){
        for (Exercise ex : this.m_listExercises){
            if (ex.getId() == id){
                return ex;
            }
        }
        return null;
    }

    public void showExercises(){
        for (Exercise m_listExercise : this.m_listExercises) {
            m_listExercise.showExercise();
        }
    }

}
