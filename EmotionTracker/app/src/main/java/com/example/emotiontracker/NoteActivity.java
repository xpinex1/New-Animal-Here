package com.example.emotiontracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NoteActivity extends AppCompatActivity{
    private static final String TAG = "NoteActivity";

    private TextView date;
    private Button b;
    private FirebaseFirestore db = null;
    private FirebaseAuth mAuth;
    private EditText tBox;
    private String d;
    private Spinner feeling;
    private Spinner weather;
    private EditText location;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.note_activity);
        date = (TextView) findViewById(R.id.date);
        tBox = (EditText) findViewById(R.id.textBox);
        feeling = (Spinner) findViewById(R.id.feeling);
        ArrayAdapter<CharSequence> adapterF = ArrayAdapter.createFromResource(this, R.array.feelings_array, android.R.layout.simple_spinner_item);
        adapterF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feeling.setAdapter(adapterF);
        weather = (Spinner) findViewById(R.id.weather);
        ArrayAdapter<CharSequence> adapterW = ArrayAdapter.createFromResource(this, R.array.weather_array, android.R.layout.simple_spinner_item);
        adapterW.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weather.setAdapter(adapterW);
        location = (EditText) findViewById(R.id.location);
        b=(Button)findViewById(R.id.button);
        db = FirebaseFirestore.getInstance();
        Intent in = getIntent();
        d = in.getStringExtra("date");
        date.setText(d);
//        exampleData();
        getDocument();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterJournal();
                Intent intent = new Intent(NoteActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void getDocument() {
        // [START get_document]
        FirebaseUser user = mAuth.getCurrentUser();
        String displayName = user.getDisplayName();
        DocumentReference docRef = db.collection(displayName).document(d);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String stupid = document.getData().toString();
                        if (stupid.length() > 0) {
                            tBox.setText(stupid.substring(stupid.indexOf("entry=")+6,stupid.indexOf("weather")-2));
                            feeling.setSelection(Integer.parseInt(stupid.substring(stupid.indexOf("feeling=") +8, stupid.length()-1)));
                            location.setText(stupid.substring(stupid.indexOf("location=") +9, stupid.indexOf("feeling")-2));
                            weather.setSelection(Integer.parseInt(stupid.substring(stupid.indexOf("weather=") +8, stupid.indexOf("location")-2)));
                        } else{}
                    }
                }
            }
        });
        // [END get_document]
    }
    public void enterJournal(){

        FirebaseUser user = mAuth.getCurrentUser();
        String displayName = user.getDisplayName();
        CollectionReference currentUser = db.collection(displayName);

        Map<String, Object> entry = new HashMap<>();
        entry.put("entry", tBox.getText().toString());
        entry.put("feeling",feeling.getSelectedItemPosition());
        entry.put("location",location.getText().toString());
        entry.put("weather",weather.getSelectedItemPosition());
        entry.put("date",d);
//        entry.put("name", "San Francisco");
//        entry.put("state", "CA");
//        entry.put("country", "USA");
//        entry.put("capital", false);
//        entry.put("population", 860000);
//        entry.put("regions", Arrays.asList("west_coast", "norcal"));
//        currentUser.document("SF").set(data1);
        currentUser.document(d).set(entry);
        if (weather.getSelectedItemPosition()==0){
            if(feeling.getSelectedItemPosition()==0){
                currentUser.document("Sunny").update("Happy", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==1){
                currentUser.document("Sunny").update("Sad", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==2){
                currentUser.document("Sunny").update("Angry", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==3){
                currentUser.document("Sunny").update("Frustrated", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==4){
                currentUser.document("Sunny").update("Lonely", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==5){
                currentUser.document("Sunny").update("Anxious", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==6){
                currentUser.document("Sunny").update("Scared", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==7){
                currentUser.document("Sunny").update("Worried", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==8){
                currentUser.document("Sunny").update("Meh", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==9){
                currentUser.document("Sunny").update("Neutral or Other", FieldValue.increment(1));
            }
        }
        else if(weather.getSelectedItemPosition()==1){
            if(feeling.getSelectedItemPosition()==0){
                currentUser.document("Cloudy").update("Happy", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==1){
                currentUser.document("Cloudy").update("Sad", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==2){
                currentUser.document("Cloudy").update("Angry", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==3){
                currentUser.document("Cloudy").update("Frustrated", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==4){
                currentUser.document("Cloudy").update("Lonely", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==5){
                currentUser.document("Cloudy").update("Anxious", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==6){
                currentUser.document("Cloudy").update("Scared", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==7){
                currentUser.document("Cloudy").update("Worried", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==8){
                currentUser.document("Cloudy").update("Meh", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==9){
                currentUser.document("Cloudy").update("Neutral or Other", FieldValue.increment(1));
            }
        }
        else if(weather.getSelectedItemPosition()==2){
            if(feeling.getSelectedItemPosition()==0){
                currentUser.document("Rainy").update("Happy", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==1){
                currentUser.document("Rainy").update("Sad", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==2){
                currentUser.document("Rainy").update("Angry", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==3){
                currentUser.document("Rainy").update("Frustrated", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==4){
                currentUser.document("Rainy").update("Lonely", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==5){
                currentUser.document("Rainy").update("Anxious", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==6){
                currentUser.document("Rainy").update("Scared", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==7){
                currentUser.document("Rainy").update("Worried", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==8){
                currentUser.document("Rainy").update("Meh", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==9){
                currentUser.document("Rainy").update("Neutral or Other", FieldValue.increment(1));
            }
        }
        else if(weather.getSelectedItemPosition()==3){
            if(feeling.getSelectedItemPosition()==0){
                currentUser.document("Snowy").update("Happy", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==1){
                currentUser.document("Snowy").update("Sad", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==2){
                currentUser.document("Snowy").update("Angry", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==3){
                currentUser.document("Snowy").update("Frustrated", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==4){
                currentUser.document("Snowy").update("Lonely", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==5){
                currentUser.document("Snowy").update("Anxious", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==6){
                currentUser.document("Snowy").update("Scared", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==7){
                currentUser.document("Snowy").update("Worried", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==8){
                currentUser.document("Snowy").update("Meh", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==9){
                currentUser.document("Snowy").update("Neutral or Other", FieldValue.increment(1));
            }
        }
        else if(weather.getSelectedItemPosition()==4){
            if(feeling.getSelectedItemPosition()==0){
                currentUser.document("Windy").update("Happy", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==1){
                currentUser.document("Windy").update("Sad", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==2){
                currentUser.document("Windy").update("Angry", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==3){
                currentUser.document("Windy").update("Frustrated", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==4){
                currentUser.document("Windy").update("Lonely", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==5){
                currentUser.document("Windy").update("Anxious", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==6){
                currentUser.document("Windy").update("Scared", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==7){
                currentUser.document("Windy").update("Worried", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==8){
                currentUser.document("Windy").update("Meh", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==9){
                currentUser.document("Windy").update("Neutral or Other", FieldValue.increment(1));
            }
        }
        else if(weather.getSelectedItemPosition()==5){
            if(feeling.getSelectedItemPosition()==0){
                currentUser.document("Other").update("Happy", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==1){
                currentUser.document("Other").update("Sad", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==2){
                currentUser.document("Other").update("Angry", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==3){
                currentUser.document("Other").update("Frustrated", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==4){
                currentUser.document("Other").update("Lonely", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==5){
                currentUser.document("Other").update("Anxious", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==6){
                currentUser.document("Other").update("Scared", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==7){
                currentUser.document("Other").update("Worried", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==8){
                currentUser.document("Other").update("Meh", FieldValue.increment(1));
            }
            if(feeling.getSelectedItemPosition()==9){
                currentUser.document("Other").update("Neutral or Other", FieldValue.increment(1));
            }
        }
    }
//    public void exampleData() {
//        // [START example_data]
//        FirebaseUser user = mAuth.getCurrentUser();
//        String displayName = user.getDisplayName();
//        CollectionReference currentUser = db.collection(displayName);
//
//        Map<String, Object> data1 = new HashMap<>();
//        data1.put("name", "San Francisco");
//        data1.put("state", "CA");
//        data1.put("country", "USA");
//        data1.put("capital", false);
//        data1.put("population", 860000);
//        data1.put("regions", Arrays.asList("west_coast", "norcal"));
//        currentUser.document("SF").set(data1);
//
//        // [END example_data]
//    }
}
