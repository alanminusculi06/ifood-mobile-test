package br.com.minusculi.ifoodmobiletest;

import android.content.Intent;
import android.os.Bundle;

import br.com.minusculi.ifoodmobiletest.data.remote.google.GoogleNaturalLanguageService;
import br.com.minusculi.ifoodmobiletest.data.remote.twitter.Parameters;
import br.com.minusculi.ifoodmobiletest.data.remote.twitter.TwitterService;

public class SplashActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startServices();
    }

    private void startServices() {
        new Thread(() -> {
            try {
                TwitterService.getInstance()
                        .setParameters(new Parameters(
                                getString(R.string.twitter_consumer_key),
                                getString(R.string.twitter_consumer_secret),
                                getString(R.string.twitter_access_token),
                                getString(R.string.twitter_access_token_secret)))
                        .configure();
                GoogleNaturalLanguageService.getInstance().configure();
            } catch (Exception e) {
                showAlertDialog(getString(R.string.dialog_title_attention), e.getMessage(), (dialogInterface, i) -> startMainActivity());
            } finally {
                startMainActivity();
            }
        }).start();
    }

    private void startMainActivity() {
        runOnUiThread(() -> startActivity(new Intent(SplashActivity.this, MainActivity.class)));
    }
}
