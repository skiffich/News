package skiffich.news.model;

import java.util.List;

import skiffich.news.api.model.Article;

public interface IModel {
    interface MailInteractor {
        interface OnFinishedListener {
            void onFinishedLoad(List<Article> imageItems);
            void onFailure(Throwable t);
        }
        void getData(String req, int page, IModel.MailInteractor.OnFinishedListener
                             onFinishedListener);

    }

    interface FavoritesInteractor{
        interface OnFinishedListener {
            void onAdd();
            void onRemove();
            void favouriteResult(boolean change);
        }
        void manageFavourites(Article article, OnFinishedListener onFinishedListener);
        void checkFavourite(Article article, IModel.FavoritesInteractor.
                OnFinishedListener onFinishedListener);
    }
}
