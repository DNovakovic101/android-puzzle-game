package pmf.novak101.myapplicationpuzzle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ClipData;

import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity<GridLayot> extends AppCompatActivity {

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                dragged = (ImageView) view;
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDragAndDrop(data, shadowBuilder, view, 0);
                return true;
            } else {
                return false;
            }
        }
    }

    class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DROP:
                    switchTwoGridItems((ImageView) v);
                    if (isGameFinished())
                        Toast.makeText(MainActivity.this,"Congratulations!",Toast.LENGTH_LONG).show();
                    v.invalidate();
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView imageView10;
    ImageView imageView11;
    ImageView imageView12;

    List<ImageView> imageViewList = new ArrayList<>();

    ImageView dragged ;
    ImageView dropped ;
    androidx.gridlayout.widget.GridLayout gridLayout ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = (androidx.gridlayout.widget.GridLayout) findViewById(R.id.layoutGridMain);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);
        imageView10 = findViewById(R.id.imageView10);
        imageView11 = findViewById(R.id.imageView11);
        imageView12 = findViewById(R.id.imageView12);

        Collections.addAll(imageViewList, imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9,imageView10, imageView11, imageView12);

        for (ImageView imageView : imageViewList)
            setOnTouchAndDragListener(imageView);

        shuffleGrid();


    }

    public void setOnTouchAndDragListener(ImageView imageView) {
        imageView.setOnTouchListener(new MyTouchListener());
        imageView.setOnDragListener(new MyDragListener());
    }

    public void shuffleGrid() {
        Collections.shuffle(imageViewList);
        gridLayout.removeAllViews();
        int i = 0;
        for (ImageView imageView : imageViewList) {
            gridLayout.addView(imageView, i++);
        }
        gridLayout.invalidate();
    }

    public boolean isGameFinished() {

        for(int i = 0; i < 12; i++) {
            if( Integer.parseInt( gridLayout.getChildAt(i).getTag().toString())-1 != (i) )
                return false;
        }
        shuffleGrid();
        return true;
    }

    public void playAgain(View view) {
        shuffleGrid();
    }

    public void switchTwoGridItems(ImageView view) {
        dropped = (ImageView) view;
        int startPos = 0;
        int endPos = 0;
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            if (gridLayout.getChildAt(i).equals(dragged))
                startPos = i;
            if (gridLayout.getChildAt(i).equals(dropped))
                endPos = i;
        }

        gridLayout.removeView(dragged);
        gridLayout.addView(dragged,endPos);
        gridLayout.removeView(dropped);
        gridLayout.addView(dropped,startPos);

    }
}
