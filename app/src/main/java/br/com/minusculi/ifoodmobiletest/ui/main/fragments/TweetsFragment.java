package br.com.minusculi.ifoodmobiletest.ui.main.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import br.com.minusculi.ifoodmobiletest.MainActivity;
import br.com.minusculi.ifoodmobiletest.R;
import br.com.minusculi.ifoodmobiletest.adapters.TweetAdapter;
import br.com.minusculi.ifoodmobiletest.data.remote.twitter.TwitterService;
import br.com.minusculi.ifoodmobiletest.databinding.TweetsFragmentLayoutBinding;
import br.com.minusculi.ifoodmobiletest.listeners.ITweetListener;
import br.com.minusculi.ifoodmobiletest.main.objects.Tweet;
import br.com.minusculi.ifoodmobiletest.ui.main.viewmodels.ViewModel;
import twitter4j.TwitterException;

public class TweetsFragment extends Fragment implements ITweetListener {

    private final ViewModel viewModel;
    private TweetsFragmentLayoutBinding binding;

    private TweetsFragment(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public static Fragment newInstance(ViewModel viewModel) {
        return new TweetsFragment(viewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.tweets_fragment_layout, container, false);
        setupViews(binding);
        loadTweets(1);
        return binding.getRoot();
    }

    private void setupViews(TweetsFragmentLayoutBinding binding) {
        binding.lstTweets.setHasFixedSize(true);
        binding.lstTweets.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadTweets(int page) {
        //todo load more
        new Thread(() -> {
            try {
                List<Tweet> tweets = TwitterService.getInstance().loadTweets(viewModel.username.get(), page);
                listTweets(tweets);
            } catch (TwitterException e) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null)
                    mainActivity.showAlertDialog(getString(R.string.dialog_title_attention), e.getMessage(), (dialogInterface, i) -> mainActivity.selectUsernameFragment());
            }
        }).start();
    }

    private void listTweets(List<Tweet> tweets) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> {
                TweetAdapter adapter = new TweetAdapter(getContext(), tweets, TweetsFragment.this);
                binding.lstTweets.setAdapter(adapter);
            });
        }
    }

    @Override
    public void onTweetClick(Tweet tweet) {
        viewModel.setTweet(tweet);
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.selectClassificationFragment();
    }
}
