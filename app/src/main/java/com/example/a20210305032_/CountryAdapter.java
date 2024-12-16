package com.example.a20210305032_;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    private Context mContext;
    private List<String> countryList;
    private List<Integer> countryFlags;
    private OnCountrySelectedListener mListener;

    public interface OnCountrySelectedListener {
        void onCountrySelected(String country);
    }

    public CountryAdapter(Context context, List<String> countries, List<Integer> flags, OnCountrySelectedListener listener) {
        mContext = context;
        countryList = countries;
        countryFlags = flags;
        mListener = listener;
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_country, parent, false);
        return new CountryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, int position) {
        String country = countryList.get(position);
        holder.countryName.setText(country);
        holder.countryFlag.setImageResource(countryFlags.get(position));

        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onCountrySelected(country);
            }
        });
    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public static class CountryViewHolder extends RecyclerView.ViewHolder {
        TextView countryName;
        ImageView countryFlag;

        public CountryViewHolder(View itemView) {
            super(itemView);
            countryName = itemView.findViewById(R.id.countryNameTextView);
            countryFlag = itemView.findViewById(R.id.countryFlagImageView);
        }
    }
}
