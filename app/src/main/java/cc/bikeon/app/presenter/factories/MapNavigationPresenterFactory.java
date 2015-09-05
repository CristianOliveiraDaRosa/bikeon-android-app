package cc.bikeon.app.presenter.factories;

import cc.bikeon.app.presenter.MapNavigationPresenter;
import cc.bikeon.app.services.rest.directions.DirectionRequester;
import cc.bikeon.app.services.rest.directions.DirectionRequesterFactory;
import cc.bikeon.app.views.MapNavigationView;

/**
 * Responsible for create a new instance of {@link MapNavigationPresenter} for a given
 * {@link MapNavigationView}
 * <p/>
 * Created by cristianoliveira on 18/08/15.
 */
public class MapNavigationPresenterFactory {

    /**
     * Create Presenter for a given View
     * @param view
     * @return new Instance of MapNavigationPresenter
     */
    public static MapNavigationPresenter createFor(MapNavigationView view) {

        DirectionRequester requester = DirectionRequesterFactory.create();

        MapNavigationPresenter presenter = new MapNavigationPresenter(view, requester);

        return presenter;
    }
}
