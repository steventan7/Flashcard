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

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase((getApplicationContext()));
        allFlashcards = flashcardDatabase.getAllCards();

        if (allFlashcards != null && allFlashcards.size() != 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }
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

        findViewById(R.id.plus).setOnClickListener(v -> {       // button that goes to addCardActivity
            Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
            MainActivity.this.startActivityForResult(intent,100);
        });

        findViewById(R.id.edit).setOnClickListener(v -> {       // button that allows you to edit the current card
            Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
            intent.putExtra("stringKey1", ((TextView) findViewById(R.id.flashcard_question)).getText().toString());
            intent.putExtra("stringKey2", ((TextView) findViewById(R.id.flashcard_answer)).getText().toString());
            MainActivity.this.startActivityForResult(intent,100);
        });

        // button that deletes current card
        findViewById(R.id.delete).setOnClickListener(v -> {
            if (allFlashcards.size() == 0) {
                ((TextView) findViewById(R.id.flashcard_question)).setText("Your list of cards is empty");
                ((TextView) findViewById(R.id.flashcard_answer)).setText("Your list of cards is empty");
            } else {
                flashcardDatabase.deleteCard(((TextView) findViewById(R.id.flashcard_question)).getText().toString());
                allFlashcards = flashcardDatabase.getAllCards();
                currentCardDisplayIndex++;
                if (allFlashcards.size() > 0) {
                    ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
                    ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
                }
            }
        });

        findViewById(R.id.next).setOnClickListener(v -> {           // button that goes to next card
            if (allFlashcards.size() == 0) {
                return;
            }
            currentCardDisplayIndex++;
            if (currentCardDisplayIndex >= allFlashcards.size()) {
                Snackbar.make(findViewById(R.id.flashcard_question),
                        "You've reached the end of the cards, going back to start.",
                        Snackbar.LENGTH_SHORT)
                        .show();
                currentCardDisplayIndex = 0;
            }
            allFlashcards = flashcardDatabase.getAllCards();
            Flashcard flashcard = allFlashcards.get(currentCardDisplayIndex);

            ((TextView) findViewById(R.id.flashcard_question)).setText(flashcard.getAnswer());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(flashcard.getQuestion());
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
            flashcardDatabase.insertCard(new Flashcard(q,a));
            allFlashcards = flashcardDatabase.getAllCards();
        }

    }

}