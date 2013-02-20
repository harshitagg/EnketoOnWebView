package org.ei.enketoonwebview.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.Toast;
import org.ei.enketoonwebview.R;
import org.ei.enketoonwebview.agent.HttpAgent;
import org.ei.enketoonwebview.service.EnketoFormDataFetchService;

public class HomeActivity extends Activity {

    private HttpAgent httpAgent;
    private EnketoFormDataFetchService formDataFetchService;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_layout);
        initializeView();

        httpAgent = new HttpAgent();
        formDataFetchService = new EnketoFormDataFetchService(httpAgent);
    }

    private void initializeView() {
        editText = (EditText) findViewById(R.id.editText);
    }

    public void testForm(View view) {
        String url = editText.getText().toString();
        if (!URLUtil.isHttpUrl(url)) {
            Toast.makeText(this, "Invalid URL, please check!!", Toast.LENGTH_SHORT).show();
        }
        new AsyncTask<String, Void, Pair>() {
            @Override
            protected Pair doInBackground(String... urls) {
                return formDataFetchService.fetch(urls[0]);
            }
        }.execute("http://formhub.org/drishti_forms&form_id=ANC_v701_Copy");
    }
}
