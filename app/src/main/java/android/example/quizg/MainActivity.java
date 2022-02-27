package android.example.quizg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";

    private Button trueButton;
    private Button falseButton;
    private Button previousButton;
    private Button nextButton;
    private TextView questionTextView;

    private Question[] questionBank = new Question[] {
            new Question(R.string.question_Australia, true),
            new Question(R.string.question_MidEast, true),
            new Question(R.string.question_Ocean, true),
            new Question(R.string.question_america, true),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_africa, true),
    };

    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            currentIndex =savedInstanceState.getInt(KEY_INDEX, 0);
        }

        questionTextView = (TextView) findViewById(R.id.question_text_view);


        previousButton = (Button) findViewById(R.id.previous_button);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex - 1) % questionBank.length;
                updateQuestion();
            }
        });

        nextButton = (Button) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1 ) % questionBank.length;
                updateQuestion();
            }
        });

        updateQuestion();

        trueButton = (Button) findViewById(R.id.trueButton);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        falseButton = (Button) findViewById(R.id.falseButton);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, currentIndex);
    }

    private void updateQuestion(){
        int question = questionBank[currentIndex].getTextResId();
        questionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = questionBank[currentIndex].isAnswerTrue();

        int messageResId = 0;

        if(userPressedTrue == answerIsTrue){
            messageResId = R.string.correct_toast;
        }else{
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}