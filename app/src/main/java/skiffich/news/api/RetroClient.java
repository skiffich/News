package skiffich.news.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    public static final String BASE_URL = "https://newsapi.org/v2/";

    private static final RetroClient INSTANCE = new RetroClient();

    public static NewsAPI sInstance() {
        return INSTANCE.fService;
    }

    private final NewsAPI fService;

    public RetroClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        fService = retrofit.create(NewsAPI.class);
    }
}
