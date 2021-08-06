package com.pdefence.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.pdefence.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {
    public static final String COL_NAME = "users";

    public User saveUserDetails(User user) throws InterruptedException, ExecutionException {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(user.getEmail()).set(user);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return user;
//        collectionsApiFuture.get().getUpdateTime().toString();
    }

    public User getUserDetails(User user) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(user.getEmail());
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        User databaseUser = null;

        if (document.exists()) {
            databaseUser = document.toObject(User.class);
            assert databaseUser != null;
            if (databaseUser.getPassword().equals(user.getPassword())) {
                return databaseUser;
            } else {
                throw new Error("Password is wrong");
            }
        } else {
            return null;
        }
    }

    public List<User> getAllUsers() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference = dbFirestore.collection(COL_NAME);
        ApiFuture<QuerySnapshot> future = collectionReference.get();
        QuerySnapshot queryList = future.get();
        List<QueryDocumentSnapshot> docsList = queryList.getDocuments();
        List<User> toReturn = new ArrayList<>();
        for (QueryDocumentSnapshot queryDocumentSnapshot : docsList) {
            toReturn.add(queryDocumentSnapshot.toObject(User.class));
        }
        return toReturn;
    }

    public String updateUserDetails(User person) throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(person.getName()).set(person);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String deleteUser(String name) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(name).delete();
        return "Document with User ID " + name + " has been deleted";
    }
}