package salinesingularity.singularitytimingapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    int gearsScored, gearsDropped, gearsPickedUp;
    ArrayList<TeleopEvent> events;

    public final long GAME_LENGTH = 150000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        events = new ArrayList<>();

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    public void onPickupS (View v)
    {
        events.add(new TeleopEvent(TeleopEventType.PICKUP_S, System.currentTimeMillis()));
        updateValues();
    }

    public void onPickupF (View v)
    {

        events.add(new TeleopEvent(TeleopEventType.PICKUP_F, System.currentTimeMillis()));
        updateValues();
    }

    public void onScoredS (View v)
    {
        events.add(new TeleopEvent(TeleopEventType.GEAR_SCORE_S, System.currentTimeMillis()));
        updateValues();
    }

    public void onScoredF (View v)
    {
        events.add(new TeleopEvent(TeleopEventType.GEAR_SCORE_F, System.currentTimeMillis()));
        updateValues();
    }

    public void onDropped (View v)
    {
        events.add(new TeleopEvent(TeleopEventType.GEAR_DROP, System.currentTimeMillis()));
        updateValues();
    }

    public void onUndo (View v)
    {

    }

    public void onRedo (View v)
    {

    }

    public void updateValues() {
        //Log.d("Update!", "updated values");
        Log.d("Updated array: ", events.toString());
    }

    public void onEndGame (View v)
    {
        //Generate a string representing the match
        long endTime = System.currentTimeMillis();
        String result = "";
        for(TeleopEvent t : events) {
            //Log.d("event", t.toStringGameTime(endTime));
            result += t.toStringGameTime(endTime) + "; ";
        }

        Log.d("result", result);

        //Copy the string to clipboard
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Singularity Match Data", result);
        clipboard.setPrimaryClip(clip);

        Toast toast = Toast.makeText(getApplicationContext(), "Match data copied to clipboard", Toast.LENGTH_SHORT);
        toast.show();



    }

    private class TeleopEvent
    {
        private long timestamp;
        private TeleopEventType eventType;

        public TeleopEvent(TeleopEventType eventType, long timestamp) {

            this.timestamp = timestamp;
            this.eventType = eventType;

        }

        public String toString() {

            return eventType + ", " + timestamp;

        }

        public String toStringGameTime(long endTime) {
            double gameTime = ((double)(GAME_LENGTH - (endTime - timestamp))) / 1000.0;
            return eventType + ", " + gameTime;
        }

    }

    private enum TeleopEventType {

        PICKUP_S,
        PICKUP_F,
        GEAR_SCORE_S,
        GEAR_SCORE_F,
        GEAR_DROP
    }
}
