package org.ei.enketoonwebview.activity;

import android.app.Activity;
import android.os.Bundle;
import org.ei.enketoonwebview.R;

public class HomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_layout);
    }
}
