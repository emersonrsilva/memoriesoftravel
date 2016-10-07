package com.cedro.memoriesoftravel.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import com.cedro.memoriesoftravel.model.CountryModel;
import com.cedro.memoriesoftravel.util.ImageLoader;
import com.cedro.memoriesoftravel.view.fragment.CountryFragment;
import com.memoriesoftravel.R;

/**
 * Created by emerson on 06/10/16.
 */

public class VisitedCountryFragmentAdapter extends ArrayAdapter<CountryModel> {


    private Activity activity;

    private int row;
    public ImageLoader imageLoader;

    private CountryFragment view;
    private List<CountryModel> countryList;
    private CountryModel countryModel;

    public VisitedCountryFragmentAdapter(CountryFragment view, int resource, List<CountryModel> countryList) {
        super(view.getActivity(), resource, countryList);
        this.activity = view.getActivity();
        this.view = view;
        this.row = resource;
        this.countryList = countryList;

        imageLoader=new ImageLoader(activity);

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        VisitedCountryFragmentAdapter.ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);

            holder = new VisitedCountryFragmentAdapter.ViewHolder();
            view.setTag(holder);
        } else {
            holder = (VisitedCountryFragmentAdapter.ViewHolder) view.getTag();
        }

        if ((countryList == null) || ((position + 1) > countryList.size()))
            return view;

        countryModel = countryList.get(position);


        holder.imgv_latetst=(ImageView)view.findViewById(R.id.imageView1);
        holder.name=(TextView)view.findViewById(R.id.textView1);
        holder.desc=(TextView)view.findViewById(R.id.textView2);
        holder.imgv_latetst.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageLoader.exibirImagem(countryModel.getImage().toString(), holder.imgv_latetst);
        holder.name.setText(countryModel.getShortname().toString()+" - "+countryModel.getIso().toString() );

        holder.desc.setText(countryModel.getLongname().toString() );


        return view;

    }

    public class ViewHolder {

        public ImageView imgv_latetst;
        public  TextView name,desc;

    }

}
