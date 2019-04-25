package com.scandipwa;

import android.content.ComponentName;
import android.net.Uri;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.customtabs.TrustedWebUtils;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
    private ImageView logo;

    @Nullable
    private TwaCustomTabsServiceConnection mServiceConnection;

    @Nullable
    private CustomTabsIntent mCustomTabsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mServiceConnection = new TwaCustomTabsServiceConnection();
        CustomTabsClient.bindCustomTabsService(
                this, CUSTOM_TAB_PACKAGE_NAME, mServiceConnection);

        logo = findViewById(R.id.logo);

        logo.animate()
            .alpha(1f)
            .setDuration(700)
            .withEndAction(() -> TrustedWebUtils.launchAsTrustedWebActivity(
                    MainActivity.this,
                    mCustomTabsIntent,
                    Uri.parse("https://demo.scandipwa.com/")))
            .start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mServiceConnection != null) {
            unbindService(mServiceConnection);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        finish();
    }

    private class TwaCustomTabsServiceConnection extends CustomTabsServiceConnection {

        @Override
        public void onCustomTabsServiceConnected(ComponentName componentName,
                                                 CustomTabsClient client) {

            CustomTabsSession session  = client.newSession(null);

            mCustomTabsIntent  = new CustomTabsIntent.Builder(session).build();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    }
}