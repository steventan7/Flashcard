package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        String s1 = getIntent().getStringExtra("stringKey1");
        String s2 = getIntent().getStringExtra("stringKey2");
        EditText q_edit = findViewById(R.id.edit_question);
        EditText a_edit = findViewById(R.id.edit_answer);
        q_edit.setText(s1);
        a_edit.setText(s2);

        findViewById((R.id.exit)).setOnClickListener(v -> {
            finish();
        });

        findViewById((R.id.save)).setOnClickListener(v -> {
            Intent data = new Intent();
            String question = ((EditText) findViewById(R.id.edit_question)).getText().toString();
            String answer = ((EditText) findViewById(R.id.edit_answer)).getText().toString();
            if (question.equals("") || answer.equals("")) {
                showToast("Question and Answer both required!");
            } else {
                data.putExtra("string1", question);
                data.putExtra("string2", answer);
                setResult(RESULT_OK,data);
                finish();
            }
        });
    }
    void showToast(String message) {

        Toast toast = new Toast(AddCardActivity.this);

        View view = LayoutInflater.from(AddCardActivity.this)
                .inflate(R.layout.toast_layout, null);

        TextView tvMessage = view.findViewById(R.id.text);
        tvMessage.setText(message);

        toast.setView(view);
        toast.show();
    }
}