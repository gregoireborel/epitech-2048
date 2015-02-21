package epitech_2048.epitech_2048;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class GameView extends View {
    private Paint valuesPaint = new Paint();

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
            paint.setColor(Color.BLUE);
        }
    }

    private final int rowCount = 4;
    private final int colCount = 4;
    private NumberSquare[][] numbers = new NumberSquare[rowCount][colCount];
    private int   canvasWidth  = 100;
    private int   canvasHeight = 100;
    private float numberSizeX  = 10;
    private float numberSizeY  = 10;

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
        valuesPaint.setColor(Color.WHITE);
        valuesPaint.setStrokeWidth(1);
        valuesPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        for (int x = 0 ; x < rowCount ; x++) {
            for (int y = 0 ; y < colCount ; y++)
                numbers[x][y] = new NumberSquare();
        }
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
        System.out.println("Canvas size changed");
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

        System.out.println("Render ["+ col +"][" + row + "] at (" + offsetX + "," + offsetY + ")");
        canvas.drawRect(offsetX, offsetY, offsetX + numberSizeX, offsetY + numberSizeY, number.paint);
        //if (number.value > 0) {
            System.out.println("Rendering text: " + String.valueOf(number.value));
            canvas.drawText(String.valueOf(number.value), offsetX, offsetY, valuesPaint);
        //}
    }
}
