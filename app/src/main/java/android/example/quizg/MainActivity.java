package android.example.quizg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private Button trueButton;
    private Button falseButton;
    private Button previousButton;
    private Button nextButton;
    private Button cheatButton;
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
    private boolean isCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            currentIndex =savedInstanceState.getInt(KEY_INDEX, 0);
        }

        questionTextView = (TextView) findViewById(R.id.question_text_view);

        cheatButton = (Button) findViewById(R.id.cheat_button);
        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue = questionBank[currentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

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
                isCheater = false;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            isCheater = CheatActivity.wasAnswerShown(data);
        }
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

        if(isCheater){
            messageResId = R.string.judgement_toast;
        }else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}