package com.example.lab11_file;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText textBox;
    private final static String FILE_NAME = "content.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Сохранение файла FileWriter
    public void saveTextFileWriter(View view){
        Toast.makeText(this, "saveTextFileWriter", Toast.LENGTH_SHORT).show();
    }

    // Сохранение файла BufferedWriter
    public void saveTextBufferedWriter(View view){
        Toast.makeText(this, "saveTextBufferedWriter", Toast.LENGTH_SHORT).show();
    }

    // Сохранение файла Files
    public void saveTextFiles(View view){
        Toast.makeText(this, "saveTextFiles", Toast.LENGTH_SHORT).show();
    }

    // Сохранение файла FileOutputStream
    public void saveTextFileOutputStream(View view){

        FileOutputStream fos = null;
        try {
            // Получаем текст из EditBox
            textBox = (EditText) findViewById(R.id.editor);
            String text = textBox.getText().toString();

            File file = new File(getFilesDir(), FILE_NAME);
            if (file.exists()) {
                // MODE_APPEND - запись в конец файла
                fos = openFileOutput(FILE_NAME, MODE_APPEND);
                // Записываем переход на следующую строку
                String newLine = System.getProperty("line.separator");
                fos.write(newLine.getBytes());
            }
            else {
                // MODE_APPEND - запись в конец файла
                fos = openFileOutput(FILE_NAME, MODE_APPEND);
            }

            // Записываем текст в файл
            fos.write(text.getBytes());

            // Подсказка
            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
        }
        catch (IOException ex) { Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show(); }
        finally {
            try { if (fos!=null) fos.close(); }
            catch (IOException ex) { Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show(); }
        }
    }

    // Удаление файла
    public void deleteFile(View view){

        // Очищаем textView
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(""); //

        // Удаляем файл и отправляем подсказку
        File file = new File(getFilesDir(), FILE_NAME);
        if (file.delete()) Toast.makeText(this, "Файл удален", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Ошибка удаление - Файл уже удален", Toast.LENGTH_SHORT).show();
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
    }
}