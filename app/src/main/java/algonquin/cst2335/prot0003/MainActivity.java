package algonquin.cst2335.prot0003;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.Intent;
import android.util.Log;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.widget.Toast;
import android.widget.ImageButton;

import android.os.Bundle;

import algonquin.cst2335.prot0003.databinding.ActivityMainBinding;
//import algonquin.cst2335.prot0003.data.MainViewModel;

public class MainActivity extends AppCompatActivity {
      private  ActivityMainBinding binding;
//     private  MainViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
 //   model=new NewModelProvider(this).get(MainViewModel.class);

    binding = ActivityMainBinding.inflate(getLayoutInflater());
        //loads xml file on screen
    setContentView(binding.getRoot());
    Log.w( "MainActivity",  "In onCreate() - Loading Widgets");

    SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
    String emailAddress = prefs.getString("LoginName", "");
    binding.emailEditText.setText(emailAddress);

    binding.button.setOnClickListener(clk -> {
    Intent chatRoom = new Intent(MainActivity.this, ChatRoom.class);
    startActivity(chatRoom);
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
    //@Override
    protected void onStart(){
    super.onStart();
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
//    protected void onCreate(Bundle savedInstanceState) {
//    savedInstanceState: null
//    super.onCreate(savedInstanceState);
//    savedInstanceState: null

//    Log.e(tag”MainActivity”, msg:”In OnCreate()”

//     binding =  ActivityMainBinding.inflate(getLayoutInflater());
//     binding.button.setOnClickListener(click ->
       //do this when you click on button
//     Intent_nextPage = new Intent(MainActivity.this, SecondActivity.class);//tell where you want to go
//     startActivity(nextPage) // will go to another activity
//   });



    // activity is visible, and is responding to touch

//     Log.w(tag”MainActivity”, msg:”In OnResume()”





