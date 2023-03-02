package algonquin.cst2335.ni000017;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    }


}