import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.dynalink.StandardNamespace;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.net.ServerSocket;
import java.net.URLDecoder;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

// Based on
// https://www.ssaurel.com/blog/create-a-simple-http-web-server-in-java
// http://www.jcgonzalez.com/java-socket-mini-server-http-example

public class WebServer {
    private static final int PORT = 8080; // port to listen to
    Creator cr= new Creator();
    CreateFile cf = new CreateFile();
    ReadFile rf = new ReadFile();
    ObjectMapper m_mapper = new ObjectMapper();

    public WebServer() throws IOException, ParseException {

        try {
            ServerSocket serverConnect = new ServerSocket(PORT);
            System.out.println("Server started.\nListening for connections on port : " + PORT + " ...\n");
            // we listen until user halts server execution
            while (true) {
                // each client connection will be managed in a dedicated Thread
                new SocketThread(serverConnect.accept());
                // create dedicated thread to manage the client connection
            }
        } catch (IOException e) {
            System.err.println("Server Connection error : " + e.getMessage());
        }
    }

    // aqui las funciones que necesites usar luego, de buscar en las estructuras de datos i tal.

    private class SocketThread extends Thread {
        // SocketThread sees WebServer attributes
        private final Socket insocked;
        // Client Connection via Socket Class

        SocketThread(Socket insocket) {
            this.insocked = insocket;
            this.start();
        }

        public static String decodeValue(String string){
            try{
                return URLDecoder.decode(string, StandardCharsets.UTF_8.toString());
            }catch(UnsupportedEncodingException ex) {
                throw new RuntimeException(ex.getCause());
            }
        }

        @Override
        public void run() {
            // we manage our particular client connection
            BufferedReader in;
            PrintWriter out;
            String resource;

            try {
                // we read characters from the client via input stream on the socket
                in = new BufferedReader(new InputStreamReader(insocked.getInputStream(), StandardCharsets.UTF_8));
                // we get character output stream to client
                out = new PrintWriter(insocked.getOutputStream());
                // get first line of the request from the client
                String input = in.readLine();
                // we parse the request with a string tokenizer

                System.out.println("sockedthread : " + input);

                StringTokenizer parse = new StringTokenizer(input);
                String method = parse.nextToken().toUpperCase();
                // we get the HTTP method of the client
                if (!method.equals("GET")) {
                    System.out.println("501 Not Implemented : " + method + " method.");
                } else {
                    // what comes after "localhost:8080"
                    resource = parse.nextToken();
                    System.out.println("input " + input);
                    System.out.println("method " + method);
                    System.out.println("resource " + resource);
                    String[] tokens = new String[20];
                    if (resource.startsWith("/import")){
                        // extract the  query
                        String url = resource.replace("/import?", "");
                        String finalString = decodeValue(url);
                        System.out.println("//////////////////////////////////////////////////");
                        System.out.println("Final String: " + finalString);

                        tokens[0] = "import";
                        tokens[1] = finalString;
                    }
                    else{
                        parse = new StringTokenizer(resource, "/[?]=&");
                        int i = 0;
                        // more than the actual number of parameters
                        while (parse.hasMoreTokens()) {
                            tokens[i] = parse.nextToken();
                            System.out.println("token " + i + "=" + tokens[i]);
                            i++;
                        }
                    }
                    // Make the answer as a JSON string, to be sent to the Javascript client
                    String answer = makeHeaderAnswer() + makeBodyAnswer(tokens);
                    System.out.println("answer\n" + answer);
                    // Here we send the response to the client
                    out.println(answer);
                    out.flush(); // flush character output stream buffer
                }

                in.close();
                out.close();
                insocked.close(); // we close socket connection
            } catch (Exception e) {
                System.err.println("Exception : " + e);
            }
        }


        private String makeBodyAnswer(String[] tokens) throws IOException, ParseException {
            String body = "";
            ObjectMapper m_mapper = new ObjectMapper();
            // aqui la array tokens tiene en formato string los parametros de la solicitud del front-end
            // token[0] tiene el nombre de la peticion
            switch (tokens[0]) {
                case "get_trainings": {
                    rf = new ReadFile();
                    body = rf.getJSONRaw();
                    break;
                }
                case "generate_training":{
                    cf.refresh();
                    IdGenerator idGenerator = new IdGenerator();
                    for (Training t : rf.getM_listTrainings()){
                        cf.addTraining(t);
                        t.setId(idGenerator.generateId());
                    }
                    System.out.println("Creando entrenamiento");
                    Training t = cr.createTraining(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]));
                    t.setId(idGenerator.generateId());
                    cf.addTraining(t);
                    break;
                }
                case "get_exercises":{
                    body = rf.getExercisesJSONRaw();
                    break;
                }
                case "create_training": {
                    cf.refresh();
                    IdGenerator idGenerator = new IdGenerator();
                    for (Training t : rf.getM_listTrainings()){
                        t.setId(idGenerator.generateId());
                        cf.addTraining(t);
                    }

                    // to set the name properly
                    String name = correctName(tokens[1]);

                    Training t = new Training(name, 3);
                    t.readDate(tokens[2]);
                    t.setId(idGenerator.generateId());
                    List<Exercise> exercises = fillExercises(tokens[3]);
                    for (Exercise e : exercises){
                        t.addExercise(e);
                    }
                    cf.addTraining(t);
                    // t.showDetails();
                    break;
                }

                case "edit_exercise":{
                    int trainingId = Integer.parseInt(tokens[1]);
                    int exerciseId = Integer.parseInt(tokens[2]);
                    double weight = Double.parseDouble(tokens[3]);
                    int series = Integer.parseInt(tokens[4]);
                    int repes = Integer.parseInt(tokens[5]);

                    cf.refresh();
                    for (Training t : rf.getM_listTrainings()){
                        if (t.getId() == trainingId){
                            for (Exercise e : t.getExercises()){
                                if (e.getId() == exerciseId){
                                    e.addDetails(weight, series, repes);
                                }
                            }
                        }
                        cf.addTraining(t);
                    }
                    break;
                }

                case "edit_training": {
                    int trainingId = Integer.parseInt(tokens[1]);
                    String newName = correctName(tokens[2]);
                    String newDate = tokens[3];
                    cf.refresh();
                    for (Training t : rf.getM_listTrainings()){
                        if (t.getId() == trainingId){
                            t.changeName(newName);
                            t.readDate(newDate);
                            }
                        cf.addTraining(t);
                    }
                    break;
                }

                case "delete_training": {
                    System.out.println("Entered delete_training");
                    int trainingId = Integer.parseInt(tokens[1]);
                    cf.refresh();
                    System.out.println(rf.getM_listTrainings().size());
                    for (Training t : rf.getM_listTrainings()){
                        System.out.println("Training id: " + t.getId());
                        if (t.getId() != trainingId){
                            System.out.println("Training added");
                            cf.addTraining(t);
                        } else {
                            if (rf.getM_listTrainings().size() == 1 && t.getId() == trainingId) {
                                cf.writeEmpty();
                            }
                        }
                    }
                    System.out.println(rf.getM_listTrainings().size());
                    break;
                }

                case "delete_exercise": {
                    int trainingId = Integer.parseInt(tokens[1]);
                    int exerciseId = Integer.parseInt(tokens[2]);
                    cf.refresh();
                    for (Training t : rf.getM_listTrainings()){
                        if (t.getId() != trainingId){
                            cf.addTraining(t);
                        } else {
                            for (Exercise e : t.getExercises()){
                                System.out.println("Deleting");
                                System.out.println(e.getWeight());
                                if (e.getId() == exerciseId){
                                    t.getExercises().remove(e);
                                    cf.addTraining(t);
                                }
                            }
                            cf.addTraining(t);
                        }
                    }
                    break;
                }

                case "add_exercises": {
                    int trainingId =  Integer.parseInt(tokens[1]);
                    // Que no este vacia
                    if (!Objects.equals(tokens[2], "-1")){
                        List<Exercise> exercises = fillExercises(tokens[2]);
                        List<Exercise> aux = new ArrayList<>();
                        cf.refresh();
                        for (Training t : rf.getM_listTrainings()){
                            if (t.getId() == trainingId){
                                // Añadir los que se mantienen
                                for (Exercise ex : t.getExercises()){
                                    for (Exercise ex2 : exercises){
                                        if (ex.getId() == ex2.getId()){
                                            aux.add(ex);
                                        }
                                    }
                                }
                                // Mirar los nuevos
                                for (Exercise ex : exercises){
                                    boolean isNew = true;
                                    for (Exercise ex2 : t.getExercises()){
                                        if (ex.getId() == ex2.getId()) {
                                            isNew = false;
                                            break;
                                        }
                                    }
                                    if (isNew){
                                        aux.add(ex);
                                    }
                                }
                                t.getExercises().clear();
                                t.setNExercises(0);
                                for (Exercise e : aux){
                                    t.addExercise(e);
                                }
                            }
                            cf.addTraining(t);
                        }
                    } else {
                        cf.refresh();
                        for (Training t : rf.getM_listTrainings()){
                            if (t.getId() == trainingId){
                                t.getExercises().clear();
                            }
                            cf.addTraining(t);
                        }

                    }

                    break;
                }

                case "export": {
                    // El entrenamiento en formato String para guardarlo en Firebase
                    String training = "";
                    // La fecha del momento en que se comparte para
                    LocalDateTime myDateObj = LocalDateTime.now();
                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HHmmssSS");
                    String formattedDate = myDateObj.format(myFormatObj);

                    int trainingId = Integer.parseInt(tokens[1]);
                    for (Training t : rf.getM_listTrainings()){
                        if (t.getId() == trainingId){
                            cf.createTrainingFile();
                            cf.addTraining2(t);
                            training = rf.getTrainingRaw();
                        }
                    }
                    if (!Objects.equals(training, "")){
                        body = formattedDate + "---" + training;
                        System.out.println(body);
                    }
                    else {
                        body = "";
                    }
                    break;
                }

                case "import": {
                    // Codigo que el usuario introducirá
                    String training = tokens[1];
                    try {
                        FileWriter file = new FileWriter("./src/main/java/training_help.json", false);
                        //We can write any JSONArray or JSONObject instance to the file
                        file.write(m_mapper.writerWithDefaultPrettyPrinter().writeValueAsString(m_mapper.readTree(training)));
                        file.flush();
                        // the imported training
                        rf.getImportedTraining();

                        cf.refresh();
                        IdGenerator idGenerator = new IdGenerator();
                        for (Training t : rf.getM_listTrainings()){
                            t.setId(idGenerator.generateId());
                            cf.addTraining(t);
                        }
                        Training importedTraining = rf.getImportedTraining();
                        importedTraining.setId(idGenerator.generateId());
                        cf.addTraining(importedTraining);
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                   break;
                }

                default:
                    System.out.println("Default");
                    assert false;
            }

            // System.out.println(body);
            return body;
        }

        private String makeHeaderAnswer() {
            String answer = "";
            answer += "HTTP/1.0 200 OK\r\n";
            answer += "Content-type: application/json\r\n";
            answer += "\r\n";
            // blank line between headers and content, very important !
            return answer;
        }
    } // SocketThread

    String correctName(String name){
        // to set the name properly
        String newName = "";
        if(name.contains("%20")) {
            newName = name.replace("%20", " ");
        }else
        {
            newName = name;
        }
        return newName;
    }

    List<Exercise> fillExercises(String ids) throws IOException, ParseException {
        List<Exercise> exercises = new ArrayList<>();
        Catalog c = new Catalog();
        String[] parts = ids.split("-");
        for(String id : parts){
            exercises.add(c.getExercise(Integer.parseInt(id)));
        }
        return exercises;
    }
} // WebServer<<<

