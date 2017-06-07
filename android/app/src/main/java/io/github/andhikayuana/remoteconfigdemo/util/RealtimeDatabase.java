package io.github.andhikayuana.remoteconfigdemo.util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * @author yuana <andhikayuana@gmail.com>
 * @since 6/7/17
 */

public class RealtimeDatabase {

    private static final String DB_KEY = "features";
    private static RealtimeDatabase instance;

    public RealtimeDatabase() {
    }

    public static RealtimeDatabase getInstance() {
        if (instance == null) instance = new RealtimeDatabase();
        return instance;
    }

    public FirebaseDatabase Db() {
        return FirebaseDatabase.getInstance();
    }

    public DatabaseReference Ref() {
        return Db().getReference(DB_KEY);
    }
}
