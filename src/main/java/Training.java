import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class Training {

    // Private attributes
    private int m_id;
    // Name of the training
    private String m_name;
    // Type of the training 0 hypertrophy 1 strength
    private final int m_type;
    // Number of exercises in the training
    private int m_nExercises;
    // List of exercises
    private final List<Exercise> m_exercises;
    private IdGenerator generator;
    // The date set to this training // Check the type
    private Calendar m_date;
    private String m_printDate;

    public Training(String name, int type){
        this.m_name = name;
        // 0 strength 1 hypertrophy
        this.m_type = type;
        this.m_exercises = new LinkedList<>();
        this.m_date = Calendar.getInstance();
    }


    public String getName(){
        return this.m_name;
    }

    public int getNExercises(){
        return this.m_nExercises;
    }

    public List<Exercise> getExercises(){
        return this.m_exercises;
    }

    public void showDetails(){
        System.out.println("Training id: " + this.m_id);
        System.out.println("Training name: " + this.m_name);
        System.out.println("Training date: " + this.m_printDate);
        System.out.println("Training type: " + this.m_type);
        System.out.println("nExercises: " + this.m_nExercises);
        System.out.println("Exercises: ");
        System.out.println("");
        if(!this.m_exercises.isEmpty()){
            for(Exercise e:this.m_exercises){
                e.showExercise();
            }
        }
    }

    public void addDetailsExercise(int id, int weight, int series, int repes) {
        for (Exercise ex : this.m_exercises){
            if (ex.getId() == id) {
                ex.setWeight(weight);
                ex.setSeries(series);
                ex.setRepes(repes);
            }
        }
    }

    public void setNExercises(int n){
        this.m_nExercises = n;
    }

    // To add an exercise to the training
    public void addExercise(Exercise e){
        Exercise ex = new Exercise(e.getId(), e.getName(), e.getLocation(), e.getBodyPart1(), e.getBodyPart2(), e.getBodyPart3(), e.getLink());
        ex.addDetails(e.getWeight(), e.getSeries(), e.getRepes());
        this.m_exercises.add(ex);
        this.m_nExercises += 1;
    }

    public void addExerciseRead(Exercise e){
        Exercise ex = new Exercise(e.getId(), e.getName(), e.getLocation(), e.getBodyPart1(), e.getBodyPart2(), e.getBodyPart3(), e.getLink());
        ex.addDetails(e.getWeight(), e.getSeries(), e.getRepes());
        this.m_exercises.add(ex);
    }

    public int getType(){
        return this.m_type;
    }

    public int getId(){
        return this.m_id;
    }

    public void setId(int id){
        this.m_id = id;
    }

    public String getPrintDate() {return this.m_printDate;}

    // Method to change the name of the training
    public void changeName(String name){
        this.m_name = name;
    }

    // Method to change the date set to a training
    public void changeDate(int year, int month, int day){
        this.m_date.set(Calendar.YEAR, year);
        this.m_date.set(Calendar.MONTH, month);
        this.m_date.set(Calendar.DATE, day);
        convertDate();
    }

    public void convertDate(){
        int day = this.m_date.get(Calendar.DATE);
        int month = this.m_date.get(Calendar.MONTH);
        int year = this.m_date.get(Calendar.YEAR);
        // System.out.println("Day: " + day + " Month: " + month + " Year: " + year);
        this.m_printDate = Integer.toString(day) + "-" + Integer.toString(month) + "-" + Integer.toString(year);
        // System.out.println(m_printDate);
    }

    public void readDate(String date) {
        String[] parts = date.split("-");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);
        changeDate(year, month, day);
    }
}
