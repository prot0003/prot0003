package algonquin.cst2335.prot0003;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import algonquin.cst2335.prot0003.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
        ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        //loads xml file on screen
        setContentView(binding.getRoot());

        Log.w( "MainActivity",  "In onCreate() - Loading Widgets");

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailAddress = prefs.getString("LoginName", "");
        binding.emailEditText.setText(emailAddress);

        binding.button.setOnClickListener(clk -> {
            String userTyped = binding.emailEditText.getText().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("LoginName", userTyped);
            editor.apply();

            Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);//tell where you want to go
            nextPage.putExtra ("EmailAddress", binding.emailEditText.getText().toString());

            startActivity(nextPage);
        });
    }
    // activity is visible, but not responding to touch
    @Override
    protected void onStart(){
        super.onStart();
        //      Log.e(tag”MainActivity”, msg:”In OnStart()”
        Log.w("MainActivity", "In onStart() - Loading");

    }
    // activity is visible, and is responding to touch
    @Override
    protected void onResume() {
        super.onResume();
        Log.w("MainActivity", "In onResume() - Loading");
    }

    //no longer responds to user input
    @Override
    protected void onPause() {
        super.onPause();
        Log.w("MainActivity", "In onPause() - Loading");
    }

    // no longer visiblle
    @Override
    protected void onStop() {
        super.onStop();
        Log.w("MainActivity", "In onStop() - Loading");
    }

    // the garbage collector is clearing the memory
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w("MainActivity", "In onDestroy() - Loading");
    }
}

//    ActivityMainBinding binding;
 //   @Override
  //  protected void onCreate(Bundle savedInstanceState) {
//savedInstanceState: null
 //       super.onCreate(savedInstanceState);
//savedInstanceState: null

//        Log.e(tag”MainActivity”, msg:”In OnCreate()”

//        binding =  ActivityMainBinding.inflate(getLayoutInflater());
 //       binding.button.setOnClickListener(click ->
//do this when you click on button
 //               Intent_nextPage = new Intent(MainActivity.this, SecondActivity.class);//tell where you want to go
 //       startActivity(nextPage) // will go to another activity
 //   });



    // activity is visible, and is responding to touch

//Log.w(tag”MainActivity”, msg:”In OnResume()”





