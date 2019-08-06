package com.example.lenovo.pmuprojekat.Main.Main;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.lenovo.pmuprojekat.R;

//Klasa koja omogucava da sve slike od trenutne igre budu na jednom mestu.
//Moze da se iskoristi kako bi se zapamtilo trenutno stanje igre
public class BitmapBank {
    private Bitmap player1Flag, player2Flag;
    private Bitmap field;
    private Bitmap goal;
    private Bitmap ball;
    private Bitmap selected;

    //ovaj konstruktor postavlja sve podrazumevane parametre
    //moze da se koristi nakon sto se radi reset trenutnog stanja igre
    public BitmapBank(Resources res) {
        this.player1Flag = BitmapFactory.decodeResource(res, R.drawable.flag0);
        this.player2Flag = BitmapFactory.decodeResource(res, R.drawable.flag1);
        this.field = BitmapFactory.decodeResource(res, R.drawable.field0);
        this.goal = BitmapFactory.decodeResource(res, R.drawable.goal);
        this.ball = BitmapFactory.decodeResource(res, R.drawable.ball);
        this.selected  =BitmapFactory.decodeResource(res, R.drawable.selected);

        resizeImages();
    }

    //Slike se postavljaju u proporcije koje ce biti koriscene u igri
    private void resizeImages() {
        Bitmap resizePlayer1 = Bitmap.createScaledBitmap(this.player1Flag,
                (int)(AppConstants.PLAYER_RADIUS*2),
                (int)(AppConstants.PLAYER_RADIUS*2),
                true);
        this.player1Flag = resizePlayer1;

        Bitmap resizePlayer2 = Bitmap.createScaledBitmap(this.player2Flag,
                (int)(AppConstants.PLAYER_RADIUS*2),
                (int)(AppConstants.PLAYER_RADIUS*2),
                true);
        this.player2Flag = resizePlayer2;

        Bitmap resizeField = Bitmap.createScaledBitmap(this.field,
                AppConstants.SCREEN_WIDTH,
                AppConstants.SCREEN_HEIGHT,
                true);
        this.field = resizeField;

        Bitmap resizeGoal = Bitmap.createScaledBitmap(this.goal,
                AppConstants.SCREEN_WIDTH,
                AppConstants.SCREEN_HEIGHT,
                true);
        this.goal = resizeGoal;

        Bitmap resizeBall = Bitmap.createScaledBitmap(this.ball,
                (int)(AppConstants.SOCCERBALL_RADIUS*2),
                (int)(AppConstants.SOCCERBALL_RADIUS*2),
                true);
        this.ball = resizeBall;

        //Stavljamo da bude malo vece od velicine igraca
        Bitmap resizeSelected = Bitmap.createScaledBitmap(this.selected,
                (int)(AppConstants.PLAYER_RADIUS*3),
                (int)(AppConstants.PLAYER_RADIUS*3),
                true);
        this.selected = resizeSelected;
    }

    //Moze da se ubace sve slike osim slike lopte i selektovanog igraca
    public BitmapBank(Resources res, Bitmap player1Flag, Bitmap player2Flag, Bitmap field) {
        this.player1Flag = player1Flag;
        this.player2Flag = player2Flag;
        this.field = field;

        this.ball = BitmapFactory.decodeResource(res, R.drawable.ball);
        this.selected = BitmapFactory.decodeResource(res, R.drawable.selected);

        resizeImages();
    }

    public Bitmap getPlayer1Flag() {
        return player1Flag;
    }

    public void setPlayer1Flag(Bitmap player1Flag) {
        this.player1Flag = player1Flag;
        resizeImages();
    }

    public Bitmap getPlayer2Flag() {
        return player2Flag;
    }

    public void setPlayer2Flag(Bitmap player2Flag) {
        this.player2Flag = player2Flag;
        resizeImages();
    }

    public Bitmap getFiled() {
        return field;
    }

    public void setFiled(Bitmap filed) {
        this.field = filed;
        resizeImages();
    }

    public Bitmap getBall() {
        return ball;
    }

    public Bitmap getSelected() {
        return selected;
    }

    public Bitmap getGoal() {
        return goal;
    }
}
