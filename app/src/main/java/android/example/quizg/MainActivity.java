package android.example.quizg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button trueButton;
    private Button falseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trueButton = (Button) findViewById(R.id.trueButton);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(MainActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP, 0,0);
                toast.show();

            }
        });

        falseButton = (Button) findViewById(R.id.falseButton);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(MainActivity.this,R.string.incorrect_toast, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP,0,0);
                toast.show();
            }
        });
    }
}