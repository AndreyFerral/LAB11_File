package com.example.lab11_file;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    // Описание тэга для логов debug
    private static final String TAG = "myLogs";

    EditText editText;
    TextView textView;

    SharedPreferences settings;

    private final String SIZE = "SAVED_SIZE";
    private final String COLOR = "SAVED_COLOR";

    private int SAVED_SIZE;
    private int SAVED_COLOR;

    private final static String FILE_NAME = "content.txt"; // имя файла

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Получим значения из настроек
        // Второй параметр - значение по умолчанию
        settings = getPreferences(MODE_PRIVATE);
        SAVED_COLOR = settings.getInt(COLOR, Color.BLACK);
        SAVED_SIZE = settings.getInt(SIZE, 20);

        // Отправляем получаемые значения в LogCat
        Log.d(TAG, String.valueOf(SAVED_COLOR) + " - полученный цвет");
        Log.d(TAG, String.valueOf(SAVED_SIZE) + " - полученный шрифт");

        // Найдем компоненты в XML разметке
        editText = (EditText) findViewById(R.id.editor);
        textView = (TextView) findViewById(R.id.text);

        // Устанавливаем цвет и размер текста
        editText.setTextSize(SAVED_SIZE);
        textView.setTextSize(SAVED_SIZE);
        editText.setTextColor(SAVED_COLOR);
        textView.setTextColor(SAVED_COLOR);
    }

    // Созданию меню
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Обработчик нажатий на элементы меню
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Найдем компоненты в XML разметке
        editText = (EditText) findViewById(R.id.editor);
        textView = (TextView) findViewById(R.id.text);

        switch(item.getItemId())
        {
            case R.id.size15:

                SAVED_SIZE = 15;
                editText.setTextSize(SAVED_SIZE);
                textView.setTextSize(SAVED_SIZE);

                return true;

            case R.id.size20:

                SAVED_SIZE = 20;
                editText.setTextSize(SAVED_SIZE);
                textView.setTextSize(SAVED_SIZE);

                return true;

            case R.id.size25:

                SAVED_SIZE = 25;
                editText.setTextSize(SAVED_SIZE);
                textView.setTextSize(SAVED_SIZE);

                return true;

            case R.id.size30:

                SAVED_SIZE = 30;
                editText.setTextSize(SAVED_SIZE);
                textView.setTextSize(SAVED_SIZE);

                return true;

            case R.id.red:

                SAVED_COLOR = Color.RED;
                editText.setTextColor(SAVED_COLOR);
                textView.setTextColor(SAVED_COLOR);

                return true;

            case R.id.green:

                SAVED_COLOR = Color.GREEN;
                editText.setTextColor(SAVED_COLOR);
                textView.setTextColor(SAVED_COLOR);

                return true;

            case R.id.blue:

                SAVED_COLOR = Color.BLUE;
                editText.setTextColor(SAVED_COLOR);
                textView.setTextColor(SAVED_COLOR);

                return true;

            case R.id.black:

                SAVED_COLOR = Color.BLACK;
                editText.setTextColor(SAVED_COLOR);
                textView.setTextColor(SAVED_COLOR);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Сохраняем цвет и шрифт текста
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putInt(COLOR, SAVED_COLOR);
        prefEditor.putInt(SIZE, SAVED_SIZE);
        prefEditor.apply();

        Log.d(TAG, String.valueOf(SAVED_SIZE) + " - отправленный шрифт");
        Log.d(TAG, String.valueOf(SAVED_COLOR) + " - отправленный цвет");
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    // Сохранение файла FileWriter
    public void saveTextFileWriter(View view) {

        // Получаем текст из EditBox
        editText = (EditText) findViewById(R.id.editor);
        String text = editText.getText().toString();

        File file = new File(getFilesDir(), FILE_NAME);
        FileWriter fr = null;

        try {

            // true - добавление записи к файлу
            fr = new FileWriter(file, true);

            // Записываем текст в файл
            fr.write(text);

            // Записываем переход на следующую строку
            String newLine = System.getProperty("line.separator");
            fr.write(newLine);

        }
        catch (IOException ex) { Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show(); }
        finally
        {
            try { fr.close(); }
            catch (IOException ex) { Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show(); }
        }
        Log.d(TAG, "saveTextFileWriter - отработал");
    }

    // Сохранение файла BufferedWriter
    public void saveTextBufferedWriter(View view) {

        // Получаем текст из EditBox
        editText = (EditText) findViewById(R.id.editor);
        String text = editText.getText().toString();

        File file = new File(getFilesDir(), FILE_NAME);
        BufferedWriter out = null;

        try {

            // true - добавление записи к файлу
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));

            // Записываем текст в файл
            out.write(text);

            // Записываем переход на следующую строку
            out.newLine();

        }
        catch (Exception ex) { Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show(); }
        finally
        {
            try { out.close(); }
            catch (IOException ex) { Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show(); }
        }
        Log.d(TAG, "saveTextBufferedWriter - отработал");
    }

    // Сохранение файла Files
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void saveTextFiles(View view) {

        // Получаем текст из EditBox
        editText = (EditText) findViewById(R.id.editor);
        String text = editText.getText().toString();

        File file = new File(getFilesDir(), FILE_NAME);

        try {
            // Переход на следующую строку происходит автоматически
            if (file.exists()) {
                // StandardOpenOption.APPEND - добавление записи к файлу
                Files.write(Paths.get(file.getPath()), Collections.singleton(text), StandardOpenOption.APPEND);
            }
            else {
                Files.createFile(Paths.get(file.getPath()));
                Files.write(Paths.get(file.getPath()), Collections.singleton(text));
            }

        }
        catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Log.d(TAG, "saveTextFiles - отработал");
    }

    // Сохранение файла FileOutputStream
    public void saveTextFileOutputStream(View view) {

        FileOutputStream fos = null;

        try {
            // Получаем текст из EditBox
            editText = (EditText) findViewById(R.id.editor);
            String text = editText.getText().toString();

            // MODE_APPEND - добавление записи к файлу
            fos = openFileOutput(FILE_NAME, MODE_APPEND);

            // Записываем текст в файл
            fos.write(text.getBytes());

            // Записываем переход на следующую строку
            String newLine = System.getProperty("line.separator");
            fos.write(newLine.getBytes());

        }
        catch (IOException ex) { Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show(); }
        finally
        {
            try { if (fos!=null) fos.close(); }
            catch (IOException ex) { Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show(); }
        }

        Log.d(TAG, "saveTextFileOutputStream - отработал");
    }

    // Удаление файла
    public void deleteFile(View view){

        // Очищаем textView
        textView = (TextView) findViewById(R.id.text);
        textView.setText("");

        // Удаляем файл и отправляем подсказку
        File file = new File(getFilesDir(), FILE_NAME);
        if (file.delete()) Toast.makeText(this, "Файл удален", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Ошибка удаление - Файл уже удален", Toast.LENGTH_SHORT).show();

        Log.d(TAG, "deleteFile - отработал");
    }

    // Открытие файла
    public void openText(View view){

        FileInputStream fin = null;
        textView = (TextView) findViewById(R.id.text);

        try {
            fin = openFileInput(FILE_NAME);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String (bytes);
            textView.setText(text);

            // Подсказка
            Toast.makeText(this, "Файл открыт", Toast.LENGTH_SHORT).show();
        }
        catch (IOException ex) { Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show(); textView.setText(""); }
        finally {
            try { if(fin!=null) fin.close(); }
            catch (IOException ex) { Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show(); }
        }

        Log.d(TAG, "openText - отработал");
    }
}