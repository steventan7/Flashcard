package com.example.flashcard;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView question = findViewById(R.id.flashcard_question);
        TextView answer = findViewById(R.id.flashcard_answer);
        question.setOnClickListener(v -> {
            question.setVisibility(View.INVISIBLE);
            answer.setVisibility(View.VISIBLE);
        });
        answer.setOnClickListener(v -> {
            question.setVisibility(View.VISIBLE);
            answer.setVisibility(View.INVISIBLE);
        });

        findViewById(R.id.plus).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
            MainActivity.this.startActivityForResult(intent,100);
        });

        findViewById(R.id.edit).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
            intent.putExtra("stringKey1", ((TextView) findViewById(R.id.flashcard_question)).getText().toString());
            intent.putExtra("stringKey2", ((TextView) findViewById(R.id.flashcard_answer)).getText().toString());
            MainActivity.this.startActivityForResult(intent,100);
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            String q = data.getExtras().getString("string1");
            String a = data.getExtras().getString("string2");
            TextView question = findViewById(R.id.flashcard_question);
            TextView answer = findViewById(R.id.flashcard_answer);
            question.setText(q);
            answer.setText(a);
            Snackbar.make(findViewById(R.id.flashcard_question),
                    "Card has been successfully created!",
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
    }
}