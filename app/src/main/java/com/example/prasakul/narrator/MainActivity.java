package com.example.prasakul.narrator;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

public class MainActivity extends Activity{

    TextToSpeech t1;
    EditText ed1;
    Button b1 ,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1=(EditText)findViewById(R.id.editText);
        b1=(Button)findViewById(R.id.uploadText);
        b2=(Button)findViewById(R.id.text2speech);


        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = ed1.getText().toString();
                Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    public void getTextFromFile(final View view){
//        String yourFilePath = view.getContext().getFilesDir()+ "/" + "hello.txt";
//        File yourFile = new File( yourFilePath );
        FileChooser chooser=new FileChooser(MainActivity.this).setFileListener(new FileChooser.FileSelectedListener() {
            @Override public void fileSelected(final File file) {
//                Toast.makeText(MainActivity.this, "Chosen FileOpenDialog File: " +file, Toast.LENGTH_LONG).show();
                readTextFromFile(file);
            }});
        chooser.setExtension(".txt");
        chooser.refresh(Environment.getExternalStorageDirectory());
        chooser.showDialog();

    }
    private  void readTextFromFile(final File yourFilePath ){
        StringBuilder sb= new StringBuilder();

//        try {
//            BufferedReader br = new BufferedReader(new FileReader(yourFilePath));
//            String line;
//
//            while ((line = br.readLine()) != null) {
//                if(line.length() > 0){
//                    sb.append(line).append("\n");
//                }else{
//                    sb.append("\n");
//                }
//            }
//            br.close();
//        } catch (FileNotFoundException e) {
//            sb.append( "Exception");
//        } catch (UnsupportedEncodingException e) {
//            sb.append( "Exception");
//        } catch (IOException e) {
//            sb.append( "Exception");
//        }finally {
//
//        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(yourFilePath));
            sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }

        } catch (FileNotFoundException e) {
            sb.append( "Exception");
        } catch (UnsupportedEncodingException e) {
            sb.append( "Exception");
        } catch (IOException e) {
            sb.append( "Exception");
        }
        ed1.setText(sb.toString());
    }
    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }
}
