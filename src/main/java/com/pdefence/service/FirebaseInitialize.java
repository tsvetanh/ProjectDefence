package com.pdefence.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;

@Service
public class FirebaseInitialize {
        @PostConstruct
        public void initialize() {
            try {
                FileInputStream serviceAccount =
                        new FileInputStream("src/main/resources/pcnstuff-153f0-037ffb116155.json");
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://pcnstuff-153f0-default-rtdb.europe-west1.firebasedatabase.app")
                        .build();
                FirebaseApp.initializeApp(options);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
}
