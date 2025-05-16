
package com.example.probalitycalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsFragment extends Fragment {

    private EditText editTextData;
    private Button buttonCalculateStatistics;
    private BarChart barChartStatistics;
    private TextView textViewMean, textViewMedian, textViewVariance, textViewStandardDeviation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        editTextData = view.findViewById(R.id.editTextData);
        buttonCalculateStatistics = view.findViewById(R.id.buttonCalculateStatistics);
        barChartStatistics = view.findViewById(R.id.barChartStatistics);
        textViewMean = view.findViewById(R.id.textViewMean);
        textViewMedian = view.findViewById(R.id.textViewMedian);
        textViewVariance = view.findViewById(R.id.textViewVariance);
        textViewStandardDeviation = view.findViewById(R.id.textViewStandardDeviation);

        buttonCalculateStatistics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextData == null || textViewMean == null || textViewMedian == null ||
                        textViewVariance == null || textViewStandardDeviation == null || barChartStatistics == null) {
                    return; // Проверка, что все View инициализированы
                }

                String dataString = editTextData.getText().toString();
                String[] dataValues = dataString.split(",");
                List<Double> data = new ArrayList<>();
                try {
                    for (String value : dataValues) {
                        data.add(Double.parseDouble(value.trim()));
                    }
                } catch (NumberFormatException e) {
                    textViewMean.setText("Некорректный ввод данных");
                    textViewMedian.setText("");
                    textViewVariance.setText("");
                    textViewStandardDeviation.setText("");
                    barChartStatistics.clear(); // Очистить график
                    return;
                }

                if (!data.isEmpty()) {
                    double mean = StatisticsUtils.calculateMean(data);
                    double median = StatisticsUtils.calculateMedian(data);
                    double variance = StatisticsUtils.calculateVariance(data);
                    double standardDeviation = StatisticsUtils.calculateStandardDeviation(data);

                    textViewMean.setText("Среднее: " + String.format("%.2f", mean)); // Форматирование вывода
                    textViewMedian.setText("Медиана: " + String.format("%.2f", median));
                    textViewVariance.setText("Дисперсия: " + String.format("%.2f", variance));
                    textViewStandardDeviation.setText("Стандартное отклонение: " + String.format("%.2f", standardDeviation));

                    createHistogram(barChartStatistics, data);
                } else {
                    textViewMean.setText("Введите данные");
                    textViewMedian.setText("");
                    textViewVariance.setText("");
                    textViewStandardDeviation.setText("");
                    barChartStatistics.clear(); // Очистить график
                }
            }
        });

        return view;
    }

    private void createHistogram(BarChart chart, List<Double> data) {
        if (data.isEmpty()) {
            chart.clear();
            return;
        }

        // Автоматический выбор количества бинов (минимум 2, максимум 7)
        int binCount = (int) (1 + 3.322 * Math.log10(data.size()));
        if (binCount < 2) binCount = 2;
        if (binCount > 7) binCount = 7;

        double min = Collections.min(data);
        double max = Collections.max(data);
        double binSize = (max - min) / binCount;

        // Считаем частоты
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (Double value : data) {
            int binIndex = (int) Math.floor((value - min) / binSize);
            if (binIndex >= binCount) binIndex = binCount - 1; // Последний бин
            frequencyMap.put(binIndex, frequencyMap.getOrDefault(binIndex, 0) + 1);
        }

        // Создаём столбцы
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < binCount; i++) {
            entries.add(new BarEntry(i, frequencyMap.getOrDefault(i, 0)));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Частота");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        BarData barData = new BarData(dataSet);
        chart.setData(barData);

        // Настройка оси X (подписи интервалов)
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(binCount);
        xAxis.setValueFormatter(new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                double start = min + (value * binSize);
                double end = start + binSize;
                return String.format("%.1f-%.1f", start, end);
            }
        });

        chart.getDescription().setText("Гистограмма");
        chart.animateY(1000);
        chart.invalidate();
    }
}
