package com.example.android.miwok;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UniversalAdapter extends ArrayAdapter<UniversalObject>{

    private int color;

    public UniversalAdapter(Context context, ArrayList<UniversalObject> objects, int color) {
        super(context, 0, objects);
        this.color = color;
    }

    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        UniversalObject universalObject = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }

        ImageView miwok_image_view = (ImageView) convertView.findViewById(R.id.miwok_image_veiw);
        TextView miwok_text_view = (TextView) convertView.findViewById(R.id.miwok_text_view);
        TextView default_text_view = (TextView) convertView.findViewById(R.id.default_text_view);

        miwok_text_view.setText(universalObject.getmMiwokTranslation());
        default_text_view.setText(universalObject.getmDefaultTranslation());
        convertView.setBackgroundResource(color);

        if (universalObject.hasImage()) {
            miwok_image_view.setImageResource(universalObject.getmImageResouseId());
            miwok_image_view.setVisibility(View.VISIBLE);
        }
           else {
            miwok_image_view.setVisibility(View.GONE);
        }



        return convertView;
    }
}
