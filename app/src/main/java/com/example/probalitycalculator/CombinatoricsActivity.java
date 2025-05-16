package com.example.probalitycalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigInteger;

public class CombinatoricsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combinatorics);

        EditText editTextN = findViewById(R.id.editTextN);
        EditText editTextK = findViewById(R.id.editTextK);
        RadioGroup radioGroup = findViewById(R.id.radioGroupCombinatorics);
        Button buttonCalculate = findViewById(R.id.buttonCalculateCombinatorics);
        TextView textViewResult = findViewById(R.id.textViewResultCombinatorics);

        buttonCalculate.setOnClickListener(v -> {
            try {
                int n = Integer.parseInt(editTextN.getText().toString());
                int k = 0;

                // Для перестановок k не нужен
                if (radioGroup.getCheckedRadioButtonId() != R.id.radioButtonPermutations) {
                    k = Integer.parseInt(editTextK.getText().toString());
                }

                if (n < 0) {
                    textViewResult.setText("Ошибка: n должно быть ≥ 0");
                    return;
                }

                // Проверка k для размещений и сочетаний
                if (radioGroup.getCheckedRadioButtonId() != R.id.radioButtonPermutations) {
                    if (k < 0) {
                        textViewResult.setText("Ошибка: k должно быть ≥ 0");
                        return;
                    }
                    if (k > n) {
                        textViewResult.setText("Ошибка: k не может быть > n");
                        return;
                    }
                }

                BigInteger result;
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == R.id.radioButtonArrangements) {
                    result = CombinatoricsUtils.calculateArrangements(n, k);
                } else if (selectedId == R.id.radioButtonCombinations) {
                    result = CombinatoricsUtils.calculateCombinations(n, k);
                } else if (selectedId == R.id.radioButtonPermutations) {
                    result = CombinatoricsUtils.calculatePermutations(n);
                } else {
                    textViewResult.setText("Выберите тип расчёта!");
                    return;
                }

                textViewResult.setText("Результат: " + result);

            } catch (NumberFormatException e) {
                textViewResult.setText("Ошибка: введите целые числа");
            } catch (IllegalArgumentException e) {
                textViewResult.setText("Ошибка: " + e.getMessage());
            }
        });
    }
}