package algonquin.cst2335.prot0003;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import algonquin.cst2335.prot0003.databinding.ActivityMainBinding;

/**
 * Password validation, validates the password whether includes UpperCase, LowerCase letter, Number
 * @author Protasova Lyubov
 * @version 1.0
 * @since October 21, 2022
 */
public class MainActivity extends AppCompatActivity {

    protected String cityName;
    protected RequestQueue queue = null;
    ImageRequest imgReq;
    Bitmap image;
    String url = null;
    String imgUrl = "https://openweathermap.org/img/w/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.getForecastButton.setOnClickListener(click -> {
            cityName = binding.cityEdit.getText().toString();
            try{
                url = "https://api.openweathermap.org/data/2.5/weather?q=" + URLEncoder.encode(cityName, "UTF-8") + "&appid=69b4a40df6f3b050bb7a22edfd16a495&units=metric";
//            String stringURL = "https://api.openweathermap.org/data/2.5/weather?q=" + URLEncoder.encode(cityName, "UTF-8") + "&appid=69b4a40df6f3b050bb7a22edfd16a495&units=metric";
       JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                          (response) -> {

                              try {
             //                     JSONObject coordination = response.getJSONObject("coordination");
                                  JSONArray weatherArray = response.getJSONArray("weather");
                                  JSONObject position0 = weatherArray.getJSONObject(0);
                                  String description = position0.getString("description");
                                  String iconName = position0.getString("icon");
                                  JSONObject mainObject = response.getJSONObject("main");
                                  double current = mainObject.getDouble("temp");
                                  double min = mainObject.getDouble("temp_min");
                                  double max = mainObject.getDouble("temp_max");
                                  int humidity = mainObject.getInt("humidity");

                                  String pathname = getFilesDir() + "/" + iconName + ".png";
                                  File file = new File(pathname);
                                  if (file.exists()) {
                                      image = BitmapFactory.decodeFile(pathname);
                                  } else {
                                      imgReq = new ImageRequest(imgUrl + iconName + ".png", new Response.Listener<Bitmap>() {
                                          @Override
                                          public void onResponse(Bitmap response) {
                                              try {
                                                  image = response;
                                                  image.compress(Bitmap.CompressFormat.PNG, 100, MainActivity.this.openFileOutput(iconName + ".png", Activity.MODE_PRIVATE));
                                              } catch (Exception ex) {

                                              }
                                          }

                                      }, 1024, 1024, ImageView.ScaleType.CENTER, null,
                                              (error) -> {
                                      });
                                      queue.add(imgReq);
                                  }
                                  runOnUiThread(() ->{
                                      binding.tempText.setText("The current temperature is: " + current);
                                      binding.tempText.setVisibility(View.VISIBLE);
                                      binding.minText.setText("The min temperature is: " + min);
                                      binding.minText.setVisibility(View.VISIBLE);
                                      binding.maxText.setText("The max temperature is: " + max);
                                      binding.maxText.setVisibility(View.VISIBLE);
                                      binding.humidityText.setText("The humidity is " + humidity + "%");
                                      binding.humidityText.setVisibility(View.VISIBLE);
                                      binding.icon.setImageBitmap(image);
                                      binding.icon.setVisibility(View.VISIBLE);
                                      binding.descriptionText.setText(description);
                                      binding.descriptionText.setVisibility(View.VISIBLE);

                                  });
                              } catch (JSONException e) {
                                  e.printStackTrace();
                              } },
                              (error) -> {
                              });
                              queue.add(request);
                          } catch (UnsupportedEncodingException e) {
           e.printStackTrace();
                }
            });
                }
    }

//                           (error) -> { } );
//        queue.add(request);
//        });
    //    setContentView(R.layout.activity_main);

        /** This holds the text at the centre of the screen */
    //    TextView typePW = findViewById(R.id.textView);
        /** This holds the password at the centre of the screen */
    //    EditText pw = findViewById(R.id.EditText);
        /** This holds the login button at the centre of the screen */
    //   Button btn = findViewById(R.id.LoginButton);

        /**
         * Login button onClick Listener to validate the password
         */
//       btn.setOnClickListener(click -> {
//            String password = pw.getText().toString();
//            checkPasswordComplexity(password);
//            if (checkPasswordComplexity(password)) {
//                typePW.setText("Your password meets the requirements.");
//            } else {
//                typePW.setText("You shall not pass!");
    /**
     * this function is checking the entering password which is suitable for the requirement,
     * @param password The String object that we are checking
     * @return true if the password is suitable for all requirement
     */
 //   boolean checkPasswordComplexity (String password){
 //      boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
 //       foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;
//        for (int i = 0; i < password.length(); i++) {
//            if (Character.isDigit(password.charAt(i))) {
//              foundNumber = true;
//            } else if (Character.isUpperCase(password.charAt(i))) {
//                foundUpperCase = true;
//            } else if (Character.isLowerCase(password.charAt(i))) {
//                foundLowerCase = true;
//            } else if (isSpecialCharacter(password.charAt(i))) {
//                foundSpecial = true;
//           }

//       }

//        if (!foundUpperCase) {
//            Toast.makeText(this, "Your password have no an Upper Case Letter.", Toast.LENGTH_LONG).show();
//            return false;
//        } else if (!foundLowerCase) {
//            Toast.makeText(this, "Your password have no a Lower Case Letter.", Toast.LENGTH_LONG).show();
//            return false;
//        } else if (!foundNumber) {
//            Toast.makeText(this, "Your password have no a number.", Toast.LENGTH_LONG).show();
//            return false;
//        } else if (!foundSpecial) {
//            Toast.makeText(this, "Your password have no a Special Symbol.", Toast.LENGTH_LONG).show();
//            return false;
//        } else
//            Toast.makeText(this, "Your password meets the requirements.", Toast.LENGTH_LONG).show();
//        return true;
//   }

 //   boolean isSpecialCharacter ( char c){
 //       switch (c) {
 //          case '#':
//            case '!':
//            case '?':
//            case '*':
//            case '%':
//            case '$':
//            case '@':
//            case '^':
//                return true;
//            default:
//                return false;
//       }
 //   }



