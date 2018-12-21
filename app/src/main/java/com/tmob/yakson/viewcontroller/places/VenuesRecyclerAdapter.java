package com.tmob.yakson.viewcontroller.places;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tmob.michallange.R;
import com.tmob.yakson.interfaces.RecyclerItemClickListener;
import com.tmob.yakson.model.VenueModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VenuesRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int HOLDER_VENUE = 0;
    private final int HOLDER_SPACE = 1;

    private ArrayList<VenueModel> venueList = new ArrayList<>();
    private RecyclerItemClickListener recyclerItemClickListener;

    public static class VenueHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.venueNameTextView) TextView venueNameTextView;
        @BindView(R.id.venueAddressTextView) TextView venueAddressTextView;
        @BindView(R.id.venueCountryTextView) TextView venueCountryTextView;

        public VenueHolder(View view,
                           final RecyclerItemClickListener recyclerItemClickListener) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerItemClickListener != null){
                        recyclerItemClickListener.onItemClick(view, getAdapterPosition());
                    }
                }
            });
        }
    }

    public static class SpaceHolder extends RecyclerView.ViewHolder{
        public SpaceHolder(View view) {
            super(view);
        }
    }

    public VenuesRecyclerAdapter(ArrayList<VenueModel> venueList,
                                 RecyclerItemClickListener recyclerItemClickListener){
        this.venueList = venueList;
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == venueList.size()){
            return HOLDER_SPACE;
        }
        else {
            return HOLDER_VENUE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){

            case HOLDER_VENUE:
                view = inflater.inflate(R.layout.row_venue, parent, false);
                viewHolder = new VenueHolder(view, recyclerItemClickListener);
                break;

            case HOLDER_SPACE:
                view = inflater.inflate(R.layout.row_space, parent, false);
                viewHolder = new SpaceHolder(view);
                break;
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case HOLDER_VENUE:
                VenueHolder venueHolder = (VenueHolder) holder;
                venueHolder.venueNameTextView.setText(venueList.get(position).getName());
                venueHolder.venueAddressTextView.setText(venueList.get(position).getLocation().getAddress());
                venueHolder.venueCountryTextView.setText(venueList.get(position).getLocation().getCountry());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return venueList.size() + 1;
    }

}