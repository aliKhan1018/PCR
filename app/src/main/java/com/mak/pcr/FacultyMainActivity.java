package com.mak.pcr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.mak.pcr.databinding.ActivityFacultyMainBinding;
import com.mak.pcr.databinding.ActivityMainBinding;
import com.mak.pcr.dbentities.Faculty;

public class FacultyMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityFacultyMainBinding binding;

    TextView txtvw_facultyName, txtvw_facultyEmail;
    DatabaseConnection db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityFacultyMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DatabaseConnection();


        setSupportActionBar(binding.appBarFaculty.toolbar);
//        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = binding.facultyDrawerLayout;
        NavigationView navigationView = binding.navViewFaculty;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_faculty);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        if(!Network.isNetworkAvailable(FacultyMainActivity.this)){
            new AlertDialog.Builder(FacultyMainActivity.this)
                    .setTitle("Internet Required!")
                    .setMessage("Please connect to the internet and try again")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setCancelable(false)
                    .show();
        }


//        btn_signout = navigationView.findViewById(R.id.btn_signout);
//        btn_signout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utility.MakeToast(FacultyMainActivity.this, "Signing out...", 0);
//                db.get_firebaseAuth().signOut();
//                startActivity( new Intent( FacultyMainActivity.this, FacultyLogin.class ));
//                finish();
//            }
//        });

        String _id = db.get_firebaseAuth().getUid();

        db.get_dbReference("Faculty").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(_id)){
                    Faculty _f = snapshot.child(_id).getValue( Faculty.class );

                    txtvw_facultyName = navigationView.findViewById(R.id.txtvw_facultyName);
                    txtvw_facultyEmail = navigationView.findViewById(R.id.txtvw_facultyEmail);

                    txtvw_facultyEmail.setText(_f.getEmail());
                    txtvw_facultyName.setText(_f.getFullName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_faculty);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}