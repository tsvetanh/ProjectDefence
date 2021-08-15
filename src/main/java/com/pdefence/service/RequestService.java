package com.pdefence.service;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.pdefence.entity.Request;
import com.pdefence.entity.enums.Status;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

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
        List<Request> requests = db.collection(COL_NAME).whereEqualTo("date", date).whereNotEqualTo("status", "CANCELLED").get().get().toObjects(Request.class);
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
        Firestore db = FirestoreClient.getFirestore();
        Query query = db.collection(COL_NAME).whereGreaterThanOrEqualTo("date", new Date()).orderBy("date").orderBy("hour").limit(5);

        return query.get().get().toObjects(Request.class);
    }

    public List<Request> getArchivedRequests() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        Query query = db.collection(COL_NAME).whereLessThanOrEqualTo("date", new Date()).orderBy("date", Query.Direction.ASCENDING).orderBy("hour", Query.Direction.ASCENDING).limit(5);

        return query.get().get().toObjects(Request.class);
    }


    public List<Request> nextPage(Request last, boolean archive) throws ExecutionException, InterruptedException {

        Firestore db = FirestoreClient.getFirestore();
        CollectionReference ref = db.collection(COL_NAME);
        DocumentSnapshot snapshot = ref.document(last.getId()).get().get();

        Query query;
        if (archive) {
            query = ref.whereLessThanOrEqualTo("date", new Date()).orderBy("date").orderBy("hour").limit(5).startAfter(snapshot);
        } else {
            query = ref.whereGreaterThanOrEqualTo("date", new Date()).orderBy("date").orderBy("hour").limit(5).startAfter(snapshot);
        }

        List<Request> requests = query.get().get().toObjects(Request.class);
        return requests;
    }

    public List<Request> prevPage(Request first, boolean archive) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference ref = db.collection(COL_NAME);
        DocumentSnapshot snapshot = ref.document(first.getId()).get().get();

        Query query;
        if (archive) {
            query = ref.whereLessThanOrEqualTo("date", new Date()).orderBy("date", Query.Direction.DESCENDING).orderBy("hour", Query.Direction.DESCENDING).limit(5).startAfter(snapshot);
        } else {
            query = ref.whereGreaterThanOrEqualTo("date", new Date()).orderBy("date", Query.Direction.DESCENDING).orderBy("hour", Query.Direction.DESCENDING).limit(5).startAfter(snapshot);
        }

        List<Request> requests = query.get().get().toObjects(Request.class);
        this.sortRequests(requests);
        return requests;
    }


    private void sortRequests(List<Request> toReturn) {
        toReturn.sort(Comparator.comparing(Request::getHour));
        toReturn.sort(Comparator.comparing(Request::getDate));
    }


    public void setStatusToRequest(String id, Status status) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        Request request = this.getRequestById(id);
        request.setStatus(status);
        db.collection(COL_NAME).document(id).set(request);
    }

    public List<Integer> getStatusCount() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        List<Integer> list = new ArrayList<>();
        int activeCount = db.collection(COL_NAME).whereGreaterThanOrEqualTo("date", new Date()).whereEqualTo("status", "ACTIVE").get().get().size();
        list.add(activeCount);
        int doneCount = db.collection(COL_NAME).whereGreaterThanOrEqualTo("date", new Date()).whereEqualTo("status", "DONE").get().get().size();
        list.add(doneCount);
        int cancelledCount = db.collection(COL_NAME).whereGreaterThanOrEqualTo("date", new Date()).whereEqualTo("status", "CANCELLED").get().get().size();
        list.add(cancelledCount);
        return list;
    }
}
