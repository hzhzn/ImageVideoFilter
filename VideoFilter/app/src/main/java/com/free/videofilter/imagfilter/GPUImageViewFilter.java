package com.free.videofilter.imagfilter;

import com.free.videofilter.*;

import android.content.*;
import android.graphics.*;
import android.util.*;
import android.widget.*;

/**
 * Created by huzhenning on 16/9/23.
 */

public class GPUImageViewFilter extends ImageView {

    private GPUImage gpuImage = null;

    public GPUImageViewFilter(Context context) {
        super(context);
        initView(context);
    }

    public GPUImageViewFilter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    private void initView(Context mContext){
        gpuImage = new GPUImage(mContext);
        gpuImage.setImage(BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.nofilter));
    }


    public void setFilterType(int type){
        switch (type){
            case 0:
                GPUImageFilter gpuImageFilter = new GPUImageFilter();
                gpuImage.setFilter(gpuImageFilter);
                this.setImageBitmap(gpuImage.getBitmapWithFilterApplied());
                break;
            case 1:
                GPUImageLookupFilter xiandaiFilter = new GPUImageLookupFilter();
                xiandaiFilter.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lookup_miss_etikate));
                gpuImage.setFilter(xiandaiFilter);
                this.setImageBitmap(gpuImage.getBitmapWithFilterApplied());
                break;
            case 2:
                GPUImageLookupFilter rihanFilter = new GPUImageLookupFilter();
                rihanFilter.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lookup_soft_elegance_2));
                gpuImage.setFilter(rihanFilter);
                this.setImageBitmap(gpuImage.getBitmapWithFilterApplied());
                break;
            case 3:
                GPUImageLookupFilter fangkeFilter = new GPUImageLookupFilter();
                fangkeFilter.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lookup_effect_1));
                gpuImage.setFilter(fangkeFilter);
                this.setImageBitmap(gpuImage.getBitmapWithFilterApplied());
                break;
            case 4:
                GPUImageLookupFilter dongbuFilter = new GPUImageLookupFilter();
                dongbuFilter.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lookup_lomo));
                gpuImage.setFilter(dongbuFilter);
                this.setImageBitmap(gpuImage.getBitmapWithFilterApplied());
                break;
            case 5:
                GPUImageLookupFilter heibaiFilter = new GPUImageLookupFilter();
                heibaiFilter.setBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.lookup_blackwhite));
                gpuImage.setFilter(heibaiFilter);
                this.setImageBitmap(gpuImage.getBitmapWithFilterApplied());
                break;
            case 6:
                GPUImageLookupFilter xibuFilter = new GPUImageLookupFilter();
                xibuFilter.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.lookup_amatorka));
                gpuImage.setFilter(xibuFilter);
                this.setImageBitmap(gpuImage.getBitmapWithFilterApplied());
                break;
            case 7:
                GPUImageSepiaFilter gpuImageSepiaFilter = new GPUImageSepiaFilter();
                gpuImage.setFilter(gpuImageSepiaFilter);
                this.setImageBitmap(gpuImage.getBitmapWithFilterApplied());
                break;
        }
    }
}
