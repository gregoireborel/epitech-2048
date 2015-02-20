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
        public Paint paint;

        public NumberSquare() {
            paint = new Paint();
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

    private NumberSquare[][] numbers = new NumberSquare[4][4];

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
        valuesPaint.setColor(Color.BLUE);
        valuesPaint.setStrokeWidth(1);
        valuesPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void setNumberArray(int[][] array) {
        for (int x = 0 ; x < 4 ; x++) {
          for (int y = 0 ; y < 4 ; y++)
            numbers[x][y].setValue(array[x][y]);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        renderNumbers(canvas);
        super.onDraw(canvas);
    }

    private float getNumberSizeY() {
        return 10;
    }

    private float getNumberSizeX() {
        return 10;
    }

    private float getNumberPadding() {
        return 1;
    }

    private void renderNumbers(Canvas canvas) {
        int currentRow = 0;

        for (NumberSquare[] numberRow : numbers) {
            int   currentCol = 0;

            for (NumberSquare number : numberRow)
                renderNumber(canvas, number, currentRow, currentCol);
            currentRow++;
        }
    }

    private void renderNumber(Canvas canvas, NumberSquare number, int row, int col) {
        float offsetY = row * (getNumberSizeY() + getNumberPadding());
        float offsetX = col * (getNumberSizeX() + getNumberPadding());

        canvas.drawRect(offsetX, offsetY, getNumberSizeX(), getNumberSizeY(), number.paint);
        if (number.value > 0)
          canvas.drawText(String.valueOf(number.value), offsetX, offsetY, valuesPaint);
    }
}
