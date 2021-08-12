package com.example.egyedu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    //view
    private FragmentContainerView nav;
    private BottomNavigationView bottomNavigationView;

    private TextView title;

    private NavController navController;

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //findView
        nav = findViewById(R.id.nav_host_fragment);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        title = findViewById(R.id.title_Discover_courses);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(item ->
        {


            switch (item.getItemId()) {
                case R.id.nav_home:
                    title.setText(R.string.home);
                    navController.navigate(R.id.homeFragment);
                    break;

                case R.id.nav_profile:
                    title.setText(R.string.profile);
                    navController.navigate(R.id.profileFlagment);
                    break;

                    case R.id.nav_mycourses:
                    title.setText("Mycours");
                    navController.navigate(R.id.mycoursesFragment);
                    break;

                case R.id.nav_filter:
                    title.setText(R.string.filters);
                    navController.navigate(R.id.filterFragment);
                    break;


                case R.id.nav_favorite:
                    title.setText(R.string.favorite);
                    navController.navigate(R.id.favoriteFlagment);
                    break;


            }
            return true;

        });


    }


}
