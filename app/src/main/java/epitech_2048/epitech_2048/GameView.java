package epitech_2048.epitech_2048;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GameView extends View {
    public static class RGB {
        public int red, green, blue;

        public RGB(int red, int green, int blue) {
            this.red   = red;
            this.green = green;
            this.blue  = blue;
        }

        void setOnPaint(Paint paint) {
            paint.setARGB(255, red, green, blue);
        }
    }

    private static class NumberSquare {
        public int   value = 0;
        public Paint paint = new Paint();

        public NumberSquare() {
            initialize();
        }

        public void initialize() {
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setStrokeWidth(2);
            updatePaint();
        }

        public void setValue(int value) {
            this.value = value;
            updatePaint();
        }

        public void updatePaint() {
            int          power = 0;
            double       value = 0;
            GameView.RGB color = GameView.squareColors[0];

            while (value < 2048) {
                if (value == this.value)
                    color = GameView.squareColors[power];
                power += 1;
                value = Math.pow(2, power);
            }
            color.setOnPaint(paint);
        }
    }

    private final int         rowCount     = 4;
    private final int         colCount     = 4;
    private NumberSquare[][]  numbers      = new NumberSquare[rowCount][colCount];
    private int               canvasWidth  = 100;
    private int               canvasHeight = 100;
    private float             numberSizeX  = 10;
    private float             numberSizeY  = 10;
    public static RGB[]       squareColors = new RGB[12];
    private Paint             valuesPaint  = new Paint();
    private Paint.FontMetrics fontMetrics;

    public GameView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        valuesPaint.setColor(Color.BLACK);
        valuesPaint.setTextSize(100);
        fontMetrics = valuesPaint.getFontMetrics();
        initializeColors();
        for (int x = 0 ; x < rowCount ; x++) {
            for (int y = 0 ; y < colCount ; y++)
                numbers[x][y] = new NumberSquare();
        }
    }

    private void initializeColors() {
        squareColors[0]  = new RGB(0,   0,   0);
        squareColors[1]  = new RGB(213, 216, 218);
        squareColors[2]  = new RGB(192, 194, 196);
        squareColors[3]  = new RGB(173, 175, 176);
        squareColors[4]  = new RGB(156, 158, 158);
        squareColors[5]  = new RGB(140, 142, 142);
        squareColors[6]  = new RGB(126, 128, 128);
        squareColors[7]  = new RGB(113, 115, 115);
        squareColors[8]  = new RGB(102, 104, 104);
        squareColors[9]  = new RGB(92,  94,  94);
        squareColors[10] = new RGB(83,  85,  85);
        squareColors[11] = new RGB(75,  76,  76);
    }

    public void setNumberArray(int[][] array) {
        for (int x = 0 ; x < rowCount ; x++) {
          for (int y = 0 ; y < colCount ; y++)
            numbers[x][y].setValue(array[x][y]);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        renderNumbers(canvas);
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        canvasWidth  = w;
        canvasHeight = h;
        numberSizeX  = getNumberSizeX();
        numberSizeY  = getNumberSizeY();
        if (numberSizeY > numberSizeX)
          numberSizeY = numberSizeX;
        if (numberSizeX > numberSizeY)
          numberSizeX = numberSizeY;
    }

    private float getNumberSizeY() {
        return (canvasHeight / rowCount) - (getNumberPadding() / rowCount);
    }

    private float getNumberSizeX() {
        return (canvasWidth / colCount) - (getNumberPadding() / colCount);
    }

    private float getNumberPadding() {
        return 10;
    }

    private void renderNumbers(Canvas canvas) {
        for (int x = 0 ; x < rowCount ; x++) {
            for (int y = 0 ; y < colCount ; y++) {
                renderNumber(canvas, x, y);
            }
        }
    }

    private void renderNumber(Canvas canvas, int row, int col) {
        NumberSquare number  = numbers[row][col];
        float        offsetY = row * (numberSizeY + getNumberPadding());
        float        offsetX = col * (numberSizeX + getNumberPadding());

        canvas.drawRect(offsetX, offsetY, offsetX + numberSizeX, offsetY + numberSizeY, number.paint);
        if (number.value > 0)
            canvas.drawText(String.valueOf(number.value), offsetX, offsetY - fontMetrics.top, valuesPaint);
    }
}
