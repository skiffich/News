package skiffich.news.model;

import java.util.List;

import skiffich.news.api.NewsAPI;
import skiffich.news.api.model.Article;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import skiffich.news.api.model.ResponseArt;

public class MainModel implements IModel.MailInteractor {
    private static final int MAX_ITEMS_PER_PAGE = 20;

    private NewsAPI service;

    public MainModel(NewsAPI service){
        this.service = service;
    }

    public void getData(String req, int page, final IModel.MailInteractor.OnFinishedListener onFinishedListener){
        Call<ResponseArt> call = service.everything(req, page, MAX_ITEMS_PER_PAGE);
        call.clone().enqueue(new Callback<ResponseArt>() {
            @Override
            public void onResponse(Call<ResponseArt> call, Response<ResponseArt> response) {
                if (response.body().getTotalResults() > 0) {
                    onFinishedListener.onFinishedLoad(response.body().getArticles());
                }
            }
            @Override
            public void onFailure(Call<ResponseArt> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });

    }
}
