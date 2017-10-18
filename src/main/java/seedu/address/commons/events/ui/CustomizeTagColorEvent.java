package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to change the tag color of the application
 */
public class CustomizeTagColorEvent extends BaseEvent {
    private String color;

    public CustomizeTagColorEvent(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
