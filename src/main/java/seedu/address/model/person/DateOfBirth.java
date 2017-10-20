package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's DateOfBirth in the address book.
 */

public class DateOfBirth {

    public final String date;

    /**
     * Validates DateOfBirth
     *
     * @throws IllegalValueException if given dateOfBirth string is invalid.
     */

    public DateOfBirth(String date) {
        if (!date.equals("")) {
            String trimmedDate = date.trim();
            this.date = trimmedDate;
        } else {
            this.date = "";
        }
    }

    @Override
    public String toString() {
        return date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DateOfBirth
                && this.date.equals(((DateOfBirth) other).date));
    }
    @Override
    public int hashCode() {
        return date.hashCode();
    }
}
