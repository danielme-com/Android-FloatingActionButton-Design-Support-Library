package com.danielme.android.fab;

import android.support.annotation.NonNull;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * @author danielme.com
 */
public class OnScrollUpDownListener implements AbsListView.OnScrollListener {
    private int previousScrollPosition;
    private int previousItemPosition;
    private int minDistance;
    private ListView listView;
    private Action action;

    public interface Action {
        void up();

        void down();
    }

    public OnScrollUpDownListener(@NonNull ListView listView, int minDistance, @NonNull Action action) {
        this.listView = listView;
        this.minDistance = minDistance;
        this.action = action;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //nothing here
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int currentScrollPosition = getCurrentScrollPosition();
        if (firstVisibleItem == previousItemPosition) {
            int scrolled = Math.abs(previousScrollPosition - currentScrollPosition);
            if (scrolled > minDistance) {
                if (previousScrollPosition > currentScrollPosition) {
                    action.up();
                } else {
                    action.down();
                }
            }
        } else if (firstVisibleItem > previousItemPosition) {
            action.up();
        } else {
            action.down();
        }
        previousScrollPosition = currentScrollPosition;
        previousItemPosition = firstVisibleItem;

    }

    private int getCurrentScrollPosition() {
        int pos = 0;
        if (listView.getChildAt(0) != null) {
            pos = listView.getChildAt(0).getTop();
        }
        return pos;
    }

}