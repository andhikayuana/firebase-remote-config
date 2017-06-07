package io.github.andhikayuana.remoteconfigdemo.ui.featuresenable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import io.github.andhikayuana.remoteconfigdemo.R;
import io.github.andhikayuana.remoteconfigdemo.util.RealtimeDatabase;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 6/7/17
 */

public class FeaturesEnableActivity extends AppCompatActivity {

    private static final String TAG = "Features Enable";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features_enable);

        RealtimeDatabase
                .getInstance()
                .Ref()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(TAG, "data change");
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            Log.d(TAG, child.getValue() + "");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(TAG, databaseError.getMessage());
                    }
                });
    }
}
