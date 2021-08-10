package com.pdefence.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pdefence.entity.Request;
import com.pdefence.entity.User;
import com.pdefence.entity.enums.Role;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static java.time.LocalTime.now;

@Service
public class RequestService {
    public static final String COL_NAME = "requests";

    public void saveRequest(Request request) {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            String requestId = String.valueOf(System.currentTimeMillis());
            dbFirestore.collection(COL_NAME).document(requestId).set(request);

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public List<Request> getRequestByDate(Date date) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        List<Request> requests = db.collection(COL_NAME).whereEqualTo("date", date).get().get().toObjects(Request.class);
        return requests;
    }

    public List<Request> getRequestByEmail(String email) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        List<Request> requests = db.collection(COL_NAME).whereEqualTo("createdBy", email).get().get().toObjects(Request.class);
        return requests;
    }

    public List<Request> getAllRequests() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference = dbFirestore.collection(COL_NAME);
        QuerySnapshot queryList = collectionReference.get().get();

        List<QueryDocumentSnapshot> docsList = queryList.getDocuments();
        List<Request> toReturn = new ArrayList<>();

        for (QueryDocumentSnapshot queryDocumentSnapshot : docsList) {
            toReturn.add(queryDocumentSnapshot.toObject(Request.class));
        }
        return toReturn;
    }
}
