package com.example.lenovo.pmuprojekat.Main.Main;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.lenovo.pmuprojekat.R;

//Klasa koja omogucava da sve slike od trenutne igre budu na jednom mestu.
//Moze da se iskoristi kako bi se zapamtilo trenutno stanje igre
public class BitmapBank {
    private Bitmap player1Flag, player2Flag;
    private Bitmap filed;
    private Bitmap ball;
    private Bitmap selected;

    //ovaj konstruktor postavlja sve podrazumevane parametre
    //moze da se koristi nakon sto se radi reset trenutnog stanja igre
    public BitmapBank(Resources res) {
        this.player1Flag = BitmapFactory.decodeResource(res, R.drawable.flag0);
        this.player2Flag = BitmapFactory.decodeResource(res, R.drawable.flag1);
        this.filed = BitmapFactory.decodeResource(res, R.drawable.field0);
        this.ball = BitmapFactory.decodeResource(res, R.drawable.ball);
        this.selected  =BitmapFactory.decodeResource(res, R.drawable.selected);
    }

    //Moze da se ubace sve slike osim slike lopte i selektovanog igraca
    public BitmapBank(Resources res, Bitmap player1Flag, Bitmap player2Flag, Bitmap filed) {
        this.player1Flag = player1Flag;
        this.player2Flag = player2Flag;
        this.filed = filed;

        this.ball = BitmapFactory.decodeResource(res, R.drawable.ball);
        this.selected = BitmapFactory.decodeResource(res, R.drawable.selected);
    }

    public Bitmap getPlayer1Flag() {
        return player1Flag;
    }

    public void setPlayer1Flag(Bitmap player1Flag) {
        this.player1Flag = player1Flag;
    }

    public Bitmap getPlayer2Flag() {
        return player2Flag;
    }

    public void setPlayer2Flag(Bitmap player2Flag) {
        this.player2Flag = player2Flag;
    }

    public Bitmap getFiled() {
        return filed;
    }

    public void setFiled(Bitmap filed) {
        this.filed = filed;
    }
}
