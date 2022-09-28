package ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.prot0003.R;
import algonquin.cst2335.prot0003.databinding.ActivityMainBinding;
import data.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding variableBinding;
    private MainViewModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//       connectAppCompatActivity to ViewModel class;
        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());//loads XML
        TextView myText = variableBinding.textview;
        CheckBox checkBox = variableBinding.checkBox;
        Switch switch1 = variableBinding.switch1;
        RadioButton radioButton = variableBinding.radioButton;
        ImageButton myimagebutton = variableBinding.myimagebutton;
        EditText myedit = variableBinding.myedittext;

        variableBinding.mybutton.setOnClickListener(click ->
        {
            String editString = myedit.getText().toString();

        model.editString.postValue(editString);
        });

        //call the observe() function, Lambda() function

        model.editString.observe( this, s -> {

            variableBinding.textview.setText("Your edit text has" + s);
        });
        //selected button

        model.isSelected.observe( this, selected -> {

//            int x = 1;
            variableBinding.checkBox.setChecked(selected);
            variableBinding.radioButton.setChecked(selected);
            variableBinding.switch1.setChecked(selected);
        Toast.makeText( this, "The value now is: " + selected, Toast.LENGTH_LONG).show();
        });

        variableBinding.checkBox.setOnCheckedChangeListener( (button, isSelected) -> {
                    model.isSelected.postValue(isSelected);
                });

        variableBinding.radioButton.setOnCheckedChangeListener( (button, isSelected) -> {
                            model.isSelected.postValue(isSelected);
                });

        variableBinding.switch1.setOnCheckedChangeListener( (button, isSelected) -> {
                        model.isSelected.postValue(isSelected);
                    });

        //ImageButton
        myimagebutton.setOnClickListener(click -> {
            Toast.makeText(this, "the width =" +variableBinding.myimagebutton.getWidth() + " the heigth =" +variableBinding.myimagebutton.getHeight(), Toast.LENGTH_LONG).show();


    });

            }
        }




















