package banana.com.thepeel.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import banana.com.thepeel.R;
import banana.com.thepeel.permissions;


public class map_page_fragment extends Fragment implements OnMapReadyCallback {

    private SupportMapFragment mMapFragment;
    private GoogleMap mGoogleMap;
    private Activity rActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_page_layout, container, false);
        rActivity = getActivity();
        if(googleServicesAvailable()){
            initMaps();
        }
        return view;
    }

    private void initMaps(){
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if(permissions.checkLocationPermission(rActivity, rActivity)) {
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    public boolean googleServicesAvailable() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(getContext());
        if (isAvailable == ConnectionResult.SUCCESS)
            return true;
        else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(rActivity, isAvailable, 0);
            dialog.show();
        } else {
            Toast.makeText(rActivity, "GS NA", Toast.LENGTH_LONG).show();
        }
        return false;
    }
}
