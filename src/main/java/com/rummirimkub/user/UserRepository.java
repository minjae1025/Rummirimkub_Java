package com.rummirimkub.user;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
public class UserRepository {

    private final Firestore firestore;
    public static final String USERS_COLLECTION = "users";

    public UserRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public SiteUser save(SiteUser user) {
        ApiFuture<WriteResult> future = firestore.collection(USERS_COLLECTION).document(user.getUsername()).set(user);
        try {
            future.get(); // block until write is complete
            return user;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to save user to Firestore", e);
        }
    }

    public Optional<SiteUser> findByUsername(String username) {
        try {
            DocumentSnapshot documentSnapshot = firestore.collection(USERS_COLLECTION).document(username).get().get();
            if (documentSnapshot.exists()) {
                SiteUser user = documentSnapshot.toObject(SiteUser.class);
                return Optional.ofNullable(user);
            } else {
                return Optional.empty();
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to retrieve user from Firestore", e);
        }
    }
}
