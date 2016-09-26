package com.free.videofilter;

import com.lansosdk.box.*;
import com.lansosdk.videoeditor.*;

import java.io.*;

import android.app.*;
import android.content.*;
import android.media.*;
import android.media.MediaPlayer.*;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class MainActivity extends Activity {
    private FilterView filterView;
    private String mVideoPath = "/storage/emulated/0/DCIM/Video/V60919-153509.mp4";
    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        filterView = (FilterView) findViewById(R.id.view_mtv);
        findViewById(R.id.start_button).setOnClickListener(listener);
        mediaPlayer = new MediaPlayer();

//        try {
//            mediaPlayer.setDataSource(mVideoPath);
//            mediaPlayer.prepareAsync();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
//
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                // TODO Auto-generated method stub
//                start(mp);
//            }
//        });
    }


    private void startPlayVideo() {
//            mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
//
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    // TODO Auto-generated method stub
//                    if(mFilterView.isRunning()){
//                        mFilterView.stop();
//
//                        toastStop();
//
//                        if(SDKFileUtils.fileExist(editTmpPath)){
//                            boolean ret=VideoEditor.encoderAddAudio(mVideoPath,editTmpPath,SDKDir.TMP_DIR,dstPath);
//                            if(!ret){
//                                dstPath=editTmpPath;
//                            }else{
//                                SDKFileUtils.deleteFile(editTmpPath);
//                            }
//                        }
//                    }
//                }
//            });


    }


    private void start(MediaPlayer mp) {
        MediaInfo info = new MediaInfo(mVideoPath);
        info.prepare();

//        if(DemoCfg.ENCODE){
//            filterView.setRealEncodeEnable(1000*1000,(int)info.vFrameRate,editTmpPath);
//        }

        //这里设置为等比例滤镜,实际int glwidth,int glheight的值可任意设置, 短视频一般是480x480的居多.
        filterView.setFilterRenderSize(480, 480, mp.getVideoWidth(), mp.getVideoHeight(), new onFilterViewSizeChangedListener() {

            @Override
            public void onSizeChanged(int viewWidth, int viewHeight) {
                // TODO Auto-generated method stub
                filterView.start();

                mediaPlayer.setSurface(filterView.getSurface());
                mediaPlayer.start();
            }
        });
    }


    OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.start_button:
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this,FilterRealTimeActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

}
