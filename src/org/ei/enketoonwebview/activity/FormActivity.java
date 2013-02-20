package org.ei.enketoonwebview.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.ei.enketoonwebview.R;

import static org.ei.enketoonwebview.activity.HomeActivity.FORM;
import static org.ei.enketoonwebview.activity.HomeActivity.MODEL;


public class FormActivity extends Activity {
    private WebView webView;
    private ProgressDialog progressDialog;
    private String model;
    private String form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.form_layout);

        getIntentData();
        progressDialogInitialization();
        webViewInitialization(this);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        model = intent.getStringExtra(MODEL);
        form = intent.getStringExtra(FORM);
    }

    private void progressDialogInitialization() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading ...");
        progressDialog.setMessage("Please wait");
        progressDialog.show();
    }

    private void webViewInitialization(final Activity activity) {
        webView = (WebView) findViewById(R.id.webview);

        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setProgress(progress * 1000);

                if (progress == 100 && progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });

        WebSettings webViewSettings = webView.getSettings();
        webViewSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.addJavascriptInterface(new FormWebInterface(model, form), "androidContext");
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        webViewSettings.setDatabaseEnabled(true);
        webViewSettings.setDomStorageEnabled(true);
        webView.loadUrl("file:///android_asset/www/template.html");
    }
}
