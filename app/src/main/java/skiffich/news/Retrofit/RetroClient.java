package skiffich.news.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {
    private static final String rootURL = "https://newsapi.org/v2/";

    public static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(rootURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NewsAPI getNewsAPI() {
        return getRetrofitInstance().create(NewsAPI.class);
    }
}
