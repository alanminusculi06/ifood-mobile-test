package br.com.minusculi.ifoodmobiletest.ui.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import br.com.minusculi.ifoodmobiletest.MainActivity;
import br.com.minusculi.ifoodmobiletest.R;
import br.com.minusculi.ifoodmobiletest.data.remote.google.GoogleNaturalLanguageApi;
import br.com.minusculi.ifoodmobiletest.data.remote.google.GoogleNaturalLanguageService;
import br.com.minusculi.ifoodmobiletest.data.remote.google.objects.Response;
import br.com.minusculi.ifoodmobiletest.databinding.ClassificationFragmentLayoutBinding;
import br.com.minusculi.ifoodmobiletest.ui.main.viewmodels.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;

public class ClassificationFragment extends Fragment {

    private ClassificationFragmentLayoutBinding binding;
    private final ViewModel viewModel;

    private ClassificationFragment(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public static Fragment newInstance(ViewModel viewModel) {
        return new ClassificationFragment(viewModel);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.classification_fragment_layout, container, false);
        binding.setViewModel(viewModel);
        setupViews(binding);
        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getClassification();
    }

    private void setupViews(ClassificationFragmentLayoutBinding binding) {
        Picasso.get().load(viewModel.getTweet().getProfileImageUrl()).into(binding.tweetProfilePicture);
    }

    private void getClassification() {
        viewModel.setLoading(true);
        GoogleNaturalLanguageApi api = GoogleNaturalLanguageService.getInstance().getApiService();
        api.post(getString(R.string.google_cloud_api_key), viewModel.createRequestObject())
                .enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> result) {
                        Response response = result.body();
                        if (response != null) {
                            viewModel.setLoading(false);
                            viewModel.setScore(response.documentSentiment.score);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                        viewModel.setLoading(false);
                        MainActivity mainActivity = (MainActivity) getActivity();
                        if (mainActivity != null)
                            mainActivity.showAlertDialog(getString(R.string.dialog_title_attention), t.getMessage(), (dialogInterface, i) -> mainActivity.selectUsernameFragment());
                    }
                });
    }

}
