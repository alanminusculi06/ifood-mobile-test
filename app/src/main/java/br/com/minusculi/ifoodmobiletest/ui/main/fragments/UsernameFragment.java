package br.com.minusculi.ifoodmobiletest.ui.main.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.List;

import br.com.minusculi.ifoodmobiletest.MainActivity;
import br.com.minusculi.ifoodmobiletest.R;
import br.com.minusculi.ifoodmobiletest.adapters.UserAdapter;
import br.com.minusculi.ifoodmobiletest.data.remote.twitter.TwitterService;
import br.com.minusculi.ifoodmobiletest.databinding.UsernameFragmentLayoutBinding;
import br.com.minusculi.ifoodmobiletest.ui.main.viewmodels.ViewModel;
import twitter4j.TwitterException;
import twitter4j.User;

public class UsernameFragment extends Fragment {

    private final ViewModel viewModel;

    private UsernameFragment(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public static Fragment newInstance(ViewModel viewModel) {
        return new UsernameFragment(viewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        UsernameFragmentLayoutBinding binding = DataBindingUtil.inflate(inflater, R.layout.username_fragment_layout, container, false);
        binding.setViewModel(viewModel);
        setupViews(binding);
        return binding.getRoot();
    }

    private void setupViews(UsernameFragmentLayoutBinding binding) {
        binding.btnNext.setOnClickListener(getBtnNextOnClick(binding));
        binding.txtUsername.addTextChangedListener(getTxtUsernameTextWatcher(binding));
    }

    private View.OnClickListener getBtnNextOnClick(UsernameFragmentLayoutBinding binding) {
        return v -> {
            String username = binding.txtUsername.getText().toString();
            if (!username.isEmpty()) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null)
                    mainActivity.selectTweetsFragment();
            }
        };
    }

    private TextWatcher getTxtUsernameTextWatcher(UsernameFragmentLayoutBinding binding) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String username = s.toString();
                if (username.length() >= 3)
                    searchUsers(username, binding);
            }
        };
    }

    private void searchUsers(String username, UsernameFragmentLayoutBinding binding) {
        new Thread(() -> {
            try {
                List<User> users = TwitterService.getInstance().searchUsers("@" + username);
                listUsernames(users, binding);
            } catch (TwitterException e) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null)
                    mainActivity.showAlertDialog(getString(R.string.dialog_title_attention), e.getMessage(), null);
            }
        }).start();
    }

    private void listUsernames(List<User> users, UsernameFragmentLayoutBinding binding) {
        FragmentActivity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(() -> {
                binding.listUser.setAdapter(new UserAdapter(getContext(), users));
                binding.listUser.setOnItemClickListener((parent, view, position, id) -> {
                    User user = users.get(position);
                    binding.txtUsername.setText(user.getScreenName());
                });
            });
        }
    }
}
