package skiffich.news.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {
    public static final String BASE_URL = "https://newsapi.org/v2/";

    private static final RetroClient instance = new RetroClient();

    public static NewsAPI instance() {
        return instance.service;
    }

    private final NewsAPI service;

    public RetroClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(NewsAPI.class);
    }
}
