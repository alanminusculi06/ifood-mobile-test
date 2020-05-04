package br.com.minusculi.ifoodmobiletest.ui.main.viewmodels;

import android.view.View;

import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableFloat;
import androidx.databinding.ObservableInt;

import br.com.minusculi.ifoodmobiletest.data.remote.google.objects.Document;
import br.com.minusculi.ifoodmobiletest.data.remote.google.objects.Request;
import br.com.minusculi.ifoodmobiletest.main.objects.Tweet;

public class ViewModel {

    public ObservableField<String> username = new ObservableField<>();
    public ObservableBoolean loading = new ObservableBoolean();
    public ObservableFloat score = new ObservableFloat();
    public ObservableBoolean sad = new ObservableBoolean();
    public ObservableBoolean happy = new ObservableBoolean();
    public ObservableBoolean neutral = new ObservableBoolean();
    private Tweet tweet;

    public ViewModel(){
        score.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                float value = score.get();
                if (value >= -1 && value <= -0.4)
                    sad.set(true);
                else if (value >= -0.3 && value < 0.3)
                    neutral.set(true);
                else if (value >= 0.3)
                    happy.set(true);
            }
        });
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public Request createRequestObject() {
        return new Request(new Document(tweet.getText()));
    }

    public void setLoading(boolean loading) {
        this.loading.set(loading);
    }

    public void setScore(float score) {
        this.score.set(score);
    }
}
