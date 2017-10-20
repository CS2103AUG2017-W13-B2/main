package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.FileImage;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code PhotoCommand}.
 */
public class PhotoCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {

        ReadOnlyPerson personToAddPhoto = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        personToAddPhoto.imageProperty().setValue(new FileImage("nus.jpg"));
        PhotoCommand photoCommand = prepareCommand(INDEX_FIRST_PERSON, "nus.jpg");

        String expectedMessage = String.format(PhotoCommand.MESSAGE_PHOTO_PERSON_SUCCESS, personToAddPhoto);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.addPhotoPerson(personToAddPhoto, "nus.jpg", INDEX_FIRST_PERSON);

        assertCommandSuccess(photoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        String FilePath = "dummy.png";
        PhotoCommand photoCommand = prepareCommand(outOfBoundIndex, FilePath);

        assertCommandFailure(photoCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Returns a {@code PhotoCommand} with the parameter {@code index and Filepath}.
     */
    private PhotoCommand prepareCommand(Index index, String FilePath) {
        //DeleteCommand deleteCommand = new DeleteCommand(index);
        PhotoCommand photoCommand = new PhotoCommand(index, FilePath);
        photoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return photoCommand;
    }
    @Test
    public void equals() {
        PhotoCommand photoCommand = new PhotoCommand(INDEX_FIRST_PERSON, "nus.jpg");
        PhotoCommand photoSecondCommand = new PhotoCommand(INDEX_SECOND_PERSON, "nus.jpg");

        // same object -> returns true
        assertTrue(photoCommand.equals(photoCommand));

        // same values -> returns true
        PhotoCommand photoFirstCommandCopy = new PhotoCommand(INDEX_FIRST_PERSON, "nus.jpg");
        assertTrue(photoCommand.equals(photoFirstCommandCopy));

        // different types -> returns false
        assertFalse(photoCommand.equals(1));

        // null -> returns false
        assertFalse(photoCommand.equals(null));

        // different person -> returns false
        assertFalse(photoCommand.equals(photoSecondCommand));
    }
    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }

}
