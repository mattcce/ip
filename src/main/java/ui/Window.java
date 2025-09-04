package ui;

import clanker.Clanker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import ui.components.DialogBox;

/**
 * Controller for the main GUI.
 */
public class Window extends AnchorPane {
    private Clanker clanker;

    @FXML
    private ScrollPane scrollPane = new ScrollPane();
    @FXML
    private VBox dialogContainer = new VBox();
    @FXML
    private TextField userInput = new TextField();
    @FXML
    private Button sendButton = new Button("Send");

    private Image userImage = new Image(this.getClass().getResourceAsStream("/assets/monke.jpg"));
    private Image clankerImage = new Image(this.getClass().getResourceAsStream("/assets/clanker.jpg"));

    /**
     * Constructs main window with all base components.
     */
    public Window() {
        this.scrollPane.setContent(dialogContainer);
        this.getChildren().addAll(scrollPane, userInput, sendButton);
    }

    @FXML
    private void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        this.clanker = Clanker.initialise(this::displayClankerResponse);
    }

    private void displayClankerResponse(String response) {
        this.dialogContainer.getChildren().addAll(DialogBox.getClankerDialog(response, clankerImage));
    }

    void setup() {
    }

    @FXML
    private void handleUserInput() {
        String input = this.userInput.getText();
        this.displayUserInput(input);
        this.clanker.handleCommand(input);
        this.userInput.clear();
    }

    private void displayUserInput(String input) {
        this.dialogContainer.getChildren().addAll(DialogBox.getUserDialog(input, userImage));
    }
}

