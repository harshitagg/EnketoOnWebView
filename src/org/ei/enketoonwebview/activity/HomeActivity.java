package org.ei.enketoonwebview.activity;

import android.app.Activity;
import android.content.Intent;
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

    public static final String MODEL = "model";
    public static final String FORM = "form";
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
        new AsyncTask<String, Void, Pair<String, String>>() {
            @Override
            protected Pair<String, String> doInBackground(String... urls) {
                return formDataFetchService.fetch(urls[0]);
            }

            @Override
            protected void onPostExecute(Pair<String, String> pair) {
                Intent intent = new Intent(getApplicationContext(), FormActivity.class);
                intent.putExtra(MODEL, pair.first);
                intent.putExtra(FORM, pair.second);
                startActivity(intent);
            }
        }.execute("http://formhub.org/drishti_forms&form_id=ANC_v701_Copy");
    }
}
