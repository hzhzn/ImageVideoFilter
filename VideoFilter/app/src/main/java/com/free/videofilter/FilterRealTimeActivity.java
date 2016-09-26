package com.free.videofilter;

import java.io.IOException;
import com.free.videofilter.GPUImageFilterTools.FilterAdjuster;
import com.free.videofilter.GPUImageFilterTools.OnGpuImageFilterChosenListener;
import com.free.videofilter.imagfilter.*;
import com.lansosdk.box.onFilterViewSizeChangedListener;
import com.lansosdk.videoeditor.*;
import android.graphics.*;
import android.widget.*;
import jp.co.cyberagent.lansongsdk.gpuimage.*;
import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import jp.co.cyberagent.lansongsdk.gpuimage.GPUImageFilter;
import jp.co.cyberagent.lansongsdk.gpuimage.GPUImageLookupFilter;
import jp.co.cyberagent.lansongsdk.gpuimage.GPUImageSepiaFilter;

/**
 * 演示单独使用FilterView来做视频滤镜处理.
 * FilterView 是单独用来视频滤镜的
 */
public class FilterRealTimeActivity extends Activity {
    private String mVideoPath = "/storage/emulated/0/DCIM/Video/V60919-153509.mp4";
    private FilterView mFilterView;
    private MediaPlayer mPlayer;
    private boolean mBackPressed;
    private SeekBar skbarFilterAdjuster;
    private String editTmpPath = null;
    private String dstPath = null;
    private GPUImageViewFilter yuanpianImageView,xiandaiImageView,fangkeImageView,dongbuImageView,
            xibuImageView,rihanImageView,heibaiImageView,laopaiImageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new snoCrashHandler());
        setContentView(R.layout.filter_player_layout);
        buildImageView();
        mFilterView = (FilterView) findViewById(R.id.video_view);
        skbarFilterAdjuster = (SeekBar) findViewById(R.id.id_player_seekbar1);
        skbarFilterAdjuster.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // TODO Auto-generated method stub
                if (mFilterAdjuster != null) {
                    mFilterAdjuster.adjust(progress);
                }
            }
        });
        skbarFilterAdjuster.setMax(100);
        findViewById(R.id.id_player_btnselectfilter).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                selectFilter();
            }
        });
        //在手机的/sdcard/lansongBox/路径下创建一个文件名,用来保存生成的视频文件,(在onDestroy中删除)
        editTmpPath = SDKFileUtils.newMp4PathInBox();
        dstPath = SDKFileUtils.newMp4PathInBox();

        //增加提示缩放到480的文字.
//        DemoUtils.showScale480HintDialog(FilterRealTimeActivity.this);


    }

    private void buildImageView(){
        yuanpianImageView = (GPUImageViewFilter) findViewById(R.id.yuanpian_image);
        xiandaiImageView = (GPUImageViewFilter) findViewById(R.id.xiandai_image_1);
        rihanImageView  = (GPUImageViewFilter) findViewById(R.id.rihan_image);
        fangkeImageView = (GPUImageViewFilter)findViewById(R.id.fangke_image);
        dongbuImageView = (GPUImageViewFilter) findViewById(R.id.dongbu_image);
        heibaiImageView = (GPUImageViewFilter) findViewById(R.id.heibai_image);
        xibuImageView = (GPUImageViewFilter) findViewById(R.id.xibu_image);
        laopaiImageView = (GPUImageViewFilter) findViewById(R.id.laopai_image);
        yuanpianImageView.setFilterType(0);
        xiandaiImageView.setFilterType(1);
        rihanImageView.setFilterType(2);
        fangkeImageView.setFilterType(3);
        dongbuImageView.setFilterType(4);
        heibaiImageView.setFilterType(5);
        xibuImageView.setFilterType(6);
        laopaiImageView.setFilterType(7);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                startPlayVideo();
            }
        }, 1000);
    }

    private void startPlayVideo() {
        if (mVideoPath != null) {
            mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(mVideoPath);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mPlayer.setOnPreparedListener(new OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    start(mp);
                }
            });
            mPlayer.setOnCompletionListener(new OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    if (mFilterView.isRunning()) {
//                        mFilterView.stop();
//                        if (SDKFileUtils.fileExist(editTmpPath)) {
//                            boolean ret = VideoEditor.encoderAddAudio(mVideoPath, editTmpPath, SDKDir.TMP_DIR, dstPath);
//                            if (!ret) {
//                                dstPath = editTmpPath;
//                            } else {
//                                SDKFileUtils.deleteFile(editTmpPath);
//                            }
//                        }

                        mPlayer.start();

                    }
                }
            });
            mPlayer.prepareAsync();
        }
    }



    private void start(MediaPlayer mp) {
//        MediaInfo info = new MediaInfo(mVideoPath);
//        info.prepare();

//        if (DemoCfg.ENCODE) {
//            mFilterView.setRealEncodeEnable(1000 * 1000, (int) info.vFrameRate, editTmpPath);
//        }

        //这里设置为等比例滤镜,实际int glwidth,int glheight的值可任意设置, 短视频一般是480x480的居多.
        mFilterView.setFilterRenderSize(480, 480, mp.getVideoWidth(), mp.getVideoHeight(), new onFilterViewSizeChangedListener() {

            @Override
            public void onSizeChanged(int viewWidth, int viewHeight) {
                // TODO Auto-generated method stub
                mFilterView.start();

                mPlayer.setSurface(mFilterView.getSurface());
                mPlayer.start();
            }
        });
    }

    @Override
    public void onBackPressed() {
        mBackPressed = true;
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBackPressed) {
            mFilterView.stop();

            if (mPlayer != null) {
                mPlayer.stop();
                mPlayer.release();
                mPlayer = null;
            }

        }

    }

    private FilterAdjuster mFilterAdjuster;

    private void selectFilter() {
        GPUImageFilterTools.showDialog(this, new OnGpuImageFilterChosenListener() {

            @Override
            public void onGpuImageFilterChosenListener(final GPUImageFilter filter) {

                if (mFilterView.switchFilterTo(filter))//<----------------------//在这里切换滤镜
                {
                    mFilterAdjuster = new FilterAdjuster(filter);

                    findViewById(R.id.id_player_seekbar1).setVisibility(mFilterAdjuster.canAdjust() ? View.VISIBLE : View.GONE);
                }
            }
        });
    }




    public void bottomClick(View v){
        switch (v.getId()){
            case R.id.yuanpian_button:
                break;
            case R.id.xiandai_button:
                GPUImageLookupFilter xiandaiFilter = new GPUImageLookupFilter();
                xiandaiFilter.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.lookup_miss_etikate));
                mFilterView.switchFilterTo(xiandaiFilter);
                break;
            case R.id.rihan_button:
                GPUImageLookupFilter rihanFilter = new GPUImageLookupFilter();
                rihanFilter.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.lookup_soft_elegance_2));
                mFilterView.switchFilterTo(rihanFilter);
                break;
            case R.id.fangke_button:
                GPUImageLookupFilter fangkeFilter = new GPUImageLookupFilter();
                fangkeFilter.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.lookup_effect_1));
                mFilterView.switchFilterTo(fangkeFilter);
                break;
            case R.id.dongbu_button:
                GPUImageLookupFilter dongbuFilter = new GPUImageLookupFilter();
                dongbuFilter.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.lookup_lomo));
                mFilterView.switchFilterTo(dongbuFilter);
                break;
            case R.id.heibai_button:
                GPUImageGrayscaleFilter heibaiFilter = new GPUImageGrayscaleFilter();
                mFilterView.switchFilterTo(heibaiFilter);
                break;
            case R.id.xibu_button:
                GPUImageLookupFilter xibuFilter = new GPUImageLookupFilter();
                xibuFilter.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lookup_amatorka));
                mFilterView.switchFilterTo(xibuFilter);
                break;
            case R.id.laopai_button:
                GPUImageSepiaFilter laopaiFilter = new GPUImageSepiaFilter();
                mFilterView.switchFilterTo(laopaiFilter);
                break;
        }
    }
}
