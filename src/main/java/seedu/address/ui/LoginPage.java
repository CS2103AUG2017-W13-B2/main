package seedu.address.ui;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;


import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.Config;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.util.SampleDataUtil;
import seedu.address.storage.AccountsStorage;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;
import seedu.address.storage.XmlAddressBookStorage;

public class LoginPage extends UiPart<Region>{

    private static final String ICON = "/images/address_book_32.png";
    private static final String FXML = "loginPage.fxml";
    public static final String DEFAULT_PAGE = "default.html";
    private static final int MIN_HEIGHT = 600;
    private static final int MIN_WIDTH = 450;

    private final Logger logger = LogsCenter.getLogger(this.getClass());
    private Stage primaryStage;
    private MainWindow mainWindow;

    private Config config;
    private Storage storage;
    private UserPrefs prefs;
    private Logic logic;
    private Model model;
    private AccountsStorage accPrefs;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    public LoginPage(Stage primaryStage, Config config, Storage storage, UserPrefs prefs, Logic logic, AccountsStorage accPrefs) {
        super(FXML);
        this.logic = logic;
        // Set dependencies
        this.primaryStage = primaryStage;
        this.config = config;
        this.prefs = prefs;
        this.accPrefs = accPrefs;

        // Configure the UI
        setTitle(config.getAppTitle());
        setIcon(ICON);
        setWindowMinSize();
        setWindowDefaultSize(prefs);
        Scene scene = new Scene(getRoot());
        primaryStage.setScene(scene);
        registerAsAnEventHandler(this);
    }



    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @FXML
    private void handleLoginEvent() {
        logger.info("Trying to login");
        String uname = username.getText();
        String pword = password.getText();
        if (checkValid(uname, pword)) {
            UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(config.getUserPrefsFilePath());
            AddressBookStorage addressBookStorage = new XmlAddressBookStorage("./data/" + uname + "addressbook.xml");
            storage = new StorageManager(addressBookStorage, userPrefsStorage);
            model = initModelManager(storage, prefs);
            logic = new LogicManager(model);

            mainWindow = new MainWindow(primaryStage, config, storage, prefs, logic, accPrefs);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();
        } else {
            logger.info("Wrong name or password!");
        }
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            handleLoginEvent();
        }

    }


    GuiSettings getCurrentGuiSetting() {
        return new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
    }

    private void setTitle(String appTitle) {
        primaryStage.setTitle(appTitle);
    }

    /**
     * Sets the given image as the icon of the main window.
     *
     * @param iconSource e.g. {@code "/images/help_icon.png"}
     */
    private void setIcon(String iconSource) {
        FxViewUtil.setStageIcon(primaryStage, iconSource);
    }

    void show() {
        primaryStage.show();
    }

    private void setWindowDefaultSize(UserPrefs prefs) {
        primaryStage.setHeight(prefs.getGuiSettings().getWindowHeight());
        primaryStage.setWidth(prefs.getGuiSettings().getWindowWidth());
        if (prefs.getGuiSettings().getWindowCoordinates() != null) {
            primaryStage.setX(prefs.getGuiSettings().getWindowCoordinates().getX());
            primaryStage.setY(prefs.getGuiSettings().getWindowCoordinates().getY());
        }
    }

    void hide() {
        primaryStage.hide();
    }

    void releaseResources() {
        if (mainWindow != null) {
            mainWindow.getBrowserPanel().freeResources();
        }
    }

    private void setWindowMinSize() {
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setMinWidth(MIN_WIDTH);
    }

    private boolean checkValid(String username, String password) {
        return accPrefs.getHm().get(username).equals(password);
    }

    /**
     * Returns a {@code ModelManager} with the data from {@code storage}'s address book and {@code userPrefs}. <br>
     * The data from the sample address book will be used instead if {@code storage}'s address book is not found,
     * or an empty address book will be used instead if errors occur when reading {@code storage}'s address book.
     */
    private Model initModelManager(Storage storage, UserPrefs userPrefs) {
        Optional<ReadOnlyAddressBook> addressBookOptional;
        ReadOnlyAddressBook initialData;
        try {
            addressBookOptional = storage.readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("Data file not found. Will be starting with a sample AddressBook");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            initialData = new AddressBook();
        }
        return new ModelManager(initialData, userPrefs);
    }
}