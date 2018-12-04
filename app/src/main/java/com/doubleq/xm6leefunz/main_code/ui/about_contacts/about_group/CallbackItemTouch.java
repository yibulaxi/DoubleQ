package com.doubleq.xm6leefunz.main_code.ui.about_contacts.about_group;

/**
 * Interface to listen move in ItemTouchHelper.Callback
 * Created by Alessandro on 15/01/2016.
 */
public interface CallbackItemTouch {

    /**
     * Called when an item has been dragged
     * @param oldPosition start position
     * @param newPosition end position
     */
    void itemTouchOnMove(int oldPosition, int newPosition);
}
