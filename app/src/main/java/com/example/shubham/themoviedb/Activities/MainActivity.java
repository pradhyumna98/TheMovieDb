package com.example.shubham.themoviedb.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.shubham.themoviedb.Adapter.PageAdapter;
import com.example.shubham.themoviedb.Constants;
import com.example.shubham.themoviedb.Database.Database;
import com.example.shubham.themoviedb.Database.MovieDAO;
import com.example.shubham.themoviedb.Database.ShowDAO;
import com.example.shubham.themoviedb.Fragments.MoviesFragment;
import com.example.shubham.themoviedb.Fragments.ShowsFragment;
import com.example.shubham.themoviedb.Networking.ApiClient;
import com.example.shubham.themoviedb.R;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MoviesFragment.OnFragmentInteractionListener,ShowsFragment.OnFragmentInteractionListener {

    TabLayout tabLayout;
ViewPager viewPager;
EditText editText;
ImageButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);
        tabLayout=findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.Container);
        editText=findViewById(R.id.searchEditText);
        button=findViewById(R.id.imageButtonSearch);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().equals(""))
                    Toast.makeText(MainActivity.this,"Enter Some Thing",Toast.LENGTH_SHORT).show();
                else
                {
                    Intent intent=new Intent(MainActivity.this,SearchResult.class);
                    String search=editText.getText().toString();
                    intent.putExtra(Constants.QUERY,search);
                    startActivity(intent);
                }
            }
        });
        PageAdapter adapter = new PageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Favourite.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public void onMoviesFragmentInteraction() {

    }

    @Override
    public void onShowsFragmentInteraction() {

    }
}
