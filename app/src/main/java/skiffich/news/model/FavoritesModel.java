package skiffich.news.model;

import skiffich.news.api.model.Article;

public class FavoritesModel implements IModel.FavoritesInteractor {
    LocalStorage storageManager;

    public FavoritesModel(LocalStorage storageManager){
        this.storageManager = storageManager;
    }

    public void manageFavourites(Article article,
                                 IModel.FavoritesInteractor.
                                         OnFinishedListener onFinishedListener){
        if (!storageManager.ifStorageContainsItem(article.getTitle())) {
            storageManager.addToStorage(article);
            onFinishedListener.onAdd();
        } else {
            storageManager.removeFromStorage(article);
            onFinishedListener.onRemove();
        }
    }

    @Override
    public void checkFavourite(Article article, IModel.FavoritesInteractor.
            OnFinishedListener onFinishedListener) {
        if (storageManager.ifStorageContainsItem(article.getTitle())) {
            onFinishedListener.favouriteResult(true);
        } else {
            onFinishedListener.favouriteResult(false);
        }
    }
}
