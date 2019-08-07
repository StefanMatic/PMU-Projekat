package com.example.lenovo.pmuprojekat.Main.GameAudio;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.example.lenovo.pmuprojekat.Main.Main.AppConstants;
import com.example.lenovo.pmuprojekat.R;

import java.util.HashMap;

public class GameAudioPlayer {

    private Context context;
    private SoundPool soundPool  = new SoundPool(10, AudioManager.STREAM_MUSIC, 100);
    private HashMap<Integer, Integer> soundPoolMap;

    public GameAudioPlayer(Context context) {
        this.context = context;
        initSoundPool();
    }

    private void initSoundPool() {
        soundPoolMap = new HashMap<Integer, Integer>();
        soundPoolMap.put(AppConstants.getCROWD(), soundPool.load(context, R.raw.crowd, 1));
        soundPoolMap.put(AppConstants.getCOLLISION(), soundPool.load(context, R.raw.collision, 1));
        soundPoolMap.put(AppConstants.getBEEP(), soundPool.load(context, R.raw.beep, 1));
    }

    public void playSound(int soundId) {
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        float curVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        float maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float leftVolume = curVolume/maxVolume;
        float rightVolume = curVolume/maxVolume;
        int priority = 1;
        int no_loop = 0;
        float normal_playback_rate = 1f;
        soundPool.play(soundId, leftVolume, rightVolume, priority, no_loop, normal_playback_rate);
    }
}
