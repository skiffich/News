package skiffich.news;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import skiffich.news.api.NewsAPI;
import skiffich.news.model.FavoritesModel;
import skiffich.news.model.IModel;
import skiffich.news.model.LocalStorage;
import skiffich.news.model.MainModel;

public class ApplicationEx extends Application {
    private static final String BASE_URL = "https://picsum.photos/";

    private static ApplicationEx instance;
    private ManageViews manageViews;
    private LocalStorage storageManager;
    private IModel.MailInteractor mainInteractor;
    private IModel.FavoritesInteractor showMoreInteractor;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        setupItems();
    }

    public IModel.FavoritesInteractor getDetailsInteractor() {
        return showMoreInteractor;
    }

    private void setupItems() {
        final NewsAPI apiClient = createApiClient();
        storageManager = new LocalStorage(getApplicationContext());
        mainInteractor = new MainModel(apiClient);

        showMoreInteractor = new FavoritesModel(storageManager);
    }

    public static ApplicationEx getInstance() {
        return instance;
    }

    private NewsAPI createApiClient() {
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        return retrofit.create(NewsAPI.class);
    }

    public void settManager(ManageViews manageViews) {
        this.manageViews = manageViews;
    }

    public IModel.MailInteractor getListInteractor() {
        return mainInteractor;
    }

    public ManageViews getFragmentManager() {
        return manageViews;
    }
}
