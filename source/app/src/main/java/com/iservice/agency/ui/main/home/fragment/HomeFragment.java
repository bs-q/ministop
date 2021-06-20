package com.iservice.agency.ui.main.home.fragment;

import android.content.pm.PackageManager;
import android.location.Location;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.iservice.agency.BR;
import com.iservice.agency.R;
import com.iservice.agency.databinding.HomeFragmentBinding;
import com.iservice.agency.di.component.FragmentComponent;
import com.iservice.agency.ui.base.fragment.BaseFragment;
import com.iservice.agency.ui.main.home.adapter.ViewPagerAdapter;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayoutMediator;

import timber.log.Timber;

public class HomeFragment extends BaseFragment<HomeFragmentBinding,HomeViewModel> {

    private boolean locationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private GoogleMap map;
    private Location lastKnownLocation;
    private static final int DEFAULT_ZOOM = 15;
    private boolean stopReloadMap = false;

    private ActivityResultLauncher<String> requestPermissionLauncher;


    @Override
    public int getBindingVariable() {
        return BR.mainHomeViewModel;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void performDataBinding() {
        // do data binding here
        ViewPagerAdapter demoCollectionAdapter = new ViewPagerAdapter(this);
        binding.mainViewpager.setAdapter(demoCollectionAdapter);
        new TabLayoutMediator(binding.tab, binding.mainViewpager,
                (tab, position) ->
                        tab.setIcon(R.drawable.tab_selector)
        ).attach();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {

            mapFragment.getMapAsync(callback);

            requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    locationPermissionGranted = true;
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                    locationPermissionGranted = false;
                }
                updateLocationUI();
            });
        }

    }

    @Override
    protected void performDependencyInjection(FragmentComponent buildComponent) {
        buildComponent.inject(this);
    }


    private final OnMapReadyCallback callback = googleMap -> {
        this.map = googleMap;
        if (!stopReloadMap){
            getLocationPermission();
            updateLocationUI();
        }

    };

    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = LocationServices.getFusedLocationProviderClient(requireActivity()).getLastLocation();
                locationResult.addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.getResult();
                        if (lastKnownLocation != null) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(lastKnownLocation.getLatitude(),
                                            lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            MarkerOptions markerOptions = new MarkerOptions();
                            LatLng latLng = new LatLng(lastKnownLocation.getLatitude(),lastKnownLocation.getLongitude());
                            markerOptions.position(latLng);
                            markerOptions.title("Cửa hàng ministop gần bạn");
                            markerOptions.snippet("Đi đến");
                            Marker locationMarker = map.addMarker(markerOptions);
                            locationMarker.showInfoWindow();
                            stopReloadMap=true;

                        }
                    } else {
                        viewModel.showErrorMessage("Can not get current location");
                        map.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch (SecurityException e) {
            Timber.e(e);
        }
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }
    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                getDeviceLocation();
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
            }
        } catch (SecurityException e) {
            Timber.e(e);
        }
    }
}