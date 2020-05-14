package views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import org.w3c.dom.Attr;

public class BoardView extends View {

    private static final int radius = 20;
    int[] h = {0,0,0,0,0,0};
    int[] v = {0,0,0,0,0,0};
    int[] val = {0,0,0,0,0,0,0,0,0};

    int flag1 = 1;

    float c1x, c4x, c7x, c1y, c2y, c3y;
    float c2x, c5x, c8x, c4y, c5y, c6y;
    float c3x, c6x, c9x, c7y, c8y, c9y;


    Paint paint = new Paint();
    public BoardView(Context context) {
        super(context);

        init(null);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public BoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {

        c1x = c4x = c7x = c1y = c2y = c3y = 200f;
        c2x = c5x = c8x = c4y = c5y = c6y = 400f;
        c3x = c6x = c9x = c7y = c8y = c9y = 600f;
    }    Paint paintLine = new Paint();



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.BLACK);

        if(flag1 % 2 == 1) {
            paintLine.setColor(Color.BLUE);
            flag1++;
        }
        else if (flag1 % 2 == 0) {
            paintLine.setColor(Color.BLACK);
            flag1++;
        }

        paintLine.setStrokeWidth(10);

        canvas.drawCircle(c1x,c1y,radius,paint);
        canvas.drawCircle(c2x,c2y,radius,paint);
        canvas.drawCircle(c3x,c3y,radius,paint);
        canvas.drawCircle(c4x,c4y,radius,paint);
        canvas.drawCircle(c5x,c5y,radius,paint);
        canvas.drawCircle(c6x,c6y,radius,paint);
        canvas.drawCircle(c7x,c7y,radius,paint);
        canvas.drawCircle(c8x,c8y,radius,paint);
        canvas.drawCircle(c9x,c9y,radius,paint);

        if(h[0] == 1)
            canvas.drawLine(c1x, c1y, c2x, c2y, paintLine);
        if(h[1] == 1)
            canvas.drawLine(c2x, c2y, c3x, c3y, paintLine);
        if(h[2] == 1)
            canvas.drawLine(c4x, c4y, c5x, c5y, paintLine);
        if(h[3] == 1)
            canvas.drawLine(c5x, c5y, c6x, c6y, paintLine);
        if(h[4] == 1)
            canvas.drawLine(c7x, c7y, c8x, c8y, paintLine);
        if(h[5] == 1)
            canvas.drawLine(c8x, c8y, c9x, c9y, paintLine);

        if(v[0] == 1)
            canvas.drawLine(c1x, c1y, c4x, c4y, paintLine);
        if(v[1] == 1)
            canvas.drawLine(c4x, c4y, c7x, c7y, paintLine);
        if(v[2] == 1)
            canvas.drawLine(c2x, c2y, c5x, c5y, paintLine);
        if(v[3] == 1)
            canvas.drawLine(c5x, c5y, c8x, c8y, paintLine);
        if(v[4] == 1)
            canvas.drawLine(c3x, c3y, c6x, c6y, paintLine);
        if(v[5] == 1)
            canvas.drawLine(c6x, c6y, c9x, c9y, paintLine);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN: {
                float x = event.getX();
                float y = event.getY();

                double dx1 = Math.pow(x - c1x, 2);
                double dy1 = Math.pow(y - c1y, 2);

                if(dx1 + dy1 < Math.pow(radius, 2)) {
                    val[0] = 1;
                }

                double dx2 = Math.pow(x - c2x, 2);
                double dy2 = Math.pow(y - c2y, 2);

                if(dx2 + dy2 < Math.pow(radius, 2)) {
                    val[1] = 1;
                }

                double dx3 = Math.pow(x - c3x, 2);
                double dy3 = Math.pow(y - c3y, 2);

                if(dx3 + dy3 < Math.pow(radius, 2)) {
                    val[2] = 1;
                }

                double dx4 = Math.pow(x - c4x, 2);
                double dy4 = Math.pow(y - c4y, 2);

                if(dx4 + dy4 < Math.pow(radius, 2)) {
                    val[3] = 1;
                }

                double dx5 = Math.pow(x - c5x, 2);
                double dy5 = Math.pow(y - c5y, 2);

                if(dx5 + dy5 < Math.pow(radius, 2)) {
                    val[4] = 1;
                }

                double dx6 = Math.pow(x - c6x, 2);
                double dy6 = Math.pow(y - c6y, 2);

                if(dx6 + dy6 < Math.pow(radius, 2)) {
                    val[5] = 1;
                }

                double dx7 = Math.pow(x - c7x, 2);
                double dy7 = Math.pow(y - c7y, 2);

                if(dx7 + dy7 < Math.pow(radius, 2)) {
                    val[6] = 1;
                }

                double dx8 = Math.pow(x - c8x, 2);
                double dy8 = Math.pow(y - c8y, 2);

                if(dx8 + dy8 < Math.pow(radius, 2)) {
                    val[7] = 1;
                }

                double dx9 = Math.pow(x - c9x, 2);
                double dy9 = Math.pow(y - c9y, 2);

                if(dx9 + dy9 < Math.pow(radius, 2)) {
                    val[8] = 1;
                }

                return true;
            }

            case MotionEvent.ACTION_MOVE: {
                float x = event.getX();
                float y = event.getY();

                //double dx = Math.pow(x - 100, 2);
                //double dy = Math.pow(y - 100, 2);

                if(val[0] == 1) {
                    double dx2 = Math.pow(x - c2x, 2);
                    double dy2 = Math.pow(y - c2y, 2);
                    double dx4 = Math.pow(x - c4x, 2);
                    double dy4 = Math.pow(y - c4y, 2);

                    if(dx2 + dy2 < Math.pow(radius, 2)) {
                        h[0] = 1;
                        postInvalidate();
                        val[0] = 0;
                    }
                    if(dx4 + dy4 < Math.pow(radius, 2)) {
                        v[0] = 1;
                        postInvalidate();
                        val[0] = 0;
                    }
                }

                if(val[1] == 1) {
                    double dx1 = Math.pow(x - c1x, 2);
                    double dy1 = Math.pow(y - c1y, 2);
                    double dx3 = Math.pow(x - c3x, 2);
                    double dy3 = Math.pow(y - c3y, 2);
                    double dx5 = Math.pow(x - c5x, 2);
                    double dy5 = Math.pow(y - c5y, 2);

                    if(dx1 + dy1 < Math.pow(radius, 2)) {
                        h[0] = 1;
                        postInvalidate();
                        val[1] = 0;
                    }
                    else if(dx3 + dy3 < Math.pow(radius, 2)) {
                        h[1] = 1;
                        postInvalidate();
                        val[1] = 0;
                    }
                    else if(dx5 + dy5 < Math.pow(radius, 2)) {
                        v[2] = 1;
                        postInvalidate();
                        val[1] = 0;
                    }
                }

                if(val[2] == 1) {
                    double dx2 = Math.pow(x - c2x, 2);
                    double dy2 = Math.pow(y - c2y, 2);
                    double dx6 = Math.pow(x - c6x, 2);
                    double dy6 = Math.pow(y - c6y, 2);

                    if(dx2 + dy2 < Math.pow(radius, 2)) {
                        h[1] = 1;
                        postInvalidate();
                        val[2] = 0;
                    }
                    else if(dx6 + dy6 < Math.pow(radius, 2)) {
                        v[4] = 1;
                        postInvalidate();
                        val[2] = 0;
                    }
                }

                if(val[3] == 1) {
                    double dx1 = Math.pow(x - c1x, 2);
                    double dy1 = Math.pow(y - c1y, 2);
                    double dx7 = Math.pow(x - c7x, 2);
                    double dy7 = Math.pow(y - c7y, 2);
                    double dx5 = Math.pow(x - c5x, 2);
                    double dy5 = Math.pow(y - c5y, 2);

                    if(dx1 + dy1 < Math.pow(radius, 2)) {
                        v[0] = 1;
                        postInvalidate();
                        val[3] = 0;
                    }
                    else if(dx7 + dy7 < Math.pow(radius, 2)) {
                        v[1] = 1;
                        postInvalidate();
                        val[3] = 0;
                    }
                    else if(dx5 + dy5 < Math.pow(radius, 2)) {
                        h[2] = 1;
                        postInvalidate();
                        val[3] = 0;
                    }
                }

                if(val[4] == 1) {
                    double dx2 = Math.pow(x - c2x, 2);
                    double dy2 = Math.pow(y - c2y, 2);
                    double dx4 = Math.pow(x - c4x, 2);
                    double dy4 = Math.pow(y - c4y, 2);
                    double dx6 = Math.pow(x - c6x, 2);
                    double dy6 = Math.pow(y - c6y, 2);
                    double dx8 = Math.pow(x - c8x, 2);
                    double dy8 = Math.pow(y - c8y, 2);

                    if(dx2 + dy2 < Math.pow(radius, 2)) {
                        v[2] = 1;
                        postInvalidate();
                        val[4] = 0;
                    }
                    else if(dx4 + dy4 < Math.pow(radius, 2)) {
                        h[2] = 1;
                        postInvalidate();
                        val[4] = 0;
                    }
                    else if(dx6 + dy6 < Math.pow(radius, 2)) {
                        h[3] = 1;
                        postInvalidate();
                        val[4] = 0;
                    }
                    else if(dx8 + dy8 < Math.pow(radius, 2)) {
                        v[3] = 1;
                        postInvalidate();
                        val[4] = 0;
                    }
                }

                if(val[5] == 1) {
                    double dx3 = Math.pow(x - c3x, 2);
                    double dy3 = Math.pow(y - c3y, 2);
                    double dx9 = Math.pow(x - c9x, 2);
                    double dy9 = Math.pow(y - c9y, 2);
                    double dx5 = Math.pow(x - c5x, 2);
                    double dy5 = Math.pow(y - c5y, 2);

                    if(dx3 + dy3 < Math.pow(radius, 2)) {
                        v[4] = 1;
                        postInvalidate();
                        val[4] = 0;
                    }
                    else if(dx9 + dy9 < Math.pow(radius, 2)) {
                        v[5] = 1;
                        postInvalidate();
                        val[4] = 0;
                    }
                    else if(dx5 + dy5 < Math.pow(radius, 2)) {
                        h[3] = 1;
                        postInvalidate();
                        val[4] = 0;
                    }
                }

                if(val[6] == 1) {
                    double dx4 = Math.pow(x - c4x, 2);
                    double dy4 = Math.pow(y - c4y, 2);
                    double dx8 = Math.pow(x - c8x, 2);
                    double dy8 = Math.pow(y - c8y, 2);

                    if(dx4 + dy4 < Math.pow(radius, 2)) {
                        v[1] = 1;
                        postInvalidate();
                        val[6] = 0;
                    }
                    else if(dx8 + dy8 < Math.pow(radius, 2)) {
                        h[4] = 1;
                        postInvalidate();
                        val[6] = 0;
                    }
                }

                if(val[7] == 1) {
                    double dx7 = Math.pow(x - c7x, 2);
                    double dy7 = Math.pow(y - c7y, 2);
                    double dx9 = Math.pow(x - c9x, 2);
                    double dy9 = Math.pow(y - c9y, 2);
                    double dx5 = Math.pow(x - c5x, 2);
                    double dy5 = Math.pow(y - c5y, 2);

                    if(dx7 + dy7 < Math.pow(radius, 2)) {
                        h[4] = 1;
                        postInvalidate();
                        val[7] = 0;
                    }
                    else if(dx9 + dy9 < Math.pow(radius, 2)) {
                        h[5] = 1;
                        postInvalidate();
                        val[7] = 0;
                    }
                    else if(dx5 + dy5 < Math.pow(radius, 2)) {
                        v[3] = 1;
                        postInvalidate();
                        val[7] = 0;
                    }
                }

                if(val[8] == 1) {
                    double dx6 = Math.pow(x - c6x, 2);
                    double dy6 = Math.pow(y - c6y, 2);
                    double dx8 = Math.pow(x - c8x, 2);
                    double dy8 = Math.pow(y - c8y, 2);

                    if(dx6 + dy6 < Math.pow(radius, 2)) {
                        v[5] = 1;
                        postInvalidate();
                        val[8] = 0;
                    }
                    else if(dx8 + dy8 < Math.pow(radius, 2)) {
                        h[5] = 1;
                        postInvalidate();
                        val[8] = 0;
                    }
                }

                return true;
            }

            case MotionEvent.ACTION_UP: {
                val[0] = val[1] = val[2] = val[3] = val[4] = val[5] = val[6] = val[7] = val[8] = 0;
            }
        }

        return value;
    }

    void swapPlayer() {
        if(paintLine.getColor() == Color.BLUE)
            paintLine.setColor(Color.BLACK);
        //if(paintLine.getColor() == Color.BLACK)
          //  paintLine.setColor(Color.BLUE);
    }
}
