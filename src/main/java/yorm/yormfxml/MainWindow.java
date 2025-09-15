package yorm.yormfxml;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import yorm.enums.CommandEnum;
import yorm.yormjava.Yorm;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Yorm yorm;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image yormImage = new Image(this.getClass().getResourceAsStream("/images/DaYorm.png"));

    @FXML
    public void initialize() {
        this.dialogContainer.heightProperty().addListener((obs, oldVal, newVal) -> this.scrollPane.setVvalue(1.0));
    }

    /** Injects the Yorm instance */
    public void setYorm(Yorm yorm) {
        this.yorm = yorm;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Yorm's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = yorm.getResponse(input);
        CommandEnum commandType = yorm.getCommandType();
        this.dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, this.userImage),
                DialogBox.getYormDialog(response, this.yormImage, commandType)
        );
        this.userInput.clear();
    }
}
