package ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Switch;

import algonquin.cst2335.prot0003.R;

import data.MainViewModel;

public class MainActivity extends AppCompatActivity {
    ImageView imgView;
    Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.flagView);
        sw = findViewById(R.id.switch1);



        sw.setOnCheckedChangeListener( (btn, isChecked) -> {

            if (isChecked)
            {
                RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotate.setDuration(5000);
                rotate.setRepeatCount(Animation.INFINITE);
                rotate.setInterpolator(new LinearInterpolator());

                imgView.startAnimation(rotate);
            }else {
                imgView.clearAnimation();
            }

        });

//        sw = setOnCheckedChangeListener((btn, isChecked)) -> {
//
//            if (isChecked)
//            {
//                RotateAnimation rotate = new RotateAnimation (0, 360,
//                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//                rotate.set.Duration (5000);
//                rotate.setRepeatCount(Animation.INFINITE);
//                rotate.setInterpolator(new LenearInterpolator());
//               imgView.startAnimation(rotate);
//            }
//            else {
//                imgView.clearAnimation();
//            }
//        });
        }
    }






















