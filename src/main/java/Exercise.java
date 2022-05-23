public class Exercise {

    // Private attributes
    private int m_id;
    // Name of the exercise
    private String m_name;
    // Represents the location where the training will take place
    private int m_location; //0 home, 1 gym, 2 both
    // Will be used to divide the exercises in to big parts of the body
    private int m_bodyPart1; // 0 top, 1 bottom
    // Will be used to represent the part of the body
    private String m_bodyPart2; // braç, trapezi, esquena, pectoral, abdomen, camesbraç, trapezi, esquena, pectoral, abdomen, cames
    // Will be used to represent the specific muscle.
    private String m_bodyPart3; // bíceps, tríceps, espatlla, dorsal, esquena mitjana, lumbar, glutis, quàdriceps, bíceps femoral, bessons
    private String m_exampleLink;
    private double m_weight;
    private int m_series;
    private int m_repes;

    public Exercise(int id, String name, int location, int bodyPart1, String bodyPart2, String bodyPart3, String exampleLink){
        this.m_id = id;
        this.m_name = name;
        this.m_location = location;
        this.m_bodyPart1 = bodyPart1;
        this.m_bodyPart2 = bodyPart2;
        this.m_bodyPart3 = bodyPart3;
        this.m_exampleLink = exampleLink;
    }

    public int getId(){
        return this.m_id;
    }

    public String getName(){
        return this.m_name;
    }

    public int getLocation(){
        return this.m_location;
    }

    public int getBodyPart1(){
        return this.m_bodyPart1;
    }

    public String getBodyPart2(){
        return this.m_bodyPart2;
    }

    public String getBodyPart3(){
        return this.m_bodyPart3;
    }

    public String getLink() {
        return this.m_exampleLink;
    }

    public double getWeight() {
        return this.m_weight;
    }

    public int getSeries() {
        return this.m_series;
    }

    public int getRepes() {
        return this.m_repes;
    }

    // Quizás borrar setters
    public void setId(int id){
        this.m_id = id;
    }

    public void setName(String name) {
        this.m_name = name;
    }

    public void setLocation(int location){
        this.m_location = location;
    }

    public void setBodyPart1(int bodyPart1) {
        this.m_bodyPart1 = bodyPart1;
    }

    public void setBodyPart2(String bodyPart2){
        this.m_bodyPart2 = bodyPart2;
    }

    public void setBodyPart3(String bodyPart3){
        this.m_bodyPart3 = bodyPart3;
    }

    public void setLink(String exampleLink){
        this.m_exampleLink = exampleLink;
    }

    public void setWeight(int weight){
        this.m_weight = weight;
    }

    public void setSeries(int series){
        this.m_series = series;
    }

    public void setRepes(int repes){
        this.m_repes = repes;
    }

    public void addDetails(double weight, int series, int repes) {
        this.m_weight = weight;
        this.m_series = series;
        this.m_repes = repes;
    }

    public void showExercise(){
        System.out.println("Id: " + this.getId());
        System.out.println("Name: " + this.getName());
        System.out.println("Location: " + this.getLocation());
        System.out.println("BodyPart1: " + this.getBodyPart1());
        System.out.println("BodyPart2: " + this.getBodyPart2());
        System.out.println("BodyPart3: " +  this.getBodyPart3());
        System.out.println("Link: " + this.getLink());
        System.out.println("Weight: " + this.getWeight());
        System.out.println("Series: " + this.getSeries());
        System.out.println("Repes: " + this.getRepes());
        System.out.println();
    }
}
