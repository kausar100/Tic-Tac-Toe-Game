package com.kausar.hp.tictactoc;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView playerOneScore, playerTwoScore, playerStatus;
    Button[] buttons = new Button[9];
    Button btnReset;
    int playerOneScoreCount, playerTwoScoreCount, roundCount, totalPlayerOneScore, totalPlayerTwoScore, totalRount;
    boolean activePlayer;
    //p1=>0, p2=>1, empty=>2
    int[] gameState = {2,2,2,2,2,2,2,2,2};
    int[][] winningPositions= {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},//row
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},//col
            {0, 4, 8}, {2, 4, 6}//diagonal
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore=findViewById(R.id.playerOneScore);
        playerTwoScore=findViewById(R.id.playerTwoScore);
        playerStatus=findViewById(R.id.playerStatus);

        btnReset=findViewById(R.id.btnReset);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText("");
                updatePlayerScore();

            }
        });

        for(int i = 0; i<9; i++)
        {
            String buttonID = "btn_"+i;
            int resourceID = getResources().getIdentifier(buttonID,"id",getPackageName());
            buttons[i] =  findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        roundCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;

    }

    @Override
    public void onClick(View view) {
        if(!((Button)view).getText().toString().equals(""))
        {
            return;
        }

        String buttonID = view.getResources().getResourceEntryName(view.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        if(activePlayer){
            ((Button)view).setText("X");
            ((Button)view).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        }else{
            ((Button)view).setText("O");
            ((Button)view).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;
        }
        roundCount++;

        if(checkWinner()){
            if(activePlayer){
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }else{
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }

        }else if(roundCount == 9){
            playAgain();
            Toast.makeText(this, "No Winner!", Toast.LENGTH_SHORT).show();

        }else{
            activePlayer = !activePlayer;
        }

        if(playerOneScoreCount > playerTwoScoreCount){
            playerStatus.setText("Player One is Winning!");
        }else if(playerTwoScoreCount>playerOneScoreCount){
            playerStatus.setText("Player Two is Winning!");
        }else{
            playerStatus.setText("");
        }



    }

    public boolean checkWinner()
    {
        boolean winnerResult = false;
        for(int[] winningPosition : winningPositions)
        {
            if( gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] !=2){
                winnerResult = true;

            }
        }
        return  winnerResult;
    }

    public void updatePlayerScore()
    {
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    public void playAgain(){
        roundCount = 0;
        activePlayer = true;
        for(int i = 0; i<9; i++)
        {
            gameState[i] = 2;
            buttons[i].setText("");

        }
    }
}
