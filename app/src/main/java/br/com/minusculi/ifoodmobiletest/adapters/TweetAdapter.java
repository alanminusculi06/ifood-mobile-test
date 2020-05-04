package br.com.minusculi.ifoodmobiletest.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.minusculi.ifoodmobiletest.R;
import br.com.minusculi.ifoodmobiletest.databinding.TweetLayoutBinding;
import br.com.minusculi.ifoodmobiletest.listeners.ITweetListener;
import br.com.minusculi.ifoodmobiletest.main.objects.Tweet;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ModuloViewHolder> {

    private List<Tweet> tweets;
    private LayoutInflater layoutInflater;
    private TweetLayoutBinding binding;
    private ITweetListener tweetListener;

    public TweetAdapter(Context context, List<Tweet> tweets, ITweetListener tweetListener) {
        this.tweets = tweets;
        this.tweetListener = tweetListener;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    @NonNull
    @Override
    public ModuloViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.tweet_layout, parent, false);
        View view = binding.getRoot();
        return new ModuloViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ModuloViewHolder holder, final int position) {
        Tweet tweet = tweets.get(position);
        holder.itemView.setOnClickListener(v -> tweetListener.onTweetClick(tweet));
        Picasso.get().load(tweet.getProfileImageUrl()).into(binding.tweetProfilePicture);
        binding.setTweet(tweet);
    }

    static class ModuloViewHolder extends RecyclerView.ViewHolder {

        ModuloViewHolder(View itemView) {
            super(itemView);
        }

    }
}
