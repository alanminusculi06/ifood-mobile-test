package br.com.minusculi.ifoodmobiletest;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import br.com.minusculi.ifoodmobiletest.ui.main.fragments.ClassificationFragment;
import br.com.minusculi.ifoodmobiletest.ui.main.fragments.TweetsFragment;
import br.com.minusculi.ifoodmobiletest.ui.main.fragments.UsernameFragment;
import br.com.minusculi.ifoodmobiletest.ui.main.viewmodels.ViewModel;

public class MainActivity extends ActivityBase {

    private ViewModel viewModel = new ViewModel();
    private Fragment usernameFragment = UsernameFragment.newInstance(viewModel);
    private Fragment tweetsFragment = TweetsFragment.newInstance(viewModel);
    private Fragment classificationFragment = ClassificationFragment.newInstance(viewModel);
    private List<Fragment> history = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(usernameFragment);
        setupActionBar();
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (history.isEmpty())
            fragmentTransaction.add(R.id.mainFragment, usernameFragment);
        else
            fragmentTransaction.replace(R.id.mainFragment, fragment);
        fragmentTransaction.commit();
        history.add(fragment);
    }

    public void selectUsernameFragment() {
        replaceFragment(usernameFragment);
    }

    public void selectTweetsFragment() {
        replaceFragment(tweetsFragment);
    }

    public void selectClassificationFragment() {
        replaceFragment(classificationFragment);
    }

    @Override
    public void onBackPressed() {
        if (history.isEmpty() || history.size() == 1)
            super.onBackPressed();
        else {
            Fragment current = history.get(history.size() - 1);
            Fragment previous = history.get(history.size() - 2);
            history.remove(current);
            history.remove(previous);
            replaceFragment(previous);
        }
    }
}
