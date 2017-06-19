package com.example.kovac94.meteorshower.score;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kovac94.meteorshower.R;
import com.example.kovac94.meteorshower.game.Game;

import org.json.JSONArray;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;

public class Leaderboard extends AppCompatActivity {

    ArrayList <Scores> list= new ArrayList<Scores>();
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_leaderboard);

        // Adding hardcoded data into list for testing purposes
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Scores newPlayer = new Scores(extras.getInt("Score"), extras.getString("Nick"));
            list.add(newPlayer);
        }
        ListView listView = (ListView) findViewById(R.id.listView1);
        b1 = (Button) findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Leaderboard.this.finish();
                Intent intent = new Intent(getApplicationContext(), Game.class);
                startActivity(intent);
            }
        });

        // Setting adapter for listview element

        ScoresAdapter adapter = new ScoresAdapter(this, R.layout.list_view_layout, list);
        listView.setAdapter(adapter);


    }


    @Override
    protected void onStop() {
        super.onStop();
        // Creating JSONArray and putting data into it
        JSONArray array = new JSONArray();
        for (int i = 0; i < list.size(); i++){
            array.put(list.get(i).getJSONObject());
        }

        // Serializing JSONArray in file
        Writer output = null;
        File file = new File(getFilesDir(), "MeteorShower.json");
        try {
            output = new BufferedWriter(new FileWriter(file));
            output.write(array.toString());
            output.close();
            Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Array: " + array.toString());
    }
}
