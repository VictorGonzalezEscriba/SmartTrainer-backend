import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.cloud.firestore.v1.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Firebase {



    Firebase() {
        initialize();
    }

    public void initialize() {
        try {
            FileInputStream serviceAccount = new FileInputStream("./src/main/java/smarttrainer-b8a31-firebase-adminsdk-6woa3-83da450854.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String addTraining (String id, String t) throws IOException, ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.create(options);
        ApiFuture<WriteResult> collectionApiFuture = db.collection("trainings").document(id).set(t);
        return collectionApiFuture.get().getUpdateTime().toString();
    }
}
