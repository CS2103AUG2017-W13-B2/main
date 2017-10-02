package seedu.address.model;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

public class UniqueTagListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueTagList uniqueTagList = new UniqueTagList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueTagList.asObservableList().remove(0);
    }

    @Test
    public void removeTagTest() {
        try {
            Tag t1 = new Tag("test");
            Set<Tag> tSet = new HashSet<Tag>();
            tSet.add(t1);
            UniqueTagList uniqueTagList = new UniqueTagList(tSet);
            uniqueTagList.remove(t1);
            UniqueTagList uniqueTagList2 = new UniqueTagList();
            assertTrue(uniqueTagList.equals(uniqueTagList2));
        } catch (IllegalValueException e) {

        }
    }
}
