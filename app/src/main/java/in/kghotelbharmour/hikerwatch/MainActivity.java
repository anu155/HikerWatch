package in.kghotelbharmour.hikerwatch;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    LocationListener locationListener;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            Startlistining();

        }

    }

        public void Startlistining(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)

        {  locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        }

            }





    public  void updateLocationInfo(Location location) {
        Log.i("Location info", location.toString());
        TextView latTextView = (TextView) findViewById(R.id.LatTextView);
        TextView longTextView = (TextView) findViewById(R.id.LongTextView);
        TextView AccuracyTextView = (TextView) findViewById(R.id.AccuracyTextView);
        TextView AltitudeTextView = (TextView) findViewById(R.id.AltitudeTextView);
        latTextView.setText("Latitude:" + location.getLongitude());
        longTextView.setText("Longitude:" + location.getLongitude());
        AccuracyTextView.setText("Accuracy:" + location.getAccuracy());
        AltitudeTextView.setText("Altitude:" + location.getAltitude());
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            String Address = "Could not find address";

            List<Address> ListAddresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if (ListAddresses != null && ListAddresses.size() > 0) {
                Log.i("PlaceInfo", ListAddresses.get(0).toString());

                Address ="Address\n";
                if (ListAddresses.get(0).getSubThoroughfare() != null) {
                    Address = ListAddresses.get(0).getSubThoroughfare()+ "";

                } if (ListAddresses.get(0).getThoroughfare() != null) {
                    Address = ListAddresses.get(0).getThoroughfare()+"\n";


                }
                if (ListAddresses.get(0).getLocality() != null) {
                    Address = ListAddresses.get(0).getLocality()+"\n";


                }
                if (ListAddresses.get(0).getPostalCode() != null) {
                    Address = ListAddresses.get(0).getPostalCode()+"\n";


                }
                if (ListAddresses.get(0).getCountryName() != null) {
                    Address = ListAddresses.get(0).getCountryName()+"\n";


                }



            } TextView AddressTextView = (TextView)findViewById(R.id.AddressTextView);
               AddressTextView.setText(Address);

        } catch (IOException e) {
            e.printStackTrace();

        }

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateLocationInfo(location);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        if (Build.VERSION.SDK_INT < 23) {

           Startlistining();


        } else {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


            } else {

                // we have permission!

                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

                if (location != null) {
                    updateLocationInfo(location);
                }

            }
        }
    }
}
