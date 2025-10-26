package com.rummirimkub.firebase;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FirebaseService {
    private final FirebaseApp firebaseApp;

    @Autowired
    public FirebaseService(FirebaseApp firebaseApp) {
        this.firebaseApp = firebaseApp;
    }

//    public void save() {
//        Firestore db = FirestoreClient.getFirestore();
//        try {
//            DocumentReference docRef = db.collection("users").document("test1");
//            // Add document data  with id "alovelace" using a hashmap
//            Map<String, Object> data = new HashMap<>();
//            //asynchronously write data
//            ApiFuture<WriteResult> result = docRef.set(data);
//            // ...
//            // result.get() blocks on response
//            System.out.println("Update time : " + result.get().getUpdateTime());
//        }
//        catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public void login() {

    }
}
