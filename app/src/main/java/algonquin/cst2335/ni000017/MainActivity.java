package algonquin.cst2335.ni000017;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import algonquin.cst2335.ni000017.databinding.ActivityMainBinding;

/**
 *
 * @verion 1.0
 * @author Jianglian Ni
 */

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    protected RequestQueue queue;
    protected String cityName;
    protected Bitmap image =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        queue = Volley.newRequestQueue(this);
        binding.button.setOnClickListener(click ->{
            cityName =binding.editText.getText().toString();
            String stringURL =null;

            try {
                stringURL = "https://api.openweathermap.org/data/2.5/weather?q="
                        + URLEncoder.encode(cityName, "UTF-8")
                        + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL,null, (response) -> {
                    try
                    {
                        JSONObject coord = response.getJSONObject ("coord");
                        JSONArray weatherArray = response.getJSONArray ("weather");
                        JSONObject position0 =weatherArray.getJSONObject(0);
                        String description =position0.getString("description");
                        String iconName = position0.getString("icon");

                        Snackbar.make(binding.getRoot(),"iconName: " + iconName,Snackbar.LENGTH_LONG).show();

                        int vis =response.getInt("visibility");
                        String name = response.getString("name");
                        JSONObject mainObject =response.getJSONObject("main");
                        double current = mainObject.getDouble("temp");
                        double min = mainObject.getDouble("temp_min");
                        double max = mainObject.getDouble("temp_max");
                        int humidity =mainObject.getInt("humidity");
                        String pathname = getFilesDir() + "/" + iconName + ".png";
                        File file = new File(pathname);
                        if(file.exists())
                        {
                            image = BitmapFactory.decodeFile(pathname);
                        }

                        else{
                            ImageRequest imgReq = new ImageRequest("https://openweathermap.org/img/w/" + iconName + ".png", new Response.Listener<Bitmap>()
                            {
                            @Override
                                public void onResponse(Bitmap bitmap)
                                {
                                    image = bitmap;
                                    try {
                                        image.compress(Bitmap.CompressFormat.PNG, 100, MainActivity.this.openFileOutput(iconName + ".png", Activity.MODE_PRIVATE));

                                    } catch (FileNotFoundException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }, 1024, 1024,ImageView.ScaleType.CENTER, null, new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error){
                                    error.printStackTrace();
                                Snackbar.make(binding.getRoot(),"Error",Snackbar.LENGTH_LONG).show();
                                }
                            });
                            queue.add(imgReq);
                        }

                    runOnUiThread(() ->{
                    binding.temp.setText("The current temperature is"+ current);
                    binding.temp.setVisibility(View.VISIBLE);

                    binding.minTemp.setText("The min temperature is"+ min);
                    binding.minTemp.setVisibility(View.VISIBLE);

                    binding.maxTemp.setText("The max temperature is"+ max);
                    binding.maxTemp.setVisibility(View.VISIBLE);

                    binding.humitidy.setText("The humitidyis"+ humidity);
                    binding.humitidy.setVisibility(View.VISIBLE);

                    binding.icon.setImageBitmap(image);
                    binding.icon.setVisibility(View.VISIBLE);

                    binding.description.setText(description);
                    binding.description.setVisibility(View.VISIBLE);
                     });
                }catch(Exception e) {
                e.printStackTrace();
                }
             },
            (error)->{}
            );
            queue.add(request);
        });
    }
}