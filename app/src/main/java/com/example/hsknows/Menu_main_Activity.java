package com.example.hsknows;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.myapplication.R;

public class Menu_main_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = LayoutInflater.from(this);

        setContentView(R.layout.menu_main_activity);
        MotionLayout main_layout = (MotionLayout)findViewById(R.id.menu_main_motionlayout);
        ScrollView menu_main_scrollview = (ScrollView)findViewById(R.id.menu_main_scrollview);
        ImageButton back_button = (ImageButton)findViewById(R.id.menu_main_back_button);
        LinearLayout menu_main_scrollview_LinearLayout = (LinearLayout)findViewById(R.id.menu_main_scrollview_LinearLayout);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Menu_main_Activity.this, "Back", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        menu_main_scrollview_LinearLayout.addView(inflater.inflate(R.layout.menu_main_activity_card, null));
    }
}