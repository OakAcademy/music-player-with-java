package com.ongel.mp3playertest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MusicAdapter adapter;
    private final ArrayList<String> songList = new ArrayList<>();
    private final static String MEDIA_PATH = Environment.getExternalStorageDirectory().getPath()+"/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(Build.VERSION.SDK_INT >= 33){

            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_MEDIA_AUDIO)
                    != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(MainActivity.this
                        ,new String[]{Manifest.permission.READ_MEDIA_AUDIO},1);
            }
            else
            {
                getAllAudioFiles();
            }

        }else {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(MainActivity.this
                        ,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }
            else
            {
                getAllAudioFiles();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            getAllAudioFiles();
        }
    }

    public void getAllAudioFiles() {

        if (MEDIA_PATH != null) {

            File home = new File(MEDIA_PATH);

            File[] listFiles = home.listFiles();

            if (listFiles != null && listFiles.length > 0) {

                for (File file : listFiles) {

                    Log.e("MediaPath-4 : ",file.toString());

                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {

                        final String path = file.getAbsolutePath();

                        Log.e("MediaPath-5 : ",path);

                        if (path.endsWith(".mp3") || path.endsWith(".MP3") || path.endsWith(".wav"))
                        {
                            songList.add(path);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }
            adapter = new MusicAdapter(songList, MainActivity.this);
            recyclerView.setAdapter(adapter);
        }

    }

    private void scanDirectory(File directory) {
        if (directory != null) {
            File[] listFiles = directory.listFiles();
            if (listFiles != null && listFiles.length > 0) {
                for (File file : listFiles) {
                    if (file.isDirectory()) {
                        scanDirectory(file);
                    } else {
                        final String path = file.getAbsolutePath();

                        Log.e("MediaPath-6 : ",path);

                        if (path.endsWith(".mp3") || path.endsWith(".MP3"))
                        {
                            songList.add(path);
                        }
                    }
                }
            }
        }
    }
}