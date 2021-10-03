package com.example.lab11_file;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

    EditText textBox;
    private final static String FILE_NAME = "content.txt"; // имя файла

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        int id = item.getItemId();
        switch(id){
            case R.id.size8:
                Toast.makeText(this, "size8", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.size9:
                Toast.makeText(this, "size9", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.size10:
                Toast.makeText(this, "size10", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.red:
                Toast.makeText(this, "red", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.green:
                Toast.makeText(this, "green", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.blue:
                Toast.makeText(this, "blue", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Сохранение файла FileWriter
    public void saveTextFileWriter(View view) {

        // Получаем текст из EditBox
        textBox = (EditText) findViewById(R.id.editor);
        String text = textBox.getText().toString();

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
        textBox = (EditText) findViewById(R.id.editor);
        String text = textBox.getText().toString();

        BufferedWriter out = null;
        try {

            File file = new File(getFilesDir(), FILE_NAME);

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

        File file = new File(getFilesDir(), FILE_NAME);

        // Получаем текст из EditBox
        textBox = (EditText) findViewById(R.id.editor);
        String text = textBox.getText().toString();

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
            textBox = (EditText) findViewById(R.id.editor);
            String text = textBox.getText().toString();

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
        TextView textView = (TextView) findViewById(R.id.text);
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
        TextView textView = (TextView) findViewById(R.id.text);
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