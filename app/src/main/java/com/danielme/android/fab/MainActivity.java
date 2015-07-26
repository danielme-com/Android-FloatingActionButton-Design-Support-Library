package com.danielme.android.fab;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import static com.nineoldandroids.view.ViewPropertyAnimator.animate;

/**
 * @author danielme.com
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private FloatingActionButton floatingActionButton;

    private static final int DURATION = 150;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);

        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<String> list = new LinkedList<String>();
        for (int i = 1; i < 30; i++) {
            list.add(String.valueOf(i));
        }
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, android.R.id.text1, list));

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, R.string.action, Toast.LENGTH_SHORT).show();
            }
        });
        //floatingActionButton.setBackgroundTintList(getResources().getColorStateList(R.color.primary));

        /*OnScrollUpDownListener.Action scrollAction = new OnScrollUpDownListener.Action() {

            @Override
            public void up() {
                floatingActionButton.hide();
            }

            @Override
            public void down() {
                floatingActionButton.show();
            }

        };*/

        OnScrollUpDownListener.Action scrollAction = new OnScrollUpDownListener.Action() {

            private boolean hidden = true;

            @Override
            public void up() {
                if (hidden) {
                    hidden = false;
                    animate(floatingActionButton)
                            .translationY(floatingActionButton.getHeight() +
                                    getResources().getDimension(R.dimen.fab_margin))
                            .setInterpolator(new LinearInterpolator())
                            .setDuration(DURATION);
                }
            }

            @Override
            public void down() {
                if (!hidden) {
                    hidden = true;
                    animate(floatingActionButton)
                            .translationY(0)
                            .setInterpolator(new LinearInterpolator())
                            .setDuration(DURATION);

                }
            }

        };

        listView.setOnScrollListener(new OnScrollUpDownListener(listView, 8, scrollAction));
    }


}
