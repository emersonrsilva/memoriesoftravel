package com.cedro.memoriesoftravel.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cedro.memoriesoftravel.model.CountryModel;
import com.cedro.memoriesoftravel.util.ImageLoader;
import com.cedro.memoriesoftravel.view.activity.CountryInfoActivity;
import com.cedro.memoriesoftravel.view.fragment.CountryFragment;
import com.cedro.memoriesoftravel.view.fragment.VisitedCountryFragment;
import com.memoriesoftravel.R;

import java.util.List;

/**
 * Created by emerson on 06/10/16.
 */

public class VisitedCountryFragmentAdapter extends ArrayAdapter<CountryModel> {


    private Activity activity;

    private int row;
    public ImageLoader imageLoader;

    private VisitedCountryFragment view;
    public List<CountryModel> countryList;
    private CountryModel countryModel;

    public VisitedCountryFragmentAdapter(VisitedCountryFragment view, int resource, List<CountryModel> countryList) {
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
        holder.checkbox = (CheckBox) view.findViewById(R.id.country_list_item_selectedCheckBox);
        imageLoader.exibirImagem(countryModel.getImage().toString(), holder.imgv_latetst);
        holder.name.setText(countryModel.getShortname().toString()+" - "+countryModel.getIso().toString() );

        holder.desc.setText(countryModel.getLongname().toString() );
        holder.checkbox.setVisibility(View.VISIBLE);
        view.setTag(holder);

        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = (CheckBox) view ;
                CountryModel country = (CountryModel) cb.getTag();
                country.setSelected(cb.isChecked());
            }
        });
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Intent intnet = new Intent("CHECK_VISITED");
                intnet.putExtra("action",b);
                getContext().sendBroadcast(intnet);

            }
        });

        holder.checkbox.setText("");
        holder.checkbox.setChecked(countryModel.isSelected());
        holder.checkbox.setTag(countryModel);



        return view;

    }

    public class ViewHolder {

        public ImageView imgv_latetst;
        public  TextView name,desc;
        public CheckBox checkbox;

    }

}
