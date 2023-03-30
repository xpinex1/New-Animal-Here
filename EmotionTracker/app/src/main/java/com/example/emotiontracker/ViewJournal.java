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

public class ViewJournal extends AppCompatActivity implements View.OnClickListener{
    public static final String GOOGLE_ACCOUNT = "google_account";
    private GoogleSignInClient mGoogleSignOutClient;

    private static final String TAG = "view";

    private TextView profileName, profileEmail;
    private ImageView profileImage;

    private CalendarView cal;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_journal);
        mAuth = FirebaseAuth.getInstance();
        setDataOnView();
        cal = (CalendarView) findViewById(R.id.cal);
        findViewById(R.id.home).setOnClickListener(this);
        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int day) {
                String date = day+"-"+(month+1)+"-"+year;
                Log.d(TAG, "onSelectedDayChange: dd-MM-yyyy:" + date);
                Intent intent = new Intent(ViewJournal.this,NoteActivity.class);
                intent.putExtra("date",date);
                startActivity(intent);
            }
        });
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

    @Override
    public void onClick(View v){
        Intent intent = new Intent(ViewJournal.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
//                }
//            });
//        }
//        else{
//            Intent intent = new Intent(MainActivity.this, SignIn.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//        }
    }
}
