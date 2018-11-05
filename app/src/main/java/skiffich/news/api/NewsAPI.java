package skiffich.news.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import skiffich.news.api.model.ResponseArt;

public interface NewsAPI {
    @GET("everything?apiKey=be71ff7a96c4458f8e3bbb7a8115965e")
    Call<ResponseArt> everything(   @Query("q") String request,
                                    @Query("page") int page,
                                    @Query("pageSize") int perPage);
}
