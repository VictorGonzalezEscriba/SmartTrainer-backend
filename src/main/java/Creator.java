import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.*;

public class Creator {
    // Private attributes
    private final Catalog m_catalog;

    public Creator() throws IOException, ParseException {
        this.m_catalog = new Catalog();
        // m_catalog.showExercises();
    }

    // Esta función será utlitizada si el entrenamiento se hace en el gym
    public void fillCatalog(int type){
        int series;
        int repes;
        // strength // sets between 3-5 and repetitions between 5-8
        if (type == 0){
            series = 3;
            repes = 5;
        }
        else {
            if (type == 1){
                // hypertrophy // sets between 3-4 and repetitions between 8-15
                series = 4;
                repes = 10;
            }
            else{
                series = 4;
                repes = 11;
            }
        }

        for (Exercise e : this.m_catalog.getExerciseList()){
            double weight = 0.0;
            if (Objects.equals(e.getName(), "Plancha") || (Objects.equals(e.getName(), "Plancha lateral") || Objects.equals(e.getName(), "Plancha estrella"))){
                e.addDetails(weight, series, 1);
            }
            else{
                e.addDetails(weight, series, repes);
            }
        }

    }

    public Training createTraining(int location, int type, int bodyPart) {
        Training t = new Training("Nuevo entrenamiento", type);
        t.changeDate(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH) + 1, Calendar.getInstance().get(Calendar.DATE));
        Random rand = new Random();
        // Filtro por location = 0 gym 1 casa
        List<Exercise> list = this.m_catalog.filerLocation(location);
        // Gym
        if (location == 0) {
            fillCatalog(type);
            // Si quiere hacer una rutina full body
            if (bodyPart == 0) {
                List<Exercise> l1 = this.m_catalog.checkFilter("Pectoral", list);
                t.addExercise(l1.get(rand.nextInt(l1.size())));
                // Espalda
                List<Exercise> l2 = this.m_catalog.checkFilter("Espalda", list);
                t.addExercise(l2.get(rand.nextInt(l2.size())));
                // Abdomen
                List<Exercise> l3 = this.m_catalog.checkFilter("Abdomen", list);
                t.addExercise(l3.get(rand.nextInt(l3.size())));
                // Pierna
                List<Exercise> l4 = this.m_catalog.checkFilter("Pierna", list);
                Exercise e = l4.get(rand.nextInt(l4.size()));
                t.addExercise(e);
                Exercise e2 = l4.get(rand.nextInt(l4.size()));
                if (e == e2) {
                    while (e==e2){
                        e2 = l4.get(rand.nextInt(l4.size()));
                    }
                }
                t.addExercise(e2);
            }
            else
            {
                // Parte superior
                if (bodyPart == 1){
                    // System.out.println("Bucle ejercicios");
                    // 1 pectoral, 1 espalda, 1 abdomen, 1 hombro, 1 tricpes 1 biceps
                    List<Exercise> l1 = this.m_catalog.checkFilter("Pectoral", list);
                    t.addExercise(l1.get(rand.nextInt(l1.size())));
                    List<Exercise> l2 = this.m_catalog.checkFilter("Espalda", list);
                    t.addExercise(l2.get(rand.nextInt(l2.size())));
                    List<Exercise> l3 = this.m_catalog.checkFilter("Abdomen", list);
                    t.addExercise(l3.get(rand.nextInt(l3.size())));
                    List<Exercise> l4 = this.m_catalog.checkFilter("Hombro", list);
                    t.addExercise(l4.get(rand.nextInt(l4.size())));
                    List<Exercise> l5 = this.m_catalog.checkFilter("Tríceps", list);
                    t.addExercise(l5.get(rand.nextInt(l5.size())));
                    List<Exercise> l6 = this.m_catalog.checkFilter("Bíceps", list);
                    t.addExercise(l6.get(rand.nextInt(l6.size())));
                }
                else
                {
                    if (bodyPart == 2){
                        System.out.println("Parte de abajo");
                        List<Exercise> l1 = this.m_catalog.checkFilter("Pierna", list);
                        /*
                        for (Exercise e : l1){
                            System.out.println(e.showDetails());
                        }
                         */
                        int pesoMuerto = rand.nextInt(2);
                        if (pesoMuerto == 0){
                            l1.remove(1);
                        }
                         else{
                              l1.remove(2);
                        }
                        for (int i = 0; i < 5; i++){
                            int j = rand.nextInt(l1.size());
                            t.addExercise(l1.get(j));
                            l1.remove(j);
                        }
                    }
                }
            }
        }
        else // casa
        {
            // En casa 4x12
            fillCatalog(2);
            // 1 de pecho, 1 de espalda, 1 de abdomen, 2 de pierna
            // Pecho
            List<Exercise> l1 = this.m_catalog.checkFilter("Pectoral", list);
            t.addExercise(l1.get(rand.nextInt(l1.size())));
            // Espalda
            List<Exercise> l2 = this.m_catalog.checkFilter("Espalda", list);
            t.addExercise(l2.get(rand.nextInt(l2.size())));
            // Abdomen
            List<Exercise> l3 = this.m_catalog.checkFilter("Abdomen", list);
            t.addExercise(l3.get(rand.nextInt(l3.size())));
            // Pierna
            List<Exercise> l4 = this.m_catalog.checkFilter("Pierna", list);
            Exercise e = l4.get(rand.nextInt(l4.size()));
            t.addExercise(e);
            Exercise e2 = l4.get(rand.nextInt(l4.size()));
            if (e == e2) {
                while (e==e2){
                    e2 = l4.get(rand.nextInt(l4.size()));
                }
            }
            t.addExercise(e2);
        }
        return t;
    }

}
