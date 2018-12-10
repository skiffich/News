package skiffich.news.view;

import java.util.List;

import skiffich.news.api.model.Article;

public interface ViewInterfaces {
    interface MainView {
        void setData(List<Article> imageItems);
        void onResponseFailure(Throwable throwable);
    }

    interface ShowMoreView {
        void addToFavourites();
        void removeFromFavourites();
        void editFavourite(boolean favourite);
        void initializeItems(Article article);
    }
}
