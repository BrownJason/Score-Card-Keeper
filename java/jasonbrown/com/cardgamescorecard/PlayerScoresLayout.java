package jasonbrown.com.cardgamescorecard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.view.ViewGroup.LayoutParams.FILL_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.VERTICAL;
/**
 * Created by Jason_Brown on 9/15/2017.
 *
 * The purpose of this class is to create a table based on user input
 * and to keep score of the game that you are playing. This is for
 * a card game called Books and Runs 
 *
 */

public class PlayerScoresLayout extends Activity {
    ArrayList<Integer> editTextScore;
    ArrayList<EditText> editTextList = new ArrayList<>();
    ArrayList<EditText> playerScoreList = new ArrayList<>();
    ArrayList<String> playerNames;
    EditText playerScore;
    int i, j;
    int cellId, numOfPlayers = 0;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        String[] rows = {"Round 1: 2 Books ","Round 2: 1 Book 1 Run ","Round 3: 2 Runs ","Round 4: 3 Books ","Round 5: 2 Books 1 Run ","Round 6: 1 Book 2 Runs ","Round 7: 3 Runs "}; //Round Names
        int columns = 0;

		Bundle playerBundle = getIntent().getExtras();
		if(playerBundle!=null){
			columns = playerBundle.getInt("Player_Names");
		}
        int rowLength = rows.length; // sets the amount of rows there are in the table
        int colLength = columns; // sets the of columns there will be in the table

        TableLayout tableLayout = createTable(rows, columns, rowLength+1, colLength+1); // creates the table based on the information provided and increase row and col amount by 1 to
																					 // account for the player names for the player names and the game round names
        Buttons();

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(VERTICAL);

		ScrollView scrollView = new ScrollView(this);
		scrollView.setScrollbarFadingEnabled(false);
		HorizontalScrollView horzScrollView = new HorizontalScrollView(this);
		horzScrollView.addView(tableLayout);
		scrollView.addView(horzScrollView);
        linearLayout.addView(scrollView);
        linearLayout.addView(Buttons());
        setContentView(linearLayout);
    }

	// Uses some of the inforamtion in the onCreate method to create the table layout for this class
	// to be seen by the user
    private TableLayout createTable(String[] rows, int cols, int rowCount, int colCount) {
		
    editTextScore = new ArrayList<>();

		GradientDrawable gradDraw = new GradientDrawable();
		gradDraw.setColor(Color.BLACK);
		gradDraw.setCornerRadius(5);
		gradDraw.setStroke(1, Color.GREEN);

        final TableLayout scoreTableLayout = new TableLayout(this);
		TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams();

		TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
		for (i = 0; i < rowCount; i++) {
			TableRow tableRows = new TableRow(this);
            tableRows.setBackgroundResource(R.drawable.table_row);

			for (j = 0; j < colCount; j++) {
				if(i ==0 && j == 0) {
					EditText roundAndPlayerNames = new EditText(this);
					roundAndPlayerNames.setBackgroundResource(R.drawable.table_row_cells);
					roundAndPlayerNames.setPadding(5,0,5,0);
					roundAndPlayerNames.setWidth(650);
					roundAndPlayerNames.setText(getString(R.string.players));
					roundAndPlayerNames.setFocusable(false);
                    roundAndPlayerNames.setTextColor(Color.BLACK);
					tableRows.addView(roundAndPlayerNames, tableRowParams);
				} else if(i == 0) {
					EditText roundAndPlayerNames = new EditText(this);
					roundAndPlayerNames.setBackgroundResource(R.drawable.table_row_cells);
					roundAndPlayerNames.setPadding(5,0,5,0);
					roundAndPlayerNames.setWidth(250);
                    roundAndPlayerNames.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                    roundAndPlayerNames.setTextColor(Color.BLACK);
					tableRows.addView(roundAndPlayerNames, tableRowParams);
				} else if (j == 0) {
					EditText roundAndPlayerNames = new EditText(this);
					roundAndPlayerNames.setPadding(5,0,5,0);
					roundAndPlayerNames.setWidth(150);
					roundAndPlayerNames.setText(rows[i - 1]);
					roundAndPlayerNames.setFocusable(false);
                    roundAndPlayerNames.setTextColor(Color.BLACK);
					roundAndPlayerNames.setBackgroundResource(R.drawable.table_row_cells);
					tableRows.addView(roundAndPlayerNames, tableRowParams);
				} else {
					playerScore = new EditText(this);
					//playerScore.setText(cellId);
					playerScore.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER);
					playerScore.setPadding(5,0,5,0);
					playerScore.setBackgroundResource(R.drawable.table_row_cells);
                    playerScore.setTextColor(Color.BLACK);
					editTextList.add(playerScore);
					tableRows.addView(playerScore, tableRowParams);
				}
			}
			cellId++;
			scoreTableLayout.addView(tableRows, tableLayoutParams);
		}

        final TableRow totalRow = new TableRow(this);
        totalRow.setBackgroundResource(R.drawable.table_row);

        TextView totalScores = new TextView(this);
        totalScores.setText(getString(R.string.totals));
        totalScores.setBackgroundResource(R.drawable.table_row_cells);
        totalScores.setWidth(450);
        totalScores.setTextSize(18);
        totalScores.setTextColor(Color.BLACK);
        totalRow.addView(totalScores);

        final int numOfPlayers = j - 1;

        for(int y =0; y < numOfPlayers;y++){
            EditText playersScores = new EditText(this);
            playersScores.setBackgroundResource(R.drawable.table_row_cells);
            playersScores.setWidth(240);
            playersScores.setTextColor(Color.BLACK);
            playersScores.setFocusable(false);
            totalRow.addView(playersScores);
            playerScoreList.add(playersScores);
        }

        scoreTableLayout.addView(totalRow, tableLayoutParams);

		return scoreTableLayout;
    }

    public LinearLayout Buttons(){
        LinearLayout linLayout = new LinearLayout(this);

        TableRow buttonRow = new TableRow(this);
        Button btn_score = new Button(this);
        btn_score.setText(getString(R.string.score));

        TextView blankSpace = new TextView(this);
        //blankSpace.setWidth(600);

        Button btn_back = new Button(this);
        btn_back.setText(getString(R.string.back));

        buttonRow.addView(btn_score);
        buttonRow.addView(blankSpace);
        buttonRow.addView(btn_back);


        btn_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // For ever child in the table layout (meaning every table row there is)
                // It will loop through those rows
                int finalScore = 0;
				/*
				*	This do loop will cycle for the amount of players there are to a max of 8. Each time it will then enter into an
				*	if statment depending on the tempNumOfPlayers. The if statement contains a for loop consisting of each players
				*	score and will output that score to the corresponding textfield in the tablelayout
				*/
                int tempNumOfPlayers = 0;
                numOfPlayers = j-1;
                do {
                    if(tempNumOfPlayers == 0) { // called if tempNumOfPlayers is equal to 0 ( 1st Player)
                        // int is set to tempNumOfPlayers because its the spot where the 6th player score starts in the editTextList ArrayList and increments based on the number of players
                        // the reasoning behind this is because its not in a matrix Arraylist, but a regular arraylist, the scores are all entered dependent on when the
                        // edittext was created and it will increment every numOfPlayers times. Every for loop is the same except for the edittext.setText() at the end. Giving the score to
                        // the correct players textfield and can't be altered unless a edittext field was changed
                        for (int x = 0; x < editTextList.size(); x += numOfPlayers) {
                            try {
                                int tempScore = Integer.parseInt(editTextList.get(x).getText().toString()); //parses the edittext array list so that it outputs a int instead of the index value of the edittext also accounts for any change made to any edittext
                                finalScore = finalScore + tempScore;
                                playerScoreList.get(tempNumOfPlayers).setText(getString(R.string.finalscore, finalScore));
                            } catch (NumberFormatException e) {
                                //Toast.makeText(PlayerScoresLayout.this, "Please enter in the missing score!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    } else if(tempNumOfPlayers == 1){ // called if tempNumOfPlayers is equal to 1 ( 2nd Player)
                        finalScore = 0;
                        for (int x = 1; x < editTextList.size(); x += numOfPlayers) {
                            try {
                                int tempScore = Integer.parseInt(editTextList.get(x).getText().toString()); //parses the edittext array list so that it outputs a int instead of the index value of the edittext also accounts for any change made to any edittext
                                finalScore = finalScore + tempScore;
                                playerScoreList.get(tempNumOfPlayers).setText(getString(R.string.finalscore, finalScore));
                            } catch (NumberFormatException e) {
                                //Toast.makeText(PlayerScoresLayout.this, "Please enter in the missing score!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if(tempNumOfPlayers == 2) { // called if tempNumOfPlayers is equal to 2 ( 3rd Player)
                        finalScore = 0;
                        for (int x = 2; x < editTextList.size(); x += numOfPlayers) {
                            try {
                                int tempScore = Integer.parseInt(editTextList.get(x).getText().toString()); //parses the edittext array list so that it outputs a int instead of the index value of the edittext also accounts for any change made to any edittext
                                finalScore = finalScore + tempScore;
                                playerScoreList.get(tempNumOfPlayers).setText(getString(R.string.finalscore, finalScore));
                            } catch (NumberFormatException e) {
                                //Toast.makeText(PlayerScoresLayout.this, "Please enter in the missing score!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if(tempNumOfPlayers == 3) { // called if tempNumOfPlayers is equal to 3 ( 4th Player)
                        finalScore = 0;
                        for (int x = 3; x < editTextList.size(); x += numOfPlayers) {
                            try {
                                int tempScore = Integer.parseInt(editTextList.get(x).getText().toString()); //parses the edittext array list so that it outputs a int instead of the index value of the edittext also accounts for any change made to any edittext
                                finalScore = finalScore + tempScore;
                                playerScoreList.get(tempNumOfPlayers).setText(getString(R.string.finalscore, finalScore));
                            } catch (NumberFormatException e) {
                                //Toast.makeText(PlayerScoresLayout.this, "Please enter in the missing score!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if(tempNumOfPlayers == 4) { // called if tempNumOfPlayers is equal to 4 ( 5th Player)
                        finalScore = 0;
                        for (int x = 4; x < editTextList.size(); x += numOfPlayers) {
                            try {
                                int tempScore = Integer.parseInt(editTextList.get(x).getText().toString()); //parses the edittext array list so that it outputs a int instead of the index value of the edittext also accounts for any change made to any edittext
                                finalScore = finalScore + tempScore;
                                playerScoreList.get(tempNumOfPlayers).setText(getString(R.string.finalscore, finalScore));
                            } catch (NumberFormatException e) {
                                //Toast.makeText(PlayerScoresLayout.this, "Please enter in the missing score!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if(tempNumOfPlayers == 5) { // called if tempNumOfPlayers is equal to 5 ( 6th Player)
                        finalScore = 0;
                        for (int x = 5; x < editTextList.size(); x += numOfPlayers) {
                            try {
                                int tempScore = Integer.parseInt(editTextList.get(x).getText().toString()); //parses the edittext array list so that it outputs a int instead of the index value of the edittext also accounts for any change made to any edittext
                                finalScore = finalScore + tempScore;
                                playerScoreList.get(tempNumOfPlayers).setText(getString(R.string.finalscore, finalScore));
                            } catch (NumberFormatException e) {
                                //Toast.makeText(PlayerScoresLayout.this, "Please enter in the missing score!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if(tempNumOfPlayers == 6) {// called if tempNumOfPlayers is equal to 6 ( 7th Player)
                        finalScore = 0;
                        for (int x = 6; x < editTextList.size(); x += numOfPlayers) {
                            try {
                                int tempScore = Integer.parseInt(editTextList.get(x).getText().toString()); //parses the edittext array list so that it outputs a int instead of the index value of the edittext also accounts for any change made to any edittext
                                finalScore = finalScore + tempScore;
                                playerScoreList.get(tempNumOfPlayers).setText(getString(R.string.finalscore, finalScore));
                            } catch (NumberFormatException e) {
                                //Toast.makeText(PlayerScoresLayout.this, "Please enter in the missing score!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else { // called if tempNumOfPlayers is greater than 7, doesn't enter any of the above if statements ( 8th Player)
                        finalScore = 0;
                        for (int x = 7; x < editTextList.size(); x += numOfPlayers) {
                            try {
                                int tempScore = Integer.parseInt(editTextList.get(x).getText().toString()); //parses the edittext array list so that it outputs a int instead of the index value of the edittext also accounts for any change made to any edittext
                                finalScore = finalScore + tempScore;
                                playerScoreList.get(tempNumOfPlayers).setText(getString(R.string.finalscore, finalScore));
                            } catch (NumberFormatException e) {
                                //Toast.makeText(PlayerScoresLayout.this, "Please enter in the missing score!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    tempNumOfPlayers++;
                } while (tempNumOfPlayers != numOfPlayers);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlayerScoresLayout.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        /* Not actually needed
        Button btn_save = new Button(this);
        btn_save.setText("Save Content");
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTableInfo(view);
            }
        });

        Button btn_display = new Button(this);
        btn_display.setText("Display Content");
        btn_display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayData(view);
            }
        }); */

        //buttonRow.addView(btn_save);
        //buttonRow.addView(btn_display);
        linLayout.addView(buttonRow);

        return linLayout;
    }

    /*
    *   Not actually needed just using to test a couple of things

    public void saveTableInfo(View view){
        SharedPreferences shardPref = getSharedPreferences("tableInfo", Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = shardPref.edit();
        for(i = 0; i < editTextList.size();i++){
            edit.putInt("tableContent", Integer.parseInt(editTextList.get(i).getText().toString()));
            Log.d("Editor: ", edit.toString());
            edit.apply();
            edit.commit();
        }
    }

    public void displayData(View view){
        SharedPreferences shardPref = getSharedPreferences("tableInfo", Context.MODE_PRIVATE);

        for(i = 0; i < editTextList.size();i++) {
            int tableScore = shardPref.getInt("tableContent", i);
            Log.d("Shared Pref Display: ", String.valueOf(editTextList.size()));
            editTextList.get(i).setText(getString(R.string.playerscore, tableScore));

        }
    } */
}