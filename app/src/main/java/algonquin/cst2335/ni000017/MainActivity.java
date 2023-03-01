package algonquin.cst2335.ni000017;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is a page to simulate the login page
 * @verion 1.0
 * @author Jianglian Ni
 */
public class MainActivity extends AppCompatActivity {
    /** this holds the text on the centre of the screen*/
    private TextView tv=null;
    /** this holds the edit text for inputting the password*/
    private EditText et= null;
    /** this is "Login" button displaying at the centre of the screen*/
    private  Button btn =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        et = findViewById (R.id.editText);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(clk ->{
            String password = et.getText().toString();
            if (checkPasswordComplexity(password)){
                tv.setText("Your password meets the requirements");
            }
            else{
                tv.setText("You shall not pass!");
            }
        });
    }

    /**This function is used to validate the password to meet the requirenment or not.
     * @param pw The String object that we are checking
     * @return true if the password meet the requirement
     */
    boolean checkPasswordComplexity(String pw)
    {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for (int i =0;i< pw.length();i++){
            char c = pw.charAt(i);
            if(Character.isUpperCase(c)){
                foundUpperCase = true;
            }

            else if(Character.isLowerCase(c)){
                foundLowerCase= true;
            }
            else if(Character.isDigit(c)){
                foundNumber = true;
            }
            else if (isSpecialCharacter(c)){
                foundSpecial=true;
            }
        }
        if(!foundUpperCase)
        {
            Toast.makeText(this,"Your password does not have an upper case letter",Toast.LENGTH_LONG).show() ;// Say that they are missing an upper case letter;
            return false;
        }

        else if(!foundLowerCase)
        {
            Toast.makeText(this,"Your password does not have an lower case letter", Toast.LENGTH_LONG).show(); // Say that they are missing a lower case letter;

            return false;
        }
        else if(!foundNumber) {
            Toast.makeText( this, "Your password does not any number", Toast.LENGTH_LONG ).show();
            return false;
        }
        else if(!foundSpecial) {
            Toast.makeText(this, "Your password does not any special letter", Toast.LENGTH_LONG).show();
            return false;
        }
        else return true;
    }

    /**
     *This function is used to check parameter c has special character or not.
     * @param c this parameter is used to check if have the special letter.
     * @ return true when the parameter has special letter otherwise return false
     */
    boolean isSpecialCharacter(char c)
    {
        switch (c)
        {
            case '#':
            case '?':
            case '*':
            case '!':
            case '&':
            case '@':
            case '%':
            case '^':
            case '$':
                return true;
            default:
                return false;
        }
    }
}