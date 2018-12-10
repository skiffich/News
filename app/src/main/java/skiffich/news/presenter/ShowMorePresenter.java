package skiffich.news.presenter;

import skiffich.news.ApplicationEx;
import skiffich.news.api.model.Article;
import skiffich.news.model.IModel;
import skiffich.news.view.ViewInterfaces;

public class ShowMorePresenter implements PresenterInterfaces.ShowMorePresenter,
        IModel.FavoritesInteractor.OnFinishedListener {

    private IModel.FavoritesInteractor interactor;
    private ViewInterfaces.ShowMoreView view;
    private Article article;

    public ShowMorePresenter(ViewInterfaces.ShowMoreView view){
        this.view = view;
        interactor = ApplicationEx.getInstance().getDetailsInteractor();
    }

    public void getData(String req, int page) {
        article = ApplicationEx.getInstance().getFragmentManager().getArguments();
        view.initializeItems(article);
        interactor.checkFavourite(article, this);
    }

    public void checkFavourite() {
        interactor.manageFavourites(article, this);
    }
    public void onAdd() {
        view.addToFavourites();
    }

    public void onRemove() {
        view.removeFromFavourites();
    }
    public void favouriteResult(boolean favourite) {
        view.editFavourite(favourite);
    }
}
