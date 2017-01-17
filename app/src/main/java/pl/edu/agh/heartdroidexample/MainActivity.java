package pl.edu.agh.heartdroidexample;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import heart.Configuration;
import heart.HeaRT;
import heart.State;
import heart.StateElement;
import heart.exceptions.AttributeNotRegisteredException;
import heart.exceptions.BuilderException;
import heart.exceptions.ModelBuildingException;
import heart.exceptions.NotInTheDomainException;
import heart.exceptions.ParsingSyntaxException;
import heart.parser.hmr.HMRParser;
import heart.xtt.XTTModel;
import pl.edu.agh.heartdroidexample.util.GeneralUtils;
import pl.edu.agh.heartdroidexample.util.Symbolics;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private final String TAG = getClass().getSimpleName();

    private GoogleApiClient googleApiClient;
    private XTTModel model;
    private ModelTableInfoView modelTableInfoView;
    private CheckBox isManualCheckbox;

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGoogleApi();

        modelTableInfoView = (ModelTableInfoView) findViewById(R.id.info_table);
        isManualCheckbox = (CheckBox) findViewById(R.id.is_manual_checkbox);

        isManualCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                DroidApp.instance.isManual = isChecked;
            }
        });

        try {
            final HMRParser hmrParser = new HMRParser();
            hmrParser.parse(GeneralUtils.loadHmrFileIntoSourceString(this, "urban-helper-cf.hmr"));
            model = hmrParser.getModel();
            modelTableInfoView.initForModel(model);
        } catch (ModelBuildingException | ParsingSyntaxException | IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error while loading model");
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    private void logModelState() {
        final State currentState = HeaRT.getWm().getCurrentState(model);
        for (StateElement se : currentState) {
            Log.i(TAG, "Attribute " + se.getAttributeName() + " = " + se.getValue() + " certain factor " + se.getValue().getCertaintyFactor());
        }
    }

    private void initGoogleApi() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(ActivityRecognition.API)
                    .build();
        }
    }

    private void startInference() {
        try {
            HeaRT.goalDrivenInference(model, new String[]{"parkingReminder"}, new Configuration.Builder().setInitialState(HeaRT.getWm().getCurrentState()).build());
            logModelState();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    modelTableInfoView.notifyModelChanged();
                    Toast.makeText(MainActivity.this, "Ui updated", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (NotInTheDomainException | AttributeNotRegisteredException | BuilderException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        DroidApp.instance.lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, GeneralUtils.createDefaultLocationRequest(), this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        DroidApp.instance.lastKnownLocation = location;
    }

    public void onStartClicked(View view) {

        scheduledExecutorService.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                startInference();
            }
        }, 0, 20, TimeUnit.SECONDS);

    }

    public void onStopClicked(View view) {
        scheduledExecutorService.shutdown();
    }

    public void onVehicleClicked(View view) {
        DroidApp.instance.recActivity = Symbolics.ActivityType.inVehicle;
    }

    public void onFootClicked(View view) {
        DroidApp.instance.recActivity = Symbolics.ActivityType.onFoot;
    }
}
