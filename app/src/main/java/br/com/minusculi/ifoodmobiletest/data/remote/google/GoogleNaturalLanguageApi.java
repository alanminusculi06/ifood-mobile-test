package br.com.minusculi.ifoodmobiletest.data.remote.google;

import br.com.minusculi.ifoodmobiletest.data.remote.google.objects.Request;
import br.com.minusculi.ifoodmobiletest.data.remote.google.objects.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GoogleNaturalLanguageApi {

    @POST("./documents:analyzeSentiment")
    Call<Response> post(@Query("key") String apiKey, @Body Request request);

}
