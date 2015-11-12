package ctec.audiovideo;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.*;

public class SoundActivity extends Activity implements Runnable
{
    private Button startButton;
    private Button stopButton;
    private Button pauseButton;
    private Button videoButton;
    private MediaPlayer soundPlayer;
    private SeekBar soundSeekBar;
    private Thread soundThread;
    private RelativeLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        startButton = (Button) findViewById(R.id.playButton);
        pauseButton = (Button) findViewById(R.id.pauseButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        videoButton = (Button) findViewById(R.id.videoButton);
        soundSeekBar = (SeekBar) findViewById(R.id.soundSeekBar);
        background = (RelativeLayout) findViewById(R.id.background);
        soundPlayer = MediaPlayer.create(this.getBaseContext(), R.raw.fun);

        setupListeners();

        soundThread = new Thread(this);
        soundThread.start();

    }
    private void changeBackground()
    {
        int redColor;
        int greenColor;
        int blueColor;

        redColor = (int) (Math.random() * 256);
        greenColor = (int) (Math.random() * 256);
        blueColor = (int) (Math.random() * 256);

        startButton.setBackgroundColor(Color.rgb(redColor, greenColor, blueColor));
        pauseButton.setBackgroundColor(Color.rgb(redColor, greenColor, blueColor));
        stopButton.setBackgroundColor(Color.rgb(redColor, greenColor, blueColor));
        videoButton.setBackgroundColor(Color.rgb(redColor, greenColor, blueColor));
    }

    private void setupListeners()
    {
        startButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                soundPlayer.start();
                background.setBackgroundColor(Color.rgb(0, 255, 0));
                changeBackground();
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                soundPlayer.pause();
                background.setBackgroundColor(Color.rgb(90, 90, 255));
                changeBackground();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                soundPlayer.stop();
                soundPlayer = MediaPlayer.create(getBaseContext(), R.raw.fun);
                background.setBackgroundColor(Color.rgb(255, 0, 0));
                changeBackground();
            }
        });


        videoButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View currentView)
            {
                Intent myIntent = new Intent(currentView.getContext(), VideoActivity.class);
                startActivityForResult(myIntent, 0);
            }

        });

        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if(fromUser)
                {
                    soundPlayer.seekTo(progress);
                }
            }
        });
    }

    @Override
    public void run()
    {
        int currentPosition = 0;
        int soundTotal =  soundPlayer.getDuration();
        soundSeekBar.setMax(soundTotal);

        while (soundPlayer != null && currentPosition < soundTotal)
        {
            try
            {
                Thread.sleep(300);
                currentPosition = soundPlayer.getCurrentPosition();

            }
            catch(InterruptedException soundException)
            {
                return;
            }
            catch (Exception otherException)
            {
                return;
            }
            soundSeekBar.setProgress(currentPosition);
        }

    }
}
