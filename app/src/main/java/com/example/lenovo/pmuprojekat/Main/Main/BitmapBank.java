package com.example.lenovo.pmuprojekat.Main.Main;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.lenovo.pmuprojekat.R;

import java.util.HashMap;

//Klasa koja omogucava da sve slike od trenutne igre budu na jednom mestu.
//Moze da se iskoristi kako bi se zapamtilo trenutno stanje igre
public class BitmapBank {
    private Bitmap player1Flag, player2Flag;
    private Bitmap field;
    private Bitmap goal;
    private Bitmap ball;
    private Bitmap selected;
    private Bitmap[] arrows;

    private HashMap<Integer, Bitmap> allFlags;
    private HashMap<Integer, Bitmap> allFields;

    //ovaj konstruktor postavlja sve podrazumevane parametre
    //moze da se koristi nakon sto se radi reset trenutnog stanja igre
    public BitmapBank(Resources res) {
        this.player1Flag = BitmapFactory.decodeResource(res, R.drawable.flag0);
        this.player2Flag = BitmapFactory.decodeResource(res, R.drawable.flag1);
        this.field = BitmapFactory.decodeResource(res, R.drawable.field0);
        this.goal = BitmapFactory.decodeResource(res, R.drawable.goal);
        this.ball = BitmapFactory.decodeResource(res, R.drawable.ball);
        this.selected = BitmapFactory.decodeResource(res, R.drawable.selected);
        this.arrows = new Bitmap[2];

        this.arrows[0] = BitmapFactory.decodeResource(res, R.drawable.arrow_left);
        this.arrows[1] = BitmapFactory.decodeResource(res, R.drawable.arrow_right);

        initAllFlags(res);
        initAllFields(res);
        resizeImages();
    }

    private void initAllFlags(Resources res) {
        allFlags = new HashMap<>();
        allFlags.put(0, BitmapFactory.decodeResource(res, R.drawable.flag0));
        allFlags.put(1, BitmapFactory.decodeResource(res, R.drawable.flag1));
        allFlags.put(2, BitmapFactory.decodeResource(res, R.drawable.flag2));
        allFlags.put(3, BitmapFactory.decodeResource(res, R.drawable.flag3));
        allFlags.put(4, BitmapFactory.decodeResource(res, R.drawable.flag4));
        allFlags.put(5, BitmapFactory.decodeResource(res, R.drawable.flag5));
        allFlags.put(6, BitmapFactory.decodeResource(res, R.drawable.flag6));
        allFlags.put(7, BitmapFactory.decodeResource(res, R.drawable.flag7));
        allFlags.put(8, BitmapFactory.decodeResource(res, R.drawable.flag8));
        allFlags.put(9, BitmapFactory.decodeResource(res, R.drawable.flag9));
        allFlags.put(10, BitmapFactory.decodeResource(res, R.drawable.flag10));
        allFlags.put(11, BitmapFactory.decodeResource(res, R.drawable.flag11));
        allFlags.put(12, BitmapFactory.decodeResource(res, R.drawable.flag12));
        allFlags.put(13, BitmapFactory.decodeResource(res, R.drawable.flag13));
        allFlags.put(14, BitmapFactory.decodeResource(res, R.drawable.flag14));
        allFlags.put(15, BitmapFactory.decodeResource(res, R.drawable.flag15));
        allFlags.put(16, BitmapFactory.decodeResource(res, R.drawable.flag16));
        allFlags.put(17, BitmapFactory.decodeResource(res, R.drawable.flag17));
        allFlags.put(18, BitmapFactory.decodeResource(res, R.drawable.flag18));
    }

    private void initAllFields(Resources res) {
        allFields = new HashMap<>();
        allFields.put(0, BitmapFactory.decodeResource(res, R.drawable.field0));
        allFields.put(1, BitmapFactory.decodeResource(res, R.drawable.field1));
        allFields.put(2, BitmapFactory.decodeResource(res, R.drawable.field2));
        allFields.put(3, BitmapFactory.decodeResource(res, R.drawable.field3));
    }

    //Slike se postavljaju u proporcije koje ce biti koriscene u igri
    private void resizeImages() {
        Bitmap resizePlayer1 = Bitmap.createScaledBitmap(this.player1Flag,
                (int) (AppConstants.PLAYER_RADIUS * 2),
                (int) (AppConstants.PLAYER_RADIUS * 2),
                true);
        this.player1Flag = resizePlayer1;

        Bitmap resizePlayer2 = Bitmap.createScaledBitmap(this.player2Flag,
                (int) (AppConstants.PLAYER_RADIUS * 2),
                (int) (AppConstants.PLAYER_RADIUS * 2),
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
                (int) (AppConstants.SOCCERBALL_RADIUS * 2),
                (int) (AppConstants.SOCCERBALL_RADIUS * 2),
                true);
        this.ball = resizeBall;

        //Stavljamo da bude malo vece od velicine igraca
        Bitmap resizeSelected = Bitmap.createScaledBitmap(this.selected,
                (int) (AppConstants.PLAYER_RADIUS * 3),
                (int) (AppConstants.PLAYER_RADIUS * 3),
                true);
        this.selected = resizeSelected;

        this.arrows[0] = resizePicture(this.arrows[0], AppConstants.SCREEN_WIDTH / 14, AppConstants.SCREEN_HEIGHT / 10);
        this.arrows[1] = resizePicture(this.arrows[1], AppConstants.SCREEN_WIDTH / 14, AppConstants.SCREEN_HEIGHT / 10);
    }

    public Bitmap resizePicture(Bitmap original, int width, int height) {
        Bitmap resizePicture = Bitmap.createScaledBitmap(original, width, height, true);
        return resizePicture;
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

    public Bitmap getFlag(int i) {
        return allFlags.get(i);
    }

    public int getNumberOfFlags() {
        return allFlags.size();
    }

    public Bitmap getField(int i) {
        return allFields.get(i);
    }

    public int getNumberOfFields() {
        return allFields.size();
    }

    public Bitmap[] getArrows() {
        return arrows;
    }
}
