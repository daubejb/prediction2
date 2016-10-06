package com.daubedesign.prediction2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import edu.cmu.pocketsphinx.demo.R;

/**
 * Created by root on 9/27/16.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(171, 239));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);

        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.clubs_a, R.drawable.hearts_a, R.drawable.spades_a, R.drawable.diamonds_a,
            R.drawable.clubs_2, R.drawable.hearts_2, R.drawable.spades_2, R.drawable.diamonds_2,
            R.drawable.clubs_3, R.drawable.hearts_3, R.drawable.spades_3, R.drawable.diamonds_3,
            R.drawable.clubs_4, R.drawable.hearts_4, R.drawable.spades_4, R.drawable.diamonds_4,
            R.drawable.clubs_5, R.drawable.hearts_5, R.drawable.spades_5, R.drawable.diamonds_5,
            R.drawable.clubs_6, R.drawable.hearts_6, R.drawable.spades_6, R.drawable.diamonds_6,
            R.drawable.clubs_7, R.drawable.hearts_7, R.drawable.spades_7, R.drawable.diamonds_7,
            R.drawable.clubs_8, R.drawable.hearts_8, R.drawable.spades_8, R.drawable.diamonds_8,
            R.drawable.clubs_9, R.drawable.hearts_9, R.drawable.spades_9, R.drawable.diamonds_9,
            R.drawable.clubs_10, R.drawable.hearts_10, R.drawable.spades_10, R.drawable.diamonds_10,
            R.drawable.clubs_j, R.drawable.hearts_j, R.drawable.spades_j, R.drawable.diamonds_j,
            R.drawable.clubs_q, R.drawable.hearts_q, R.drawable.spades_q, R.drawable.diamonds_q,
            R.drawable.clubs_k, R.drawable.hearts_k, R.drawable.spades_k, R.drawable.diamonds_k,

    };
}

