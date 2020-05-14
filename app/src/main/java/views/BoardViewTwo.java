package views;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.dotsandboxes.MainActivity;

import java.util.Random;

import static android.content.Context.VIBRATOR_SERVICE;

public class BoardViewTwo extends View {

    public BoardViewTwo(Context context) {
        super(context);

        init(null);
    }

    public BoardViewTwo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public BoardViewTwo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    public BoardViewTwo(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {

        radius = 30;

        //maxi = Math.max(noOfColumns, noOfRows);

        //spacing = (maxi == 3) ? 260 : ((maxi == 4) ? 195 : 156) ;
        //spacing = 200;

        X=Y=0;

        turn = 1;

        for(int i = 0 ; i < 40 ; i++)
            paintLines[i] = new Paint();
        for(int i = 0; i<40;i++)
            paintLines[i].setStrokeWidth(10);

        for(int i = 0 ; i < 16 ; i++)
            paintRects[i] = new Paint();


        rect = new Rect();

        two = 2;

        newRect = false;

        p1 = 0;
        p2 = 0;
        p3 = 0;

        pundo = 0;

        flag = 1;

        rand = new Random();

        postInvalidate();

        for(int i = 0; i < 20 ; i++) {
            h[i] = 0;
            v[i] = 0;
        }
        for(int i = 0; i < 25 ; i++)
            val[i] = 0;
        for(int i = 0 ; i < 16 ; i++) {
            rectDrawn[i] = 0;
            lastRectDrawn[i] = 0;
        }
    }

    public static int noOfRows;
    public static int noOfColumns;

    Paint paint = new Paint();
    Paint paintLine = new Paint();
    Paint[] paintLines = new Paint[40];                     //first h then v
    Paint[] paintRects = new Paint[16];


    static int turn;

    public static int noOfPlayers;

    static int p1;
    static int p2;
    static int p3;

    public static int difficulty;

    Random rand;

    static int pundo;

    public int maxi;
    public int spacing;
    private final int SPACING1 = 100;
    int radius;
    Rect rect;
    boolean newRect;

    int two;

    float X;
    float Y;

    int flag;

    int[] val = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    static int[] h = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    //static int[] h = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    static int[] v = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    //static int[] v = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    static int[] rectDrawn = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    static int[] lastRectDrawn = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};                 //for undo purposes

    static int[] undo = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};      //h first then v
    static int[] aiundo = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(Color.BLACK);

        paintLine.setColor(Color.BLACK);
        paintLine.setStrokeWidth(10);


        for(int a=1; a<=noOfRows ; a++) {
            for(int b=1; b<=noOfColumns ; b++) {
                canvas.drawCircle(b*spacing,a*spacing,radius,paint);
            }
        }

        if(noOfPlayers == 1) {
            MainActivity.undo.setVisibility(INVISIBLE);
            MainActivity.p3.setVisibility(INVISIBLE);
            MainActivity.p1.setText("| - Player");
            MainActivity.p2.setText("| - AI");
        }
        else if(noOfPlayers == 2) {
            MainActivity.p3.setVisibility(INVISIBLE);
        }

        for(int i = 0 ; i < 20 ; i++) {
            if(h[i] == 1) {
                if( (i+1)%(noOfColumns-1) == 0) {
                    int rowNum = (i+1)/(noOfColumns-1);
                    canvas.drawLine((noOfColumns-1)*spacing,rowNum*spacing,(noOfColumns)*spacing,rowNum*spacing,paintLines[i]);
                }
                else {
                    int rowNum = ((i+1)/(noOfColumns-1))+1;
                    int colNum = ((i+1)%(noOfColumns-1));
                    canvas.drawLine(colNum*spacing,rowNum*spacing,(colNum+1)*spacing,rowNum*spacing,paintLines[i]);
                }
            }
            if(v[i] == 1) {
                if( (i+1)%(noOfRows-1) == 0) {
                    int colNum = (i+1)/(noOfRows-1);
                    canvas.drawLine(colNum*spacing,(noOfRows-1)*spacing,colNum*spacing,noOfRows*spacing,paintLines[20+i]);
                }
                else {
                    int colNum = ((i+1)/(noOfRows-1))+1;
                    int rowNum = ((i+1)%(noOfRows-1));
                    canvas.drawLine(colNum*spacing,rowNum*spacing,(colNum)*spacing,(rowNum+1)*spacing,paintLines[20+i]);
                }
            }
        }

        two = 2;
        newRect = false;
        for(int i = 0, j = 0 ; i < (noOfColumns-1) ; i++ , j += noOfRows-1 ) {
            if( h[i] == 1 && h[i+noOfColumns-1] == 1 && v[j] == 1 && v[j+noOfRows-1] == 1) {
                rect.top = spacing + spacing/10;
                rect.left = (i+1)*spacing + spacing/10;           // i = i-(c-1)*(two-2)
                rect.bottom = rect.top + spacing*8/10;
                rect.right = rect.left + spacing*8/10;

                if(noOfPlayers == 2 || noOfPlayers == 1) {
                    if (turn % 2 == 0 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.BLACK);
                        newRect = true;
                        rectDrawn[i] = 1;
                        lastRectDrawn[i] = 1;
                        pundo = 1;
                        p1++;
                    } else if (turn % 2 == 1 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.BLUE);
                        newRect = true;
                        rectDrawn[i] = 1;
                        lastRectDrawn[i] = 1;
                        pundo = 2;
                        p2++;
                    }
                }
                else if(noOfPlayers == 3) {
                    if ((turn -2) % 3 == 0 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.BLACK);
                        newRect = true;
                        rectDrawn[i] = 1;
                        lastRectDrawn[i] = 1;pundo = 1;
                        p1++;
                    } else if ((turn -2) % 3 == 1 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.BLUE);
                        newRect = true;
                        rectDrawn[i] = 1;
                        lastRectDrawn[i] = 1;pundo = 2;
                        p2++;
                    } else if ((turn -2) % 3 == 2 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.RED);
                        newRect = true;
                        rectDrawn[i] = 1;
                        lastRectDrawn[i] = 1;pundo = 3;
                        p3++;
                    }
                }

                canvas.drawRect(rect,paintRects[i]);

            }
        }
        for(int i = noOfColumns-1 , j = 1 ; i < 2*(noOfColumns-1) ; i++ , j += noOfRows-1 ) {
            if( h[i] == 1 && h[i+noOfColumns-1] == 1 && v[j] == 1 && v[j+noOfRows-1] == 1) {
                rect.top = 2*spacing + spacing/10;
                rect.left = (i-(noOfColumns-1)+1)*spacing + spacing/10;
                rect.bottom = rect.top + spacing*8/10;
                rect.right = rect.left + spacing*8/10;

                if(noOfPlayers == 2 || noOfPlayers == 1) {
                    if (turn % 2 == 0 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.BLACK);
                        newRect = true;
                        rectDrawn[i] = 1;lastRectDrawn[i] = 1;pundo = 1;
                        p1++;
                    } else if (turn % 2 == 1 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.BLUE);
                        newRect = true;
                        rectDrawn[i] = 1;lastRectDrawn[i] = 1;pundo = 2;
                        p2++;
                    }
                }
                else if(noOfPlayers == 3) {
                    if ((turn -2) % 3 == 0 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.BLACK);
                        newRect = true;
                        rectDrawn[i] = 1;lastRectDrawn[i] = 1;pundo = 1;
                        p1++;
                    } else if ((turn -2) % 3 == 1 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.BLUE);
                        newRect = true;
                        rectDrawn[i] = 1;lastRectDrawn[i] = 1;pundo = 2;
                        p2++;
                    } else if ((turn -2) % 3 == 2 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.RED);
                        newRect = true;
                        rectDrawn[i] = 1;lastRectDrawn[i] = 1;pundo = 3;
                        p3++;
                    }
                }

                canvas.drawRect(rect,paintRects[i]);
            }
        }
        for(int i = 2*(noOfColumns-1) , j = 2 ; i < 3*(noOfColumns-1) ; i++ , j += noOfRows-1 ) {
            if( h[i] == 1 && h[i+noOfColumns-1] == 1 && v[j] == 1 && v[j+noOfRows-1] == 1) {
                rect.top = 3*spacing + spacing/10;
                rect.left = (i-(2*(noOfColumns-1))+1)*spacing + spacing/10;
                rect.bottom = rect.top + spacing*8/10;
                rect.right = rect.left + spacing*8/10;

                if(noOfPlayers == 2 || noOfPlayers == 1) {
                    if (turn % 2 == 0 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.BLACK);
                        newRect = true;
                        rectDrawn[i] = 1;lastRectDrawn[i] = 1;pundo = 1;
                        p1++;
                    } else if (turn % 2 == 1 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.BLUE);
                        newRect = true;
                        rectDrawn[i] = 1;lastRectDrawn[i] = 1;pundo = 2;
                        p2++;
                    }
                }
                else if(noOfPlayers == 3) {
                    if ((turn -2) % 3 == 0 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.BLACK);
                        newRect = true;
                        rectDrawn[i] = 1;lastRectDrawn[i] = 1;pundo = 1;
                        p1++;
                    } else if ((turn -2) % 3 == 1 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.BLUE);
                        newRect = true;
                        rectDrawn[i] = 1;lastRectDrawn[i] = 1;pundo = 2;
                        p2++;
                    } else if ((turn -2) % 3 == 2 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.RED);
                        newRect = true;
                        rectDrawn[i] = 1;lastRectDrawn[i] = 1;pundo = 3;
                        p3++;
                    }
                }

                canvas.drawRect(rect,paintRects[i]);
            }
        }
        for(int i = 3*(noOfColumns-1) , j = 3 ; i < 4*(noOfColumns-1) ; i++ , j += noOfRows-1 ) {
            if( h[i] == 1 && h[i+noOfColumns-1] == 1 && v[j] == 1 && v[j+noOfRows-1] == 1) {
                rect.top = 4*spacing + spacing/10;
                rect.left = (i-(3*(noOfColumns-1))+1)*spacing + spacing/10;
                rect.bottom = rect.top + spacing*8/10;
                rect.right = rect.left + spacing*8/10;

                if(noOfPlayers == 2 || noOfPlayers == 1) {
                    if (turn % 2 == 0 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.BLACK);
                        newRect = true;
                        rectDrawn[i] = 1;lastRectDrawn[i] = 1;pundo = 1;
                        p1++;
                    } else if (turn % 2 == 1 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.BLUE);
                        newRect = true;
                        rectDrawn[i] = 1;lastRectDrawn[i] = 1;pundo = 2;
                        p2++;
                    }
                }
                else if(noOfPlayers == 3) {
                    if ((turn -2) % 3 == 0 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.BLACK);
                        newRect = true;
                        rectDrawn[i] = 1;lastRectDrawn[i] = 1;pundo = 1;
                        p1++;
                    } else if ((turn -2) % 3 == 1 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.BLUE);
                        newRect = true;
                        rectDrawn[i] = 1;lastRectDrawn[i] = 1;pundo = 2;
                        p2++;
                    } else if ((turn -2) % 3 == 2 && rectDrawn[i] == 0) {
                        paintRects[i].setColor(Color.RED);
                        newRect = true;
                        rectDrawn[i] = 1;lastRectDrawn[i] = 1;pundo = 3;
                        p3++;
                    }
                }

                canvas.drawRect(rect,paintRects[i]);
            }
        }
        if(newRect == true) {
            turn--;
            MainActivity.soundPool.play(MainActivity.sound1, 1, 1, 0, 0, 1);
        }
        else {
            for (int i = 0; i < 16; i++)
                lastRectDrawn[i] = 0;
            pundo = 0;
        }

        flag = 1;
        for( int x = 0 ; x < (noOfColumns-1)*(noOfRows-1) ; x++ ) {
            if( rectDrawn[x] == 0) {
                flag = 0;
                break;
            }
        }

        if(flag == 1) {
            if( p1 > p2 && p1 > p3)
                if(noOfPlayers == 1)
                    MainActivity.res.setText("You win!!");
                else
                    MainActivity.res.setText("Player 1 wins!!");
            else if ( p2 > p1 && p2 > p3)
                if(noOfPlayers == 1)
                    MainActivity.res.setText("You lose");
                else
                    MainActivity.res.setText("Player 2 wins!!");
            else if( p3 > p1 && p3 > p2)
                MainActivity.res.setText("Player 3 wins!!");
            else
                MainActivity.res.setText("It's a draw!!");
            MainActivity.undo.setVisibility(INVISIBLE);
        }

        if(noOfPlayers == 1 && turn % 2 == 0 && flag ==0) {
            callAI();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);

        switch (event.getAction()) {


            case MotionEvent.ACTION_DOWN : {
                float x = event.getX();
                float y = event.getY();
                X=x;
                Y=y;

                for (int i = 1 ; i <= noOfRows ; i++) {
                    for (int j = 1 ; j <= noOfColumns ; j++) {
                        double dx = Math.pow(x - j*spacing, 2);
                        double dy = Math.pow(y - i*spacing, 2);

                        if(dx + dy < Math.pow(radius, 2)) {
                            val[(i-1)*noOfColumns+j - 1] = 1;
                        }
                    }
                }

                return true;
            }

            case MotionEvent.ACTION_MOVE: {
                float x = event.getX();
                float y = event.getY();

                for(int k = 0  ; k < noOfColumns*noOfRows ; k++) {
                    if (val[k] == 1) {
                        if (k == 0) {                                       //Top Left Corner Point
                            double dx1 = Math.pow(x - 2*spacing, 2);
                            double dy1 = Math.pow(y - spacing , 2);
                            double dx2 = Math.pow(x - spacing , 2);
                            double dy2 = Math.pow(y - 2*spacing , 2);

                            if(dx1 + dy1 < Math.pow(radius, 2) && h[0] == 0) {
                                h[0] = 1;
                                MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[0] = 1;
                                //postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[0].setColor(Color.BLACK);
                                    else
                                        paintLines[0].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[0].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[0].setColor(Color.BLUE);
                                    else
                                        paintLines[0].setColor(Color.RED);
                                }
                                turn++;
                                postInvalidate();
                            }
                            if(dx2 + dy2 < Math.pow(radius, 2) && v[0]==0) {
                                v[0] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[20] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[20].setColor(Color.BLACK);
                                    else
                                        paintLines[20].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[20].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[20].setColor(Color.BLUE);
                                    else
                                        paintLines[20].setColor(Color.RED);
                                }
                                turn++;
                            }

                        }

                        else if (k+1 < noOfColumns) {                          //Top Edge Point
                            double dx1 = Math.pow(x - (k)*spacing , 2);
                            double dy1 = Math.pow(y - spacing , 2);
                            double dx2 = Math.pow(x - (k+1)*spacing, 2);
                            double dy2 = Math.pow(y - 2*spacing , 2);
                            double dx3 = Math.pow(x - (k+2)*spacing, 2);
                            double dy3 = Math.pow(y - spacing , 2);

                            if(dx1 + dy1 < Math.pow(radius, 2) && h[k-1] == 0) {
                                h[k-1] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[k-1] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[k-1].setColor(Color.BLACK);
                                    else
                                        paintLines[k-1].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[k-1].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[k-1].setColor(Color.BLUE);
                                    else
                                        paintLines[k-1].setColor(Color.RED);
                                }
                                turn++;
                            }
                            if(dx2 + dy2 < Math.pow(radius, 2) && v[(k)*(noOfRows-1)] == 0) {
                                v[(k)*(noOfRows-1)] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[(k)*(noOfRows-1)+20] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[(k)*(noOfRows-1)+20].setColor(Color.BLACK);
                                    else
                                        paintLines[(k)*(noOfRows-1)+20].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[(k)*(noOfRows-1)+20].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[(k)*(noOfRows-1)+20].setColor(Color.BLUE);
                                    else
                                        paintLines[(k)*(noOfRows-1)+20].setColor(Color.RED);
                                }
                                turn++;
                            }
                            if(dx3 + dy3 < Math.pow(radius, 2) && h[k] == 0) {
                                h[k] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[k] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[k].setColor(Color.BLACK);
                                    else
                                        paintLines[k].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[k].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[k].setColor(Color.BLUE);
                                    else
                                        paintLines[k].setColor(Color.RED);
                                }
                                turn++;
                            }
                        }

                        else if (k+1 == noOfColumns) {                        //Top Right Corner Point
                            double dx1 = Math.pow(x - (k)*spacing , 2);
                            double dy1 = Math.pow(y - spacing , 2);
                            double dx2 = Math.pow(x - (k+1)*spacing, 2);
                            double dy2 = Math.pow(y - 2*spacing , 2);

                            if(dx1 + dy1 < Math.pow(radius, 2) && h[k-1] == 0) {
                                h[k-1] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[k-1] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[k-1].setColor(Color.BLACK);
                                    else
                                        paintLines[k-1].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[k-1].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[k-1].setColor(Color.BLUE);
                                    else
                                        paintLines[k-1].setColor(Color.RED);
                                }
                                turn++;
                            }
                            if(dx2 + dy2 < Math.pow(radius, 2) && v[(k)*(noOfRows-1)] == 0) {
                                v[(k)*(noOfRows-1)] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[(k)*(noOfRows-1)+20] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[(k)*(noOfRows-1)+20].setColor(Color.BLACK);
                                    else
                                        paintLines[(k)*(noOfRows-1)+20].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[(k)*(noOfRows-1)+20].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[(k)*(noOfRows-1)+20].setColor(Color.BLUE);
                                    else
                                        paintLines[(k)*(noOfRows-1)+20].setColor(Color.RED);
                                }
                                turn++;
                            }
                        }

                        else if (k+1 == noOfColumns*(noOfRows-1) + 1) {        //Bottom Left Corner Point
                            double dx1 = Math.pow(x - 2*spacing , 2);
                            double dy1 = Math.pow(y - noOfRows*spacing , 2);
                            double dx2 = Math.pow(x - spacing, 2);
                            double dy2 = Math.pow(y - (noOfRows-1)*spacing , 2);

                            if(dx1 + dy1 < Math.pow(radius, 2) && h[(noOfColumns-1)*(noOfRows-1)] == 0) {
                                h[(noOfColumns-1)*(noOfRows-1)] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[(noOfColumns-1)*(noOfRows-1)] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[(noOfColumns-1)*(noOfRows-1)].setColor(Color.BLACK);
                                    else
                                        paintLines[(noOfColumns-1)*(noOfRows-1)].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[(noOfColumns-1)*(noOfRows-1)].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[(noOfColumns-1)*(noOfRows-1)].setColor(Color.BLUE);
                                    else
                                        paintLines[(noOfColumns-1)*(noOfRows-1)].setColor(Color.RED);
                                }
                                turn++;
                            }
                            if(dx2 + dy2 < Math.pow(radius, 2) && v[noOfRows-2] == 0) {
                                v[noOfRows-2] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[noOfRows-2+20] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[noOfRows-2+20].setColor(Color.BLACK);
                                    else
                                        paintLines[noOfRows-2+20].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[noOfRows-2+20].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[noOfRows-2+20].setColor(Color.BLUE);
                                    else
                                        paintLines[noOfRows-2+20].setColor(Color.RED);
                                }
                                turn++;
                            }
                        }

                        else if ( (k+1)%noOfColumns == 1) {      //Left Edge Point
                            double dx1 = Math.pow(x - spacing , 2);
                            double dy1 = Math.pow(y - (((k+1)/noOfColumns)+2)*spacing , 2);
                            double dx2 = Math.pow(x - 2*spacing, 2);
                            double dy2 = Math.pow(y - (((k+1)/noOfColumns)+1)*spacing , 2);
                            double dx3 = Math.pow(x - spacing, 2);
                            double dy3 = Math.pow(y - (((k+1)/noOfColumns))*spacing , 2);

                            if(dx1 + dy1 < Math.pow(radius, 2) && v[(k+1)/noOfColumns] == 0) {
                                v[(k+1)/noOfColumns] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[(k+1)/noOfColumns+20] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[(k+1)/noOfColumns+20].setColor(Color.BLACK);
                                    else
                                        paintLines[(k+1)/noOfColumns+20].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[(k+1)/noOfColumns+20].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[(k+1)/noOfColumns+20].setColor(Color.BLUE);
                                    else
                                        paintLines[(k+1)/noOfColumns+20].setColor(Color.RED);
                                }
                                turn++;
                            }
                            if(dx2 + dy2 < Math.pow(radius, 2) && h[(noOfColumns-1)*((k+1)/noOfColumns)] == 0) {
                                h[(noOfColumns-1)*((k+1)/noOfColumns)] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[(noOfColumns-1)*((k+1)/noOfColumns)] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)].setColor(Color.BLACK);
                                    else
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)].setColor(Color.BLUE);
                                    else
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)].setColor(Color.RED);
                                }
                                turn++;
                            }
                            if(dx3 + dy3 < Math.pow(radius, 2) && v[((k+1)/noOfColumns)-1] == 0) {
                                v[((k+1)/noOfColumns)-1] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[((k+1)/noOfColumns)-1+20] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[((k+1)/noOfColumns)-1+20].setColor(Color.BLACK);
                                    else
                                        paintLines[((k+1)/noOfColumns)-1+20].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[((k+1)/noOfColumns)-1+20].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[((k+1)/noOfColumns)-1+20].setColor(Color.BLUE);
                                    else
                                        paintLines[((k+1)/noOfColumns)-1+20].setColor(Color.RED);
                                }
                                turn++;
                            }
                        }

                        else if (k+1 == noOfColumns*noOfRows) {        //Bottom Right Corner Point
                            double dx1 = Math.pow(x - (noOfColumns-1)*spacing , 2);
                            double dy1 = Math.pow(y - noOfRows*spacing , 2);
                            double dx2 = Math.pow(x - noOfColumns*spacing, 2);
                            double dy2 = Math.pow(y - (noOfRows-1)*spacing , 2);

                            if(dx1 + dy1 < Math.pow(radius, 2) && h[noOfRows*(noOfColumns-1)-1] == 0) {
                                h[noOfRows*(noOfColumns-1)-1] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[noOfRows*(noOfColumns-1)-1] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[noOfRows*(noOfColumns-1)-1].setColor(Color.BLACK);
                                    else
                                        paintLines[noOfRows*(noOfColumns-1)-1].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[noOfRows*(noOfColumns-1)-1].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[noOfRows*(noOfColumns-1)-1].setColor(Color.BLUE);
                                    else
                                        paintLines[noOfRows*(noOfColumns-1)-1].setColor(Color.RED);
                                }
                                turn++;
                            }
                            if(dx2 + dy2 < Math.pow(radius, 2) && v[(noOfRows-1)*(noOfColumns)-1] == 0) {
                                v[(noOfRows-1)*(noOfColumns)-1] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[(noOfRows-1)*(noOfColumns)-1+20] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[(noOfRows-1)*(noOfColumns)-1+20].setColor(Color.BLACK);
                                    else
                                        paintLines[(noOfRows-1)*(noOfColumns)-1+20].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[(noOfRows-1)*(noOfColumns)-1+20].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[(noOfRows-1)*(noOfColumns)-1+20].setColor(Color.BLUE);
                                    else
                                        paintLines[(noOfRows-1)*(noOfColumns)-1+20].setColor(Color.RED);
                                }
                                turn++;
                            }
                        }

                        else if ( (k+1) > (noOfColumns*(noOfRows-1)) ) {                 //Bottom Edge Point
                            double dx1 = Math.pow(x - ((k+1)%noOfColumns+1)*spacing , 2);
                            double dy1 = Math.pow(y - noOfRows*spacing , 2);
                            double dx2 = Math.pow(x - ((k+1)%noOfColumns)*spacing, 2);
                            double dy2 = Math.pow(y - (noOfRows-1)*spacing , 2);
                            double dx3 = Math.pow(x - ((k+1)%noOfColumns-1)*spacing, 2);
                            double dy3 = Math.pow(y - noOfRows*spacing , 2);

                            if(dx1 + dy1 < Math.pow(radius, 2) && h[(k+1)-(noOfRows-1)-1] == 0) {
                                h[(k+1)-(noOfRows-1)-1] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[(k+1)-(noOfRows-1)-1] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[(k+1)-(noOfRows-1)-1].setColor(Color.BLACK);
                                    else
                                        paintLines[(k+1)-(noOfRows-1)-1].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[(k+1)-(noOfRows-1)-1].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[(k+1)-(noOfRows-1)-1].setColor(Color.BLUE);
                                    else
                                        paintLines[(k+1)-(noOfRows-1)-1].setColor(Color.RED);
                                }
                                turn++;
                            }
                            if(dx2 + dy2 < Math.pow(radius, 2) && v[(noOfRows-1)*((k+1)%noOfColumns)-1] == 0) {
                                v[(noOfRows-1)*((k+1)%noOfColumns)-1] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[(noOfRows-1)*((k+1)%noOfColumns)-1+20] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[(noOfRows-1)*((k+1)%noOfColumns)-1+20].setColor(Color.BLACK);
                                    else
                                        paintLines[(noOfRows-1)*((k+1)%noOfColumns)-1+20].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[(noOfRows-1)*((k+1)%noOfColumns)-1+20].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[(noOfRows-1)*((k+1)%noOfColumns)-1+20].setColor(Color.BLUE);
                                    else
                                        paintLines[(noOfRows-1)*((k+1)%noOfColumns)-1+20].setColor(Color.RED);
                                }
                                turn++;
                            }
                            if(dx3 + dy3 < Math.pow(radius, 2) && h[(k+1)-(noOfRows-1)-2] == 0) {
                                h[(k+1)-(noOfRows-1)-2] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[(k+1)-(noOfRows-1)-2] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[(k+1)-(noOfRows-1)-2].setColor(Color.BLACK);
                                    else
                                        paintLines[(k+1)-(noOfRows-1)-2].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[(k+1)-(noOfRows-1)-2].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[(k+1)-(noOfRows-1)-2].setColor(Color.BLUE);
                                    else
                                        paintLines[(k+1)-(noOfRows-1)-2].setColor(Color.RED);
                                }
                                turn++;
                            }
                        }

                        else if ( (k+1) % noOfColumns == 0 ) {                 //Right Edge Point
                            double dx1 = Math.pow(x - noOfColumns*spacing , 2);
                            double dy1 = Math.pow(y - ((k+1)/noOfColumns-1)*spacing , 2);
                            double dx2 = Math.pow(x - (noOfColumns-1)*spacing, 2);
                            double dy2 = Math.pow(y - ((k+1)/noOfColumns)*spacing , 2);
                            double dx3 = Math.pow(x - noOfColumns*spacing, 2);
                            double dy3 = Math.pow(y - ((k+1)/noOfColumns+1)*spacing , 2);

                            if(dx1 + dy1 < Math.pow(radius, 2) && v[(noOfRows-1)*(noOfColumns-1)+((k+1)/noOfColumns)-2] == 0) {
                                v[(noOfRows-1)*(noOfColumns-1)+((k+1)/noOfColumns)-2] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[(noOfRows-1)*(noOfColumns-1)+((k+1)/noOfColumns)-2+20] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[(noOfRows-1)*(noOfColumns-1)+((k+1)/noOfColumns)-2+20].setColor(Color.BLACK);
                                    else
                                        paintLines[(noOfRows-1)*(noOfColumns-1)+((k+1)/noOfColumns)-2+20].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[(noOfRows-1)*(noOfColumns-1)+((k+1)/noOfColumns)-2+20].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[(noOfRows-1)*(noOfColumns-1)+((k+1)/noOfColumns)-2+20].setColor(Color.BLUE);
                                    else
                                        paintLines[(noOfRows-1)*(noOfColumns-1)+((k+1)/noOfColumns)-2+20].setColor(Color.RED);
                                }
                                turn++;
                            }
                            if(dx2 + dy2 < Math.pow(radius, 2) && h[(noOfColumns-1)*((k+1)/noOfColumns)-1] == 0) {
                                h[(noOfColumns-1)*((k+1)/noOfColumns)-1] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[(noOfColumns-1)*((k+1)/noOfColumns)-1] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)-1].setColor(Color.BLACK);
                                    else
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)-1].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)-1].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)-1].setColor(Color.BLUE);
                                    else
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)-1].setColor(Color.RED);
                                }
                                turn++;
                            }
                            if(dx3 + dy3 < Math.pow(radius, 2) && v[(noOfRows-1)*(noOfColumns-1)+((k+1)/noOfColumns)-1] == 0) {
                                v[(noOfRows-1)*(noOfColumns-1)+((k+1)/noOfColumns)-1] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[(noOfRows-1)*(noOfColumns-1)+((k+1)/noOfColumns)-1+20] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[(noOfRows-1)*(noOfColumns-1)+((k+1)/noOfColumns)-1+20].setColor(Color.BLACK);
                                    else
                                        paintLines[(noOfRows-1)*(noOfColumns-1)+((k+1)/noOfColumns)-1+20].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[(noOfRows-1)*(noOfColumns-1)+((k+1)/noOfColumns)-1+20].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[(noOfRows-1)*(noOfColumns-1)+((k+1)/noOfColumns)-1+20].setColor(Color.BLUE);
                                    else
                                        paintLines[(noOfRows-1)*(noOfColumns-1)+((k+1)/noOfColumns)-1+20].setColor(Color.RED);
                                }
                                turn++;
                            }
                        }

                        else {                                                          //THE GODDAMN CENTRES
                            double dx1 = Math.pow(x - (X) , 2);
                            double dy1 = Math.pow(y - (Y-spacing) , 2);
                            double dx2 = Math.pow(x - (X-spacing), 2);
                            double dy2 = Math.pow(y - Y , 2);
                            double dx3 = Math.pow(x - (X), 2);
                            double dy3 = Math.pow(y - (Y+spacing) , 2);
                            double dx4 = Math.pow(x -(X+spacing),2);
                            double dy4 = Math.pow(y - Y,2);

                            if(dx1 + dy1 < Math.pow(radius, 2) && v[(noOfRows-1)*((k+1)%noOfColumns-1)+(k+1)/noOfColumns-1] == 0) {
                                v[(noOfRows-1)*((k+1)%noOfColumns-1)+(k+1)/noOfColumns-1] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[(noOfRows-1)*((k+1)%noOfColumns-1)+(k+1)/noOfColumns-1+20] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[(noOfRows-1)*((k+1)%noOfColumns-1)+(k+1)/noOfColumns-1+20].setColor(Color.BLACK);
                                    else
                                        paintLines[(noOfRows-1)*((k+1)%noOfColumns-1)+(k+1)/noOfColumns-1+20].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[(noOfRows-1)*((k+1)%noOfColumns-1)+(k+1)/noOfColumns-1+20].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[(noOfRows-1)*((k+1)%noOfColumns-1)+(k+1)/noOfColumns-1+20].setColor(Color.BLUE);
                                    else
                                        paintLines[(noOfRows-1)*((k+1)%noOfColumns-1)+(k+1)/noOfColumns-1+20].setColor(Color.RED);
                                }
                                turn++;
                            }
                            if(dx2 + dy2 < Math.pow(radius, 2) && h[(noOfColumns-1)*((k+1)/noOfColumns)+((k+1)%noOfColumns)-2] == 0) {
                                h[(noOfColumns-1)*((k+1)/noOfColumns)+((k+1)%noOfColumns)-2] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[(noOfColumns-1)*((k+1)/noOfColumns)+((k+1)%noOfColumns)-2] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)+((k+1)%noOfColumns)-2].setColor(Color.BLACK);
                                    else
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)+((k+1)%noOfColumns)-2].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)+((k+1)%noOfColumns)-2].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)+((k+1)%noOfColumns)-2].setColor(Color.BLUE);
                                    else
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)+((k+1)%noOfColumns)-2].setColor(Color.RED);
                                }
                                turn++;
                            }
                            if(dx3 + dy3 < Math.pow(radius, 2) && v[(noOfRows-1)*((k+1)%noOfColumns-1)+(k+1)/noOfColumns] == 0) {
                                v[(noOfRows-1)*((k+1)%noOfColumns-1)+(k+1)/noOfColumns] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[(noOfRows-1)*((k+1)%noOfColumns-1)+(k+1)/noOfColumns+20] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[(noOfRows-1)*((k+1)%noOfColumns-1)+(k+1)/noOfColumns+20].setColor(Color.BLACK);
                                    else
                                        paintLines[(noOfRows-1)*((k+1)%noOfColumns-1)+(k+1)/noOfColumns+20].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[(noOfRows-1)*((k+1)%noOfColumns-1)+(k+1)/noOfColumns+20].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[(noOfRows-1)*((k+1)%noOfColumns-1)+(k+1)/noOfColumns+20].setColor(Color.BLUE);
                                    else
                                        paintLines[(noOfRows-1)*((k+1)%noOfColumns-1)+(k+1)/noOfColumns+20].setColor(Color.RED);
                                }
                                turn++;
                            }
                            if(dx4 + dy4 < Math.pow(radius, 2) && h[(noOfColumns-1)*((k+1)/noOfColumns)+((k+1)%noOfColumns)-1] == 0) {
                                h[(noOfColumns-1)*((k+1)/noOfColumns)+((k+1)%noOfColumns)-1] = 1;MainActivity.vibrate();
                                for(int z = 0 ; z < 40 ; z++)
                                    undo[z] = 0;
                                undo[(noOfColumns-1)*((k+1)/noOfColumns)+((k+1)%noOfColumns)-1] = 1;
                                postInvalidate();
                                val[k] = 0;
                                if(noOfPlayers == 2 || noOfPlayers == 1) {
                                    if (turn % 2 == 1)
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)+((k+1)%noOfColumns)-1].setColor(Color.BLACK);
                                    else
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)+((k+1)%noOfColumns)-1].setColor(Color.BLUE);
                                }
                                else if(noOfPlayers == 3) {
                                    if (turn % 3 == 1)
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)+((k+1)%noOfColumns)-1].setColor(Color.BLACK);
                                    else if(turn % 3 == 2)
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)+((k+1)%noOfColumns)-1].setColor(Color.BLUE);
                                    else
                                        paintLines[(noOfColumns-1)*((k+1)/noOfColumns)+((k+1)%noOfColumns)-1].setColor(Color.RED);
                                }
                                turn++;
                            }
                        }

                    }
                }

                return true;
            }

            case MotionEvent.ACTION_UP: {
                for(int i = 0 ; i < 25 ; i++)
                    val[i] = 0;
            }

        }

        return value;
    }

    public static void undobtn() {
        for (int i = 0 ; i < 40 ; i++) {
            if(undo[i] == 1) {
                if(i >= 20)
                    v[i-20] = 0;
                else
                    h[i] = 0;
                turn--;
                //postInvalidate();
            }
            if(noOfPlayers == 1)
                if(aiundo[i] == 1) {
                    if (i >= 20)
                        v[i - 20] = 0;
                    else
                        h[i] = 0;
                    turn = 1;
                }
        }
        for(int i = 0; i < 16 ; i++) {
            if(lastRectDrawn[i] == 1) {
                lastRectDrawn[i] = 0;
                turn++;
                rectDrawn[i] = 0;
                if(pundo == 1)
                    p1--;
                else if(pundo == 2)
                    p2--;
                else if(pundo == 3)
                    p3--;
            }
        }
    }

    public void callAI() {
        boolean noluck = true;
        int one = 1;
        if(difficulty == 1) {
            int num;
            int again = 1;
            do {
                num = rand.nextInt(40);
                if(num >= 20) {
                    if(num-20 > (noOfColumns)*(noOfRows-1) - 1 || v[num-20] == 1)
                        again = 1;
                    else {
                        for(int i = 0 ; i < 40 ; i++)
                            aiundo[i] = 0;
                        aiundo[num] = 1;
                        v[num - 20] = 1;
                        paintLines[num].setColor(Color.BLUE);
                        again = 0;
                    }
                }
                else {
                    if(num > (noOfColumns-1)*noOfRows - 1 || h[num] == 1)
                        again = 1;
                    else {
                        for(int i = 0 ; i < 40 ; i++)
                            aiundo[i] = 0;
                        aiundo[num] = 1;
                        h[num] = 1;
                        paintLines[num].setColor(Color.BLUE);
                        again = 0;
                    }
                }
            }while (again == 1);
            turn++;
            postInvalidate();
        }
        else if(difficulty == 2) {
            for(int i = 0, j = 0 ; i < (noOfColumns-1) ; i++ , j += noOfRows-1 ) {
                if(h[i] == 0 && h[i+noOfColumns-1] == 1 && v[j] == 1 && v[j+noOfRows-1] == 1) {
                    h[i] = 1;
                    for(int x = 0 ; x < 40 ; x++)
                        aiundo[x] = 0;
                    aiundo[i] = 1;
                    paintLines[i].setColor(Color.BLUE);
                    noluck = false;
                    break;
                }
                else if (h[i] == 1 && h[i+noOfColumns-1] == 0 && v[j] == 1 && v[j+noOfRows-1] == 1) {
                    h[i+noOfColumns-1] = 1;
                    for(int x = 0 ; x < 40 ; x++)
                        aiundo[x] = 0;
                    aiundo[i+noOfColumns-1] = 1;
                    paintLines[i+noOfColumns-1].setColor(Color.BLUE);
                    noluck = false;
                    break;
                }
                else if (h[i] == 1 && h[i+noOfColumns-1] == 1 && v[j] == 0 && v[j+noOfRows-1] == 1) {
                    v[j] = 1;
                    for(int x = 0 ; x < 40 ; x++)
                        aiundo[x] = 0;
                    aiundo[j+20] = 1;
                    paintLines[20].setColor(Color.BLUE);
                    noluck = false;
                    break;
                }
                else if (h[i] == 1 && h[i+noOfColumns-1] == 1 && v[j] == 1 && v[j+noOfRows-1] == 0) {
                    v[j+noOfRows-1] = 1;
                    for(int x = 0 ; x < 40 ; x++)
                        aiundo[x] = 0;
                    aiundo[j+noOfRows-1+20] = 1;
                    paintLines[j+noOfRows-1+20].setColor(Color.BLUE);
                    noluck = false;
                    break;
                }
            }
            if(noluck) {
                for(int i = (noOfColumns-1), j = 1 ; i < 2*(noOfColumns-1) ; i++ , j += noOfRows-1 ) {
                    if(h[i] == 0 && h[i+noOfColumns-1] == 1 && v[j] == 1 && v[j+noOfRows-1] == 1) {
                        h[i] = 1;
                        for(int x = 0 ; x < 40 ; x++)
                            aiundo[x] = 0;
                        aiundo[i] = 1;
                        paintLines[i].setColor(Color.BLUE);
                        noluck = false;
                        break;
                    }
                    else if (h[i] == 1 && h[i+noOfColumns-1] == 0 && v[j] == 1 && v[j+noOfRows-1] == 1) {
                        h[i+noOfColumns-1] = 1;
                        for(int x = 0 ; x < 40 ; x++)
                            aiundo[x] = 0;
                        aiundo[i+noOfColumns-1] = 1;
                        paintLines[i+noOfColumns-1].setColor(Color.BLUE);
                        noluck = false;
                        break;
                    }
                    else if (h[i] == 1 && h[i+noOfColumns-1] == 1 && v[j] == 0 && v[j+noOfRows-1] == 1) {
                        v[j] = 1;
                        for(int x = 0 ; x < 40 ; x++)
                            aiundo[x] = 0;
                        aiundo[j+20] = 1;
                        paintLines[20].setColor(Color.BLUE);
                        noluck = false;
                        break;
                    }
                    else if (h[i] == 1 && h[i+noOfColumns-1] == 1 && v[j] == 1 && v[j+noOfRows-1] == 0) {
                        v[j+noOfRows-1] = 1;
                        for(int x = 0 ; x < 40 ; x++)
                            aiundo[x] = 0;
                        aiundo[j+noOfRows-1+20] = 1;
                        paintLines[j+noOfRows-1+20].setColor(Color.BLUE);
                        noluck = false;
                        break;
                    }
                }
            }
            if(noluck && noOfRows > 3) {
                for(int i = 2*(noOfColumns-1), j = 2 ; i < 3*(noOfColumns-1) ; i++ , j += noOfRows-1 ) {
                    if(h[i] == 0 && h[i+noOfColumns-1] == 1 && v[j] == 1 && v[j+noOfRows-1] == 1) {
                        h[i] = 1;
                        for(int x = 0 ; x < 40 ; x++)
                            aiundo[x] = 0;
                        aiundo[i] = 1;
                        paintLines[i].setColor(Color.BLUE);
                        noluck = false;
                        break;
                    }
                    else if (h[i] == 1 && h[i+noOfColumns-1] == 0 && v[j] == 1 && v[j+noOfRows-1] == 1) {
                        h[i+noOfColumns-1] = 1;
                        for(int x = 0 ; x < 40 ; x++)
                            aiundo[x] = 0;
                        aiundo[i+noOfColumns-1] = 1;
                        paintLines[i+noOfColumns-1].setColor(Color.BLUE);
                        noluck = false;
                        break;
                    }
                    else if (h[i] == 1 && h[i+noOfColumns-1] == 1 && v[j] == 0 && v[j+noOfRows-1] == 1) {
                        v[j] = 1;
                        for(int x = 0 ; x < 40 ; x++)
                            aiundo[x] = 0;
                        aiundo[j+20] = 1;
                        paintLines[20].setColor(Color.BLUE);
                        noluck = false;
                        break;
                    }
                    else if (h[i] == 1 && h[i+noOfColumns-1] == 1 && v[j] == 1 && v[j+noOfRows-1] == 0) {
                        v[j+noOfRows-1] = 1;
                        for(int x = 0 ; x < 40 ; x++)
                            aiundo[x] = 0;
                        aiundo[j+noOfRows-1+20] = 1;
                        paintLines[j+noOfRows-1+20].setColor(Color.BLUE);
                        noluck = false;
                        break;
                    }
                }
            }
            if(noluck && noOfRows > 4) {
                for(int i = 3*(noOfColumns-1), j = 3 ; i < 4*(noOfColumns-1) ; i++ , j += noOfRows-1 ) {
                    if(h[i] == 0 && h[i+noOfColumns-1] == 1 && v[j] == 1 && v[j+noOfRows-1] == 1) {
                        h[i] = 1;
                        for(int x = 0 ; x < 40 ; x++)
                            aiundo[x] = 0;
                        aiundo[i] = 1;
                        paintLines[i].setColor(Color.BLUE);
                        noluck = false;
                        break;
                    }
                    else if (h[i] == 1 && h[i+noOfColumns-1] == 0 && v[j] == 1 && v[j+noOfRows-1] == 1) {
                        h[i+noOfColumns-1] = 1;
                        for(int x = 0 ; x < 40 ; x++)
                            aiundo[x] = 0;
                        aiundo[i+noOfColumns-1] = 1;
                        paintLines[i+noOfColumns-1].setColor(Color.BLUE);
                        noluck = false;
                        break;
                    }
                    else if (h[i] == 1 && h[i+noOfColumns-1] == 1 && v[j] == 0 && v[j+noOfRows-1] == 1) {
                        v[j] = 1;
                        for(int x = 0 ; x < 40 ; x++)
                            aiundo[x] = 0;
                        aiundo[j+20] = 1;
                        paintLines[20].setColor(Color.BLUE);
                        noluck = false;
                        break;
                    }
                    else if (h[i] == 1 && h[i+noOfColumns-1] == 1 && v[j] == 1 && v[j+noOfRows-1] == 0) {
                        v[j+noOfRows-1] = 1;
                        for(int x = 0 ; x < 40 ; x++)
                            aiundo[x] = 0;
                        aiundo[j+noOfRows-1+20] = 1;
                        paintLines[j+noOfRows-1+20].setColor(Color.BLUE);
                        noluck = false;
                        break;
                    }
                }
            }
            if(noluck) {
                int num;
                int again = 1;
                do {
                    num = rand.nextInt(40);
                    if(num >= 20) {
                        if(num-20 > (noOfColumns)*(noOfRows-1) - 1 || v[num-20] == 1)
                            again = 1;
                        else {
                            for(int i = 0 ; i < 40 ; i++)
                                aiundo[i] = 0;
                            aiundo[num] = 1;
                            v[num - 20] = 1;
                            paintLines[num].setColor(Color.BLUE);
                            again = 0;
                        }
                    }
                    else {
                        if(num > (noOfColumns-1)*noOfRows - 1 || h[num] == 1)
                            again = 1;
                        else {
                            for(int i = 0 ; i < 40 ; i++)
                                aiundo[i] = 0;
                            aiundo[num] = 1;
                            h[num] = 1;
                            paintLines[num].setColor(Color.BLUE);
                            again = 0;
                        }
                    }
                }while (again == 1);
            }
            turn++;
            postInvalidate();
        }
    }
}
