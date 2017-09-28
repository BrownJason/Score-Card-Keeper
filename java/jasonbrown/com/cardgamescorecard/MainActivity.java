package jasonbrown.com.cardgamescorecard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    //ArrayList<String> player_Names = new ArrayList<>();
    Button btn_enter, btn_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_booksAndRuns = (Button) findViewById(R.id.btn_booksAndRuns);
        btn_booksAndRuns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.dialog_player_names); // sets the content of the view to the xml file dialog_player_names
                btn_enter = (Button) findViewById(R.id.btn_enter); // initialize the enter button
                btn_cancel = (Button) findViewById(R.id.btn_cancel); // initialize the cancel button

                btn_enter.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         EditText players = (EditText) findViewById(R.id.player_Names); // initialize the edittext for the players name

                         if(players.getText()!= null){ // as long as the edit text is not null it will gather the information below

                              // gets the text from the edit text and splits into an array if there is a comma between the names
                             //String[] players = players.getTexT().toString().split(",");
                             //player_Names.add(players);

                             int playerName = Integer.parseInt(players.getText().toString());

                             if (playerName > 8) { // Max amount of players is 8 for this game, if names array is longer than 8 strings long
                             /*
                                Displays a Toast if the amount of players entered into the edit text is greater than 8 telling the user
                                that only 8 players can be entered and clears the edit text along with the arraylist so no duplicates
                                are created in the table
                              */
                                 Toast.makeText(MainActivity.this, "Too many players, can only be 8 total players!", Toast.LENGTH_SHORT).show();

                             } else if (playerName <= 8) { // if names string array is less than or equal to 8 it will enter into the if statement below creating the table with the players name
                             /*
                                Creates a new intent called PlayerScoresLayout and stores the names from player_Names arraylist as extra context to be sent over
                                to the new intent as long as "Player_Names" is called in the new intent with getIntent.getExtras("Player_Names");


                              */
                                 Intent intent = new Intent(MainActivity.this, PlayerScoresLayout.class);
                                 intent.putExtra("Player_Names", playerName);
                                 MainActivity.this.startActivity(intent);
                                 finish();
                             }



                         } else if(players.getText().toString().equals(" ")){
                             AlertDialog errorDialog = new AlertDialog.Builder(MainActivity.this).create();
                             errorDialog.setTitle("Error");
                             errorDialog.setMessage("You need to enter a name!");
                             errorDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener(){
                                 public void onClick(DialogInterface dialog, int which){
                                     dialog.cancel();
                                 }
                             });
                         }
                     }
                 });
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /*
                            Whent the cancel button is clicked, it will restart the main activity
                         */
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        MainActivity.this.startActivity(intent);
                        finish();
                    }
                });
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        /*
            This will contain the loading screen / splash screen for the application
         */
    }

    /*
            When the exit button is clicked it will close the application
         */
    public void exit(View v){
        System.exit(0);
    }
}
