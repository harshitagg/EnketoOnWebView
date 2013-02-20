package org.ei.enketoonwebview.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import org.ei.enketoonwebview.R;
import org.ei.enketoonwebview.agent.HttpAgent;

import java.io.InputStream;

public class HomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_layout);
    }

    public void testForm(View view) {
        final HttpAgent httpAgent = new HttpAgent();
        new AsyncTask<String, Void, InputStream>() {
            @Override
            protected InputStream doInBackground(String... urls) {
                return httpAgent.fetch(urls[0]);
            }
        }.execute("http://formhub.org/drishti_forms&form_id=ANC_v701_Copy");
    }
}
