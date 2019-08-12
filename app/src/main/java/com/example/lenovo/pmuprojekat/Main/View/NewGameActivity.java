package com.example.lenovo.pmuprojekat.Main.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.lenovo.pmuprojekat.Main.GameStats.GameStatus;
import com.example.lenovo.pmuprojekat.Main.Main.AppConstants;
import com.example.lenovo.pmuprojekat.R;

import androidx.appcompat.app.AppCompatActivity;

public class NewGameActivity extends AppCompatActivity {
    private EditText player1Name, player2Name;

    private ImageButton imageButtonleft1, imageButtonRight1;
    private ImageButton imageButtonleft2, imageButtonRight2;
    private ImageView imagePlayer1Flag, imagePlayer2Flag;
    private Button starNewGameButton;

    private Switch player1Computer, player2Computer;
    private boolean isPlayer1Computer, isPlayer2Computer;

    private Bitmap[] arrows;
    private int player1Counter;
    private int player2Counter;
    private Bitmap player1Flag, player2Flag;
    private BitmapDrawable[] arrowsDrawable;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Postavljanje trazene orientacije ekrana i postavljanje flegova radi koriscenja celog ekrana
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_new_game);

        //postavljanje pocetnih vrednosti
        isPlayer1Computer = false;
        isPlayer2Computer = false;

        //dohvatanje polja za popunjavanje imena
        player1Name = findViewById(R.id.player1Name);
        player2Name = findViewById(R.id.player2Name);

        //dohvatanje dugmica i prostor za postavljanje zastave timova
        imageButtonleft1 = findViewById(R.id.player1Left);
        imageButtonRight1 = findViewById(R.id.player1Right);
        imagePlayer1Flag = findViewById(R.id.player1Flag);

        imageButtonleft2 = findViewById(R.id.player2Left);
        imageButtonRight2 = findViewById(R.id.player2Right);
        imagePlayer2Flag = findViewById(R.id.player2Flag);

        player1Counter = 0;
        player2Counter = 11;

        arrows = new Bitmap[2];
        arrows = AppConstants.getBitmapBank().getArrows();
        arrowsDrawable = new BitmapDrawable[2];
        arrowsDrawable[0] = new BitmapDrawable(this.getResources(),arrows[0]);
        arrowsDrawable[1] = new BitmapDrawable(this.getResources(),arrows[1]);

        imageButtonleft1.setBackground(arrowsDrawable[0]);
        imageButtonRight1.setBackground(arrowsDrawable[1]);
        imageButtonleft2.setBackground(arrowsDrawable[0]);
        imageButtonRight2.setBackground(arrowsDrawable[1]);

        player1Flag = AppConstants.getBitmapBank().getFlag(player1Counter);
        player1Flag = AppConstants.getBitmapBank().resizePicture(player1Flag, AppConstants.SCREEN_HEIGHT / 3, AppConstants.SCREEN_HEIGHT / 3);

        player2Flag = AppConstants.getBitmapBank().getFlag(player2Counter);
        player2Flag = AppConstants.getBitmapBank().resizePicture(player2Flag, AppConstants.SCREEN_HEIGHT / 3, AppConstants.SCREEN_HEIGHT / 3);

        imagePlayer1Flag.setImageBitmap(player1Flag);
        imagePlayer2Flag.setImageBitmap(player2Flag);

        //Podesavanje switch komponenata na ekranu
        player1Computer = findViewById(R.id.player1Computer);
        player2Computer = findViewById(R.id.player2Computer);

        player1Computer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isPlayer1Computer = isChecked;
            }
        });

        player2Computer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isPlayer2Computer = isChecked;
            }
        });

        starNewGameButton = findViewById(R.id.startNewGameButton);

        Bitmap buttonBackgroundImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.button_background);
        buttonBackgroundImage = AppConstants.getBitmapBank().resizePicture(buttonBackgroundImage,
                (int)(AppConstants.SCREEN_WIDTH*0.4),
                (int) (AppConstants.SCREEN_HEIGHT*0.2));
        Drawable background = new BitmapDrawable(this.getResources(), buttonBackgroundImage);
        starNewGameButton.setBackground(background);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (AppConstants.isGamePaused())
            finish();
    }

    public void changeFlagPlayer1Left(View view) {
        if (player1Counter == 0){
            player1Counter = AppConstants.getBitmapBank().getNumberOfFlags()-1;
        }
        else
            player1Counter--;

        getAndSetFlag(true);
    }

    public void changeFlagPlayer1Right(View view) {
        if (player1Counter == AppConstants.getBitmapBank().getNumberOfFlags()-1){
            player1Counter = 0;
        }
        else
            player1Counter++;

        getAndSetFlag(true);
    }

    public void changeFlagPlayer2Left(View view) {
        if (player2Counter == 0){
            player2Counter = AppConstants.getBitmapBank().getNumberOfFlags()-1;
        }
        else
            player2Counter--;

        getAndSetFlag(false);
    }

    public void changeFlagPlayer2Right(View view) {
        if (player2Counter == AppConstants.getBitmapBank().getNumberOfFlags()-1){
            player2Counter = 0;
        }
        else
            player2Counter++;

        getAndSetFlag(false);
    }

    private void getAndSetFlag(boolean player1) {
        if (player1){
            player1Flag = AppConstants.getBitmapBank().getFlag(player1Counter);
            player1Flag = AppConstants.getBitmapBank().resizePicture(player1Flag,
                    AppConstants.SCREEN_HEIGHT / 3,
                    AppConstants.SCREEN_HEIGHT / 3);

            imagePlayer1Flag.setImageBitmap(player1Flag);
        }
        else {
            player2Flag = AppConstants.getBitmapBank().getFlag(player2Counter);
            player2Flag = AppConstants.getBitmapBank().resizePicture(player2Flag,
                    AppConstants.SCREEN_HEIGHT / 3,
                    AppConstants.SCREEN_HEIGHT / 3);

            imagePlayer2Flag.setImageBitmap(player2Flag);
        }
    }

    //postavljanje svih novih izbora, provera da li su svi podaci validni i pokretanje igre
    public void startNewGame(View view) {
        //Provera da li su sva polja popunjena i da li su korektno popunjena
        if (player1Name.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Player 1 name required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (player2Name.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Player 2 name required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (player1Counter == player2Counter){
            Toast.makeText(this, "Need to choose different teams", Toast.LENGTH_LONG).show();
            return;
        }

        //Postavljanje svih dobijenih podataka na mesta koja cemo koristiti dalje u programu
        AppConstants.getBitmapBank().setPlayer1Flag(AppConstants.getBitmapBank().getFlag(player1Counter));
        AppConstants.getBitmapBank().setPlayer2Flag(AppConstants.getBitmapBank().getFlag(player2Counter));
        AppConstants.getBitmapBank().setPlayer1FlagID(player1Counter);
        AppConstants.getBitmapBank().setPlayer2FlagID(player2Counter);

        GameStatus gameStatus = new GameStatus(player1Name.getText().toString(),
                player2Name.getText().toString(),
                AppConstants.getBitmapBank().getFiled(),
                AppConstants.getBitmapBank().getPlayer1Flag(),
                AppConstants.getBitmapBank().getPlayer2Flag(),
                isPlayer1Computer,
                isPlayer2Computer);

        AppConstants.getGameEngine().setGameStats(gameStatus);
        AppConstants.getGameEngine().initFiledAndPlayers(this);

        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
