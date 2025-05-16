package com.example.probalitycalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SingleEventsActivity extends AppCompatActivity {

    EditText editTextProbabilityA;
    EditText editTextProbabilityB;
    EditText editTextProbabilityAandB;
    RadioGroup radioGroupOperation;
    Button buttonCalculate;
    TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_events);

        editTextProbabilityA = findViewById(R.id.editTextProbabilityA);
        editTextProbabilityB = findViewById(R.id.editTextProbabilityB);
        editTextProbabilityAandB = findViewById(R.id.editTextProbabilityAandB);
        radioGroupOperation = findViewById(R.id.radioGroupOperation);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        textViewResult = findViewById(R.id.textViewResult);


        buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double probabilityA = 0.0;
                double probabilityB = 0.0;
                double probabilityAandB = 0.0;

                int selectedOperationId = radioGroupOperation.getCheckedRadioButtonId();

                // Обработка операций "И" и "ИЛИ"
                if (selectedOperationId == R.id.radioButtonAnd || selectedOperationId == R.id.radioButtonOr) {
                    try {
                        probabilityA = Double.parseDouble(editTextProbabilityA.getText().toString());
                        probabilityB = Double.parseDouble(editTextProbabilityB.getText().toString());
                    } catch (NumberFormatException e) {
                        textViewResult.setText("Некорректный ввод для P(A) или P(B)");
                        return;
                    }

                    if (probabilityA > 1 || probabilityA < 0 || probabilityB > 1 || probabilityB < 0) {
                        textViewResult.setText("Вероятности P(A) и P(B) должны быть от 0 до 1.");
                        return;
                    }
                }

                // Обработка условной вероятности
                else if (selectedOperationId == R.id.radioButtonConditional) {
                    try {
                        probabilityAandB = Double.parseDouble(editTextProbabilityAandB.getText().toString());
                        probabilityB = Double.parseDouble(editTextProbabilityB.getText().toString());
                    } catch (NumberFormatException e) {
                        textViewResult.setText("Некорректный ввод для P(A и B) или P(B)");
                        return;
                    }

                    if (probabilityB == 0) {
                        textViewResult.setText("Вероятность P(B) не может быть равна 0 для условной вероятности.");
                        return;
                    }

                    if (probabilityAandB > 1 || probabilityAandB < 0) {
                        textViewResult.setText("Вероятность P(A и B) должна быть от 0 до 1.");
                        return;
                    }
                }
                // Если ни одна кнопка не выбрана
                else {
                    textViewResult.setText("Выберите операцию");
                    return;
                }


                double result = 0.0;

                if (selectedOperationId == R.id.radioButtonAnd) {
                    result = ProbabilityUtils.calculateAndProbability(probabilityA, probabilityB);
                } else if (selectedOperationId == R.id.radioButtonOr) {
                    result = ProbabilityUtils.calculateOrProbability(probabilityA, probabilityB);
                }
                else if (selectedOperationId == R.id.radioButtonConditional) {
                    result = ProbabilityUtils.calculateConditionalProbability(probabilityAandB, probabilityB);
                }
                else {
                    return; // Ничего не делаем, если ни одна операция не выбрана
                }


                textViewResult.setText("Результат: " + result);
            }
        });
    }
}
