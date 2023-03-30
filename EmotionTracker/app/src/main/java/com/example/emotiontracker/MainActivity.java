package com.example.emotiontracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    public static final String GOOGLE_ACCOUNT = "google_account";
    private GoogleSignInClient mGoogleSignOutClient;

    private static final String TAG = "main";

    private TextView profileName, profileEmail;
    private ImageView profileImage;

    private Calendar cal;
    private Button signOut;
    private Button viewJournal;
    private Button newJournal;
    private Button viewStats;
    private FirebaseAuth mAuth;
    private SimpleDateFormat dateFormat;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        setDataOnView();
        cDoc();
        cal = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-M-yyyy");
        date = dateFormat.format(cal.getTime());
//        cal = (CalendarView) findViewById(R.id.cal);
        findViewById(R.id.sign_out).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, SignIn.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
                                                       });
        findViewById(R.id.view_journal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewJournal.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        findViewById(R.id.new_journal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });
        findViewById(R.id.view_stats).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StatsView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
//        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
//                String date = day+"-"+(month +1)+"-"+year;
//                Log.d(TAG, "onSelectedDayChange: dd-mm-yyyy:" + date);
//                Intent intent = new Intent(MainActivity.this,NoteActivity.class);
//                intent.putExtra("date",date);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,SignIn.class));
        }
    }

    private void setDataOnView() {
        FirebaseUser user = mAuth.getCurrentUser();
        String displayName = user.getDisplayName();
        setTitle(displayName);
    }

    private void cDoc(){
        FirebaseUser user = mAuth.getCurrentUser();
        String displayName = user.getDisplayName();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference currentUser = db.collection(displayName);
        DocumentReference docRef = db.collection(displayName).document("Sunny");
        docRef.get().addOnCompleteListener((new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot doc =  task.getResult();
                    if(doc.exists()){}
                    else{
                        Map<String, Object> feelings = new HashMap<>();
                        feelings.put("Happy",0);
                        feelings.put("Sad",0);
                        feelings.put("Angry",0);
                        feelings.put("Frustrated",0);
                        feelings.put("Lonely",0);
                        feelings.put("Anxious",0);
                        feelings.put("Scared",0);
                        feelings.put("Worried",0);
                        feelings.put("Meh",0);
                        feelings.put("Neutral or Other",0);
                        currentUser.document("Sunny").set(feelings);
                        currentUser.document("Cloudy").set(feelings);
                        currentUser.document("Rainy").set(feelings);
                        currentUser.document("Snowy").set(feelings);
                        currentUser.document("Windy").set(feelings);
                        currentUser.document("Other").set(feelings);
                    }
                }
            }
        }));

    }

}