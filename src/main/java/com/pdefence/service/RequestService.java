package com.pdefence.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pdefence.entity.Request;
import com.pdefence.entity.User;
import com.pdefence.entity.enums.Role;
import com.pdefence.entity.enums.Status;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static java.time.LocalTime.now;

@Service
public class RequestService {
    public static final String COL_NAME = "requests";

    public void saveRequest(Request request) {
        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            String requestId = String.valueOf(System.currentTimeMillis());
            request.setId(requestId);
            dbFirestore.collection(COL_NAME).document(requestId).set(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public List<Request> getRequestByDate(Date date) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        List<Request> requests = db.collection(COL_NAME).whereEqualTo("date", date).get().get().toObjects(Request.class);
        this.sortRequests(requests);
        return requests;
    }

    public List<Request> getRequestByEmail(String email) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        List<Request> requests = db.collection(COL_NAME).whereEqualTo("createdBy", email).get().get().toObjects(Request.class);
        this.sortRequests(requests);
        return requests;
    }

    public Request getRequestById(String id) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        Request request = db.collection(COL_NAME).whereEqualTo("id", id).get().get().toObjects(Request.class).get(0);
        return request;
    }

    public List<Request> getAllRequests() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        List<Request> requests = dbFirestore.collection(COL_NAME).whereGreaterThanOrEqualTo("date", new Date()).get().get().toObjects(Request.class);

        this.sortRequests(requests);
        return requests;
    }

    public List<Request> getArchivedRequests() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        List<Request> requests = dbFirestore.collection(COL_NAME).whereLessThanOrEqualTo("date", new Date()).get().get().toObjects(Request.class);

        this.sortRequests(requests);
        return requests;
    }

    private void sortRequests(List<Request> toReturn) {
        toReturn.sort(Comparator.comparing(Request::getHour));
        toReturn.sort(Comparator.comparing(Request::getDate));
    }


    public Request setStatusToRequest(String id, Status status) throws ExecutionException, InterruptedException {
        Request request = this.getRequestById(id);
        request.setStatus(status);
        Firestore dbFirestore = FirestoreClient.getFirestore();
        dbFirestore.collection(COL_NAME).document(id).set(request);
        return null;
    }


}
