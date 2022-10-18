package algonquin.cst2335.prot0003;

import androidx.appcompat.app.AppCompatActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.contract.ActivityResultContract;
import androidx. activity.result.ActivityResultLauncher;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import algonquin.cst2335.prot0003.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {
    ActivitySecondBinding binding;

    ActivityResultCallback callback = new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {

                Intent data = result.getData();

                Bitmap thumbnail = data.getParcelableExtra("data");

                                   //binding.changePic.setImageBitmap (thumbnail);

                FileOutputStream fOut = null;
                try {
                    fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);

                    fOut.flush();
                    fOut.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), callback);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    Intent fromPrevious = getIntent();
    String emailaddress = fromPrevious.getStringExtra("EmailAddress");
        binding.textView.setText("Welcome back " + emailaddress);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String phoneNum = prefs.getString("phoneNumber", "");
        binding.PhoneNumber.setText(phoneNum);

        binding.button.setOnClickListener( click -> {

            String userTyped = binding.PhoneNumber.getText().toString();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("phoneNumber", userTyped);
            editor.apply();

            Intent call = new Intent(Intent.ACTION_DIAL);
    call.setData(Uri.parse("tel:" + binding.PhoneNumber.getText().toString()));
            startActivity(call);
        });

        File myImage = new File (getFilesDir(), "Picture.png");
        if (myImage.exists()) {
            Bitmap theImage = BitmapFactory.decodeFile(getFilesDir() + "/" + "Picture.png");
            binding.imageView2.setImageBitmap (theImage);
        }

        binding.changePic.setOnClickListener( click -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraResult.launch(cameraIntent);
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}

