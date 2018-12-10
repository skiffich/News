package skiffich.news.presenter;

import java.util.List;

import skiffich.news.ApplicationEx;
import skiffich.news.api.model.Article;
import skiffich.news.model.IModel;
import skiffich.news.view.ViewInterfaces;

public class MainPresenter implements PresenterInterfaces.MainPresenter,
        IModel.MailInteractor.OnFinishedListener {
    private ViewInterfaces.MainView view;
    private IModel.MailInteractor interactor;

    public MainPresenter(ViewInterfaces.MainView view){
        this.view = view;
        interactor = ApplicationEx.getInstance().getListInteractor();
    }


    @Override
    public void getData(String req, int page){
        interactor.getData(req, page, this);
    }

    @Override
    public void onFinishedLoad(List<Article> articles) {
        if (view != null) {
            view.setData(articles);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        if (view != null) {
            view.onResponseFailure(t);
        }
    }
}
