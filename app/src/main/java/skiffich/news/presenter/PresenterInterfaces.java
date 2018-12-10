package skiffich.news.presenter;

public interface PresenterInterfaces {
    interface MainPresenter{
        void getData(String req, int page);
    }
    interface ShowMorePresenter {
        void checkFavourite();
        void getData(String req, int page);
    }
}
