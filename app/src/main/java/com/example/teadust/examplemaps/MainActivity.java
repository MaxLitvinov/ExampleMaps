package com.example.teadust.examplemaps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.crypto.KeyAgreement;

public class MainActivity extends AppCompatActivity implements android.location.LocationListener {

    GoogleMap map;

    LocationManager locationManager;
    LocationProvider locationProvider;
    Location location;

    String providerName;

    LatLng KHARKOV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();

        providerName = locationManager.getBestProvider(criteria, false);

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }

        location = locationManager.getLastKnownLocation(providerName);

        locationManager.requestLocationUpdates(providerName, 400, 1, this);

//        KHARKOV = new LatLng(49.9944422, 36.2368201);
        KHARKOV = new LatLng(location.getLatitude(), location.getLongitude());

        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap(); //getting map
                Marker marker = map.addMarker(new MarkerOptions().position(KHARKOV).title("Тащи сюда свои котлеты!Я надежно их спрячу...")); //create marker
//                marker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.bow_wow_cat));
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(KHARKOV, 15)); // Move the camera instantly to Kharkov with a zoom of 15.
                map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null); // Zoom in, animating the camera.
            }
        });

//        final LatLng KHARKOV = new LatLng(49.9944422,36.2368201);
//        final GoogleMap map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap(); //getting map
//        Marker marker = map.addMarker(new MarkerOptions().position(KHARKOV).title("Kharkov")); //create marker
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(KHARKOV, 15)); // Move the camera instantly to Kharkov with a zoom of 15.
//        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null); // Zoom in, animating the camera.

        Button normal = (Button) findViewById(R.id.button_normal);
        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });

        Button satellite = (Button) findViewById(R.id.button_satellite);
        satellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        Button hybrid = (Button) findViewById(R.id.button_hybrid);
        hybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(providerName, 400, 1, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        KHARKOV = new LatLng(lat, lon);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
