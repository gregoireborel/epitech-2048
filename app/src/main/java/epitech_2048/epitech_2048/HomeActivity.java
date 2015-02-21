package epitech_2048.epitech_2048;

import android.gesture.GestureOverlayView;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Random;


public class HomeActivity extends ActionBarActivity
{
    private TextView            messageView;
    private GestureOverlayView  gesture;
    private GameView            gameView;
    private int[][]             nbArray = new int[4][4];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        messageView = (TextView) findViewById(R.id.text_msg);
        messageView.setText("None.");

        gameView = (GameView) findViewById(R.id.view);

        /* Add the first two numbers */
        addNewNumber();
        addNewNumber();
        gameView.setNumberArray(nbArray);

        /* Set gesture recognizers */
        gesture = (GestureOverlayView) findViewById(R.id.gestureHandler);
        gesture.setOnTouchListener(new OnSwipeTouchListener(this) {

            private TextView messageView = (TextView) findViewById(R.id.text_msg);

            @Override
            public void onSwipeDown() {
                messageView.setText("Swipe Down.");
                moveAllNumbersToBottom();
                addNewNumber();
                printNbArray();
            }

            @Override
            public void onSwipeLeft() {
                messageView.setText("Swipe Left.");
                moveAllNumbersToLeft();
                addNewNumber();
                printNbArray();
            }

            @Override
            public void onSwipeUp() {
                messageView.setText("Swipe Up.");
                moveAllNumbersToTop();
                addNewNumber();
                printNbArray();
            }

            @Override
            public void onSwipeRight() {
                messageView.setText("Swipe Right.");
                moveAllNumbersToRight();
                addNewNumber();
                printNbArray();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.restart)
        {
            nbArray = new int[4][4];
            addNewNumber();
            addNewNumber();
            // HERE RESET UI
            return true;
        }
        else if (id == R.id.quit)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void printNbArray() {
        Log.d("nbArray", "/*** Printing array ***/");
        for (int x = 0; x < 4; x++) {
            Log.d("nbArray", String.format("%d %d %d %d", nbArray[x][0], nbArray[x][1], nbArray[x][2],nbArray[x][3]));
        }
        Log.d("nbArray", "/*** Finished printing ***/");
        gameView.setNumberArray(nbArray);
    }

    public void addNewNumber() {
        Random randomGenerator = new Random();
        int x,y;

        while (nbArray[(x = randomGenerator.nextInt(4))][(y = randomGenerator.nextInt(4))] != 0) {}

        nbArray[x][y] = randomGenerator.nextInt(4) == 0 ? 4 : 2;
    }

    public void moveAllNumbersToRight() {
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y > 0; y--) {
                if (nbArray[x][y] == 0 && nbArray[x][y-1] != 0) {
                    nbArray[x][y] = nbArray[x][y-1];
                    nbArray[x][y-1] = 0;
                    y = 4;
                }
            }
            for (int y = 3; y > 0; y--) {
                if (nbArray[x][y] != 0 && nbArray[x][y] == nbArray[x][y-1]) {
                    nbArray[x][y] += nbArray[x][y-1];
                    nbArray[x][y-1] = 0;
                }
            }
            for (int y = 3; y > 0; y--) {
                if (nbArray[x][y] == 0 && nbArray[x][y-1] != 0) {
                    nbArray[x][y] = nbArray[x][y-1];
                    nbArray[x][y-1] = 0;
                    y = 4;
                }
            }
        }
    }

    public void moveAllNumbersToLeft() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 3; y++) {
                if (nbArray[x][y] == 0 && nbArray[x][y+1] != 0) {
                    nbArray[x][y] = nbArray[x][y+1];
                    nbArray[x][y+1] = 0;
                    y = -1;
                }
            }
            for (int y = 0; y < 3; y++) {
                if (nbArray[x][y] != 0 && nbArray[x][y] == nbArray[x][y+1]) {
                    nbArray[x][y] += nbArray[x][y+1];
                    nbArray[x][y+1] = 0;
                }
            }
            for (int y = 0; y < 3; y++) {
                if (nbArray[x][y] == 0 && nbArray[x][y+1] != 0) {
                    nbArray[x][y] = nbArray[x][y+1];
                    nbArray[x][y+1] = 0;
                    y = -1;
                }
            }
        }
    }

    public void moveAllNumbersToTop() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 3; x++) {
                if (nbArray[x][y] == 0 && nbArray[x+1][y] != 0) {
                    nbArray[x][y] = nbArray[x+1][y];
                    nbArray[x+1][y] = 0;
                    x = -1;
                }
            }
            for (int x = 0; x < 3; x++) {
                if (nbArray[x][y] != 0 && nbArray[x][y] == nbArray[x+1][y]) {
                    nbArray[x][y] += nbArray[x+1][y];
                    nbArray[x+1][y] = 0;
                }
            }
            for (int x = 0; x < 3; x++) {
                if (nbArray[x][y] == 0 && nbArray[x+1][y] != 0) {
                    nbArray[x][y] = nbArray[x+1][y];
                    nbArray[x+1][y] = 0;
                    x = -1;
                }
            }
        }
    }

    public void moveAllNumbersToBottom() {
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x > 0; x--) {
                if (nbArray[x][y] == 0 && nbArray[x-1][y] != 0) {
                    nbArray[x][y] = nbArray[x-1][y];
                    nbArray[x-1][y] = 0;
                    x = 4;
                }
            }
            for (int x = 3; x > 0; x--) {
                if (nbArray[x][y] != 0 && nbArray[x][y] == nbArray[x-1][y]) {
                    nbArray[x][y] += nbArray[x-1][y];
                    nbArray[x-1][y] = 0;
                }
            }
            for (int x = 3; x > 0; x--) {
                if (nbArray[x][y] == 0 && nbArray[x-1][y] != 0) {
                    nbArray[x][y] = nbArray[x-1][y];
                    nbArray[x-1][y] = 0;
                    x = 4;
                }
            }
        }
    }
}
