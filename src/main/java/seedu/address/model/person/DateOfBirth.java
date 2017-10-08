package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's DateOfBirth in the address book.
 */

public class DateOfBirth {

    public final String Date;

    /**
     * Validates DateOfBirth
     *
     * @throws IllegalValueException if given dateOfBirth string is invalid.
     */
    public DateOfBirth(String Date) {

        requireNonNull(Date);
        String trimmedDate = Date.trim();
        this.Date = trimmedDate;
    }
    @Override
    public String toString() {
        return Date;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DateOfBirth
                && this.Date.equals(((DateOfBirth) other).Date));
    }
    @Override
    public int hashCode() {
        return Date.hashCode();
    }
}