package joshuuduan.giaoly.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashMainActivity extends AppCompatActivity {

    //firebase auth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUser();
                }
        },2000);
    }

    private void checkUser() {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null){
            //user not logger in
            //start main screen
            startActivity( new Intent(SplashMainActivity.this, MainActivity.class));
            finish(); // finish this activity
        }else {
            //user logged in check user type, same as done in login screen
            // check in db
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.child(firebaseUser.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange  (DataSnapshot snapshot) {
                              //get user type
                            String userType = ""+snapshot.child("userType").getValue();
                            //check user type
                            if(userType.equals("user")){
                                //this is simple user, open user dashboard
                                startActivity(new Intent(SplashMainActivity.this, DashboardUserActivity.class));
                                finish();
                            }else  if (userType.equals("admin")){
                                //this is amdim , open admin dashboard
                                startActivity(new Intent(SplashMainActivity.this, DashboardAdminActivity.class));
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled( DatabaseError error) {

                        }
                    });
        }
    }
}