package io.github.andhikayuana.remoteconfigdemo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import io.github.andhikayuana.remoteconfigdemo.BuildConfig;
import io.github.andhikayuana.remoteconfigdemo.ui.featuresenable.FeaturesEnableActivity;
import io.github.andhikayuana.remoteconfigdemo.util.Const;
import io.github.andhikayuana.remoteconfigdemo.R;
import io.github.andhikayuana.remoteconfigdemo.util.SharedPref;

public class MainActivity extends AppCompatActivity {

    private FirebaseRemoteConfig remoteConfig;
    private Button btnCheckout;
    private Button btnCart;
    private Button btnFinished;
    private FirebaseAnalytics firebaseAnalytics;
    private String variant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        initView();

        initRemoteConfig();

        runExperiment();

        fetchRemoteConfig();
    }

    private void initView() {
        btnCheckout = (Button) findViewById(R.id.btnCheckout);
        btnCart = (Button) findViewById(R.id.btnCart);
        btnFinished = (Button) findViewById(R.id.btnFinished);

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAnalytics.logEvent("clicked_checkout", new Bundle());
                startActivity(new Intent(MainActivity.this, FeaturesEnableActivity.class));
            }
        });
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAnalytics.logEvent("clicked_cart", new Bundle());
            }
        });
        btnFinished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAnalytics.logEvent("clicked_finished", new Bundle());
            }
        });
    }

    private void initRemoteConfig() {
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings
                .Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();

        remoteConfig = FirebaseRemoteConfig.getInstance();
        remoteConfig.setConfigSettings(configSettings);
        remoteConfig.setDefaults(R.xml.remote_config);
    }

    private void fetchRemoteConfig() {
        remoteConfig
                .fetch(Const.CACHE_TIME)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(Const.TAG, "Fetch Succeed");
                            remoteConfig.activateFetched();
                        } else {
                            Log.d(Const.TAG, "Fetch Failed");
                        }
                        runExperiment();
                    }
                });
    }

    private void runExperiment() {
        if (remoteConfig != null) {
            variant = remoteConfig.getString(Const.EXPERIMENT_VARIANT);
            SharedPref.saveString(Const.EXPERIMENT_VARIANT, variant);
        }

        variant = SharedPref.getString(Const.EXPERIMENT_VARIANT);

        firebaseAnalytics.setUserProperty(Const.EXPERIMENT, variant);

        if (variant.equals(Const.VARIANT_A)) {
            btnCheckout.setVisibility(View.VISIBLE);
            btnCart.setVisibility(View.GONE);
            btnFinished.setVisibility(View.GONE);
        } else if (variant.equals(Const.VARIANT_B)) {
            btnCheckout.setVisibility(View.GONE);
            btnCart.setVisibility(View.VISIBLE);
            btnFinished.setVisibility(View.GONE);
        } else {
            btnCheckout.setVisibility(View.GONE);
            btnCart.setVisibility(View.GONE);
            btnFinished.setVisibility(View.VISIBLE);
        }
    }
}
