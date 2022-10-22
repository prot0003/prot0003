package algonquin.cst2335.prot0003;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

/**
 * Password validation, validates the password whether includes UpperCase, LowerCase letter, Number
 * @author Protasova Lyubov
 * @version 1.0
 * @since October 21, 2022
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** This holds the text at the centre of the screen */
        TextView typePW = findViewById(R.id.textView);
        /** This holds the password at the centre of the screen */
        EditText pw = findViewById(R.id.EditText);
        /** This holds the login button at the centre of the screen */
        Button btn = findViewById(R.id.LoginButton);

        /**
         * Login button onClick Listener to validate the password
         */
        btn.setOnClickListener(click -> {
            String password = pw.getText().toString();
            checkPasswordComplexity(password);
            if (checkPasswordComplexity(password)) {
                typePW.setText("Your password meets the requirements.");
            } else {
                typePW.setText("You shall not pass!");
            }
        });

    }

    /**
     * this function is checking the entering password which is suitable for the requirement,
     * @param password The String object that we are checking
     * @return true if the password is suitable for all requirement
     */
    boolean checkPasswordComplexity (String password){
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                foundNumber = true;
            } else if (Character.isUpperCase(password.charAt(i))) {
                foundUpperCase = true;
            } else if (Character.isLowerCase(password.charAt(i))) {
                foundLowerCase = true;
            } else if (isSpecialCharacter(password.charAt(i))) {
                foundSpecial = true;
            }

        }

        if (!foundUpperCase) {
            Toast.makeText(this, "Your password have no an Upper Case Letter.", Toast.LENGTH_LONG).show();
            return false;
        } else if (!foundLowerCase) {
            Toast.makeText(this, "Your password have no a Lower Case Letter.", Toast.LENGTH_LONG).show();
            return false;
        } else if (!foundNumber) {
            Toast.makeText(this, "Your password have no a number.", Toast.LENGTH_LONG).show();
            return false;
        } else if (!foundSpecial) {
            Toast.makeText(this, "Your password have no a Special Symbol.", Toast.LENGTH_LONG).show();
            return false;
        } else
            Toast.makeText(this, "Your password meets the requirements.", Toast.LENGTH_LONG).show();
        return true;
    }

    boolean isSpecialCharacter ( char c){
        switch (c) {
            case '#':
            case '!':
            case '?':
            case '*':
            case '%':
            case '$':
            case '@':
            case '^':
                return true;
            default:
                return false;
        }
    }

}

