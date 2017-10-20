package seedu.address.model.person;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents a Person's DateOfBirth in the address book.
 */

public class DateOfBirth {

    /**
     * Represents a Person's Date Of birth
     * Guarantees: immutable; is valid as declared in {@link #isValidBirthday(String)}
     */


    public static final String BIRTHDAY_VALIDATION_REGEX = "(0[1-9]|[1-9]|1[0-9]|2[0-9]|3[01])[///./-]"
            + "(0[1-9]|1[0-2]|[1-9])[///./-](19|20)[0-9][0-9]";


    public static final String MESSAGE_BIRTHDAY_CONSTRAINTS =
            "Date of Birth must be a Valid Date and in the following format: \n"
            + "'.' and '/' can be used as separators. \n";

    public final String date;

    public DateOfBirth(String Date) throws IllegalValueException {

        requireNonNull(Date);
        if (!isValidBirthday(Date)) {
            throw new IllegalValueException(MESSAGE_BIRTHDAY_CONSTRAINTS);
        }
        String trimmedDate = Date.trim();
        this.date = trimmedDate;

    }

    @Override
    public String toString() {
        return date;
    }

    /**
     * Returns true if a given string is a valid person birthday.
     */
    public static boolean isValidBirthday(String birthday) {

        String trimmedBirthday = birthday.trim();
        if (trimmedBirthday.isEmpty()) {
            return false;
        }
        if (!trimmedBirthday.matches(BIRTHDAY_VALIDATION_REGEX)) {
            return false;
        }

        return true;
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
