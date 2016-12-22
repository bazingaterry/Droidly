package pub.terry.lab_10;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener, LocationListener {

    private ImageView compass;
    private TextView degree, latitude, longitude, address, shake;
    private Geocoder geocoder;

    private int shakeCount = 0, shakeFlag = 0;

    private float lastDegree = 0;

    private Location location;

    private float[] accelerometerValues = new float[3];
    private float[] magnetometerValues = new float[3];
    private float[] values = new float[3];
    private float[] r = new float[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView();
        initSensors();
    }

    void findView() {
        geocoder = new Geocoder(this, Locale.CHINA);
        compass = (ImageView) findViewById(R.id.compass);
        degree = (TextView) findViewById(R.id.degree);
        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
        address = (TextView) findViewById(R.id.address);
        shake = (TextView) findViewById(R.id.shakeCount);
    }

    void initSensors() {
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationProvider gpsProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
        LocationProvider netProvider = locationManager.getProvider(LocationManager.NETWORK_PROVIDER);

        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 1, this);
        if (gpsProvider != null)
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        else if (netProvider != null)
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null) setLocationView(location);
    }

    void setLocationView(Location location) {
        this.location = location;
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            address.setText(addresses.get(0).getAddressLine(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        latitude.setText(String.format("Latitude: %f", location.getLatitude()));
        longitude.setText(String.format("Longitude: %f", location.getLatitude()));
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            accelerometerValues = sensorEvent.values.clone();
            float delta[] = sensorEvent.values.clone();
            if ((Math.abs(delta[0]) + Math.abs(delta[1]) + Math.abs(delta[2])) > 30) {
                shakeFlag = (shakeFlag + 1) % 3;
                if (shakeFlag == 0) shake.setText(String.format("Shake %d time(s).", ++shakeCount));
            }
        }
        if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            magnetometerValues = sensorEvent.values.clone();
        }
        SensorManager.getRotationMatrix(r, null, accelerometerValues, magnetometerValues);
        SensorManager.getOrientation(r, values);

        float currentDegree = (float) Math.toDegrees(values[0]);
        RotateAnimation rotateAnimation = new RotateAnimation(-currentDegree, -lastDegree, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        compass.startAnimation(rotateAnimation);
        degree.setText(String.format("Angle: %f", currentDegree));
        lastDegree = currentDegree;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        setLocationView(location);
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
}
