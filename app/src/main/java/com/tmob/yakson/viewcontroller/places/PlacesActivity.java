package com.tmob.yakson.viewcontroller.places;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tmob.michallange.R;
import com.tmob.yakson.api.ApiErrorUtils;
import com.tmob.yakson.api.ApiRequest;
import com.tmob.yakson.helper.DialogHelper;
import com.tmob.yakson.interfaces.ActivityInterface;
import com.tmob.yakson.interfaces.RecyclerItemClickListener;
import com.tmob.yakson.model.GetVenueDetailsResponseModel;
import com.tmob.yakson.model.VenueModel;

import java.util.ArrayList;

import butterknife.BindView;

public class PlacesActivity extends AppCompatActivity implements ActivityInterface {

    @BindView(R.id.venuesRecyclerView) RecyclerView venuesRecyclerView;

    private PlacesViewModel placesViewModel;
    private ArrayList<VenueModel> venueList = new ArrayList<>();
    private VenuesRecyclerAdapter venuesRecyclerAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_places;
    }

    @Override
    public void getDataFromIntent() {
        venueList = (ArrayList<VenueModel>) getIntent().getSerializableExtra("venueList");
        if(venueList == null){
            venueList = new ArrayList<>();
        }
    }

    @Override
    public void initViews() {
        initRecyclerView();
    }

    @Override
    public void initViewModel() {
        placesViewModel = ViewModelProviders.of(this).get(PlacesViewModel.class);
    }

    @Override
    public void initObservers() {
        placesViewModel
                .getVenueDetailsRequestLiveData()
                .observe(
                        this,
                        new Observer<ApiRequest<GetVenueDetailsResponseModel>>() {
                            @Override
                            public void onChanged(@Nullable ApiRequest<GetVenueDetailsResponseModel> apiRequest) {
                                switch (apiRequest.getStatus()){
                                    case LOADING:
                                        DialogHelper.showLoadingDialog();
                                        break;
                                    case FINISH:
                                        DialogHelper.hideLoadingDialog();
                                        break;
                                    case SUCCESS:
                                        VenueModel venueModel = apiRequest.getApiResponseBody().getResponse().getVenueModel();
                                        DialogHelper.showVenueDetailsDialog(venueModel);
                                        break;
                                    case ERROR:
                                        ApiErrorUtils.parseError(apiRequest.getRawApiResponse());
                                        break;
                                    case FAIL:
                                        DialogHelper.showAlertDialog(apiRequest.getApiError().getMessage());
                                        break;
                                }
                            }
                        }
                );
    }

    @Override
    public void initActivity() {

    }

    private void initRecyclerView(){
        venuesRecyclerAdapter = new VenuesRecyclerAdapter(venueList, new RecyclerItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                placesViewModel.getVenueDetails(venueList.get(position).getId());
            }
        });
        venuesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        venuesRecyclerView.setAdapter(venuesRecyclerAdapter);
        venuesRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

}
