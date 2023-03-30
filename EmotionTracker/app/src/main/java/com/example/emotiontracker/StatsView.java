package com.example.emotiontracker;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class StatsView extends AppCompatActivity {
    private static final String TAG = "StatsView";
    private Button enter;
    private FirebaseAuth mAuth;
    private Spinner weather;
    private FirebaseFirestore db = null;
    private int happy,sad,angry,frustrated,lonely,anxious,scared,worried,meh,neutral;
    private int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_view);
        mAuth = FirebaseAuth.getInstance();
        weather = (Spinner) findViewById(R.id.weather);
        ArrayAdapter<CharSequence> adapterW = ArrayAdapter.createFromResource(this, R.array.weather_array, android.R.layout.simple_spinner_item);
        adapterW.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weather.setAdapter(adapterW);
        weather.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected = position;
                if(selected == 0) getDocument("Sunny");
                else if(selected==1) getDocument("Cloudy");
                else if(selected==2) getDocument("Rainy");
                else if(selected==3) getDocument("Snowy");
                else if(selected==4) getDocument("Windy");
                else if(selected==5) getDocument("Other");
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatsView.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    public void getDocument(String s){
        FirebaseUser user = mAuth.getCurrentUser();
        String displayName = user.getDisplayName();
        db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection(displayName).document(s);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if(doc.exists()){
                        String feelings = doc.getData().toString();
                        if(feelings.length() > 0){
                            happy = Integer.parseInt(feelings.substring(feelings.indexOf("Happy=")+6,feelings.indexOf("Angry")-2));
                            sad = Integer.parseInt(feelings.substring(feelings.indexOf("Sad=")+4,feelings.indexOf("Anxious")-2));
                            angry = Integer.parseInt(feelings.substring(feelings.indexOf("Angry=")+6,feelings.indexOf("Lonely")-2));
                            frustrated = Integer.parseInt(feelings.substring(feelings.indexOf("Frustrated=")+11,feelings.indexOf("Meh")-2));
                            lonely = Integer.parseInt(feelings.substring(feelings.indexOf("Lonely=")+7,feelings.indexOf("Sad")-2));
                            anxious = Integer.parseInt(feelings.substring(feelings.indexOf("Anxious=")+8,feelings.length()-1));
                            scared = Integer.parseInt(feelings.substring(feelings.indexOf("Scared=")+7,feelings.indexOf("Worried")-2));
                            worried = Integer.parseInt(feelings.substring(feelings.indexOf("Worried=")+8,feelings.indexOf("Happy")-2));
                            meh = Integer.parseInt(feelings.substring(feelings.indexOf("Meh=")+4,feelings.indexOf("Neutral")-2));
                            neutral = Integer.parseInt(feelings.substring(feelings.indexOf("Neutral or Other=")+17,feelings.indexOf("Scared")-2));
                            GraphView graph = (GraphView) findViewById(R.id.graph);
                            graph.removeAllSeries();
                            BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                                    new DataPoint(0, happy),
                                    new DataPoint(1, sad),
                                    new DataPoint(2, angry),
                                    new DataPoint(3, frustrated),
                                    new DataPoint(4, lonely),
                                    new DataPoint(5,anxious),
                                    new DataPoint(6, scared),
                                    new DataPoint(7, worried),
                                    new DataPoint(8, meh),
                                    new DataPoint(9,neutral)

                            });
                            graph.getGridLabelRenderer().setNumHorizontalLabels(6);
                            graph.addSeries(series);
                            series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
                                @Override
                                public int get(DataPoint data) {
                                    return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
                                }
                            });

                            series.setSpacing(10);

                            // draw values on top
                            series.setDrawValuesOnTop(true);
                            series.setValuesOnTopColor(Color.RED);
                        }
                    }
                }
            }
        });
    }

}
