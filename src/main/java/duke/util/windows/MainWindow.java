package duke.util.windows;

import java.io.IOException;

import duke.util.controller.Duke;
import duke.util.tasks.Task;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;






/**
 * Controller for MainWindow. Provides the layout for the other controls.
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
    @FXML
    private Text taskDisplay;
    @FXML
    private ListView<Task> listOfTasks;
    @FXML
    private BorderPane borderPane;

    private Duke duke;


    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));


    /**
     *Method called after the fields have been initialized in fxml.
     */
    @FXML
    public void initialize() {
        scrollPanePropertyInit();
        listSetSelectMultiple();
    }

    private void scrollPanePropertyInit() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        scrollPane.hvalueProperty().bind(dialogContainer.widthProperty());
        //To bind the width of the scroll pane to the dialog container
        scrollPane.fitToWidthProperty().bind(dialogContainer.fillWidthProperty());
    }

    private void listSetSelectMultiple() {
        listOfTasks.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    /**
     * Initialises a Duke for this anchorPane.
     *
     * @param d The duke to be used.
     */
    public void setDuke(Duke d) {
        duke = d;
        duke.setOut(listOfTasks);
        duke.printList();
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = duke.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        duke.printList();
        userInput.clear();
    }

    /**
     * Handle the addition of tasks.
     *
     */
    @FXML
    private void addTask() {

        try {
            Stage stage = new Stage();
            TaskInputWindow.setStage(stage);
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/TaskInputWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }


    }





    /**
     * Handle the removal of tasks through the GUI
     *
     */
    @FXML
    private void handleRemoveEvent() {
        duke.removeHandler(this.listOfTasks.getSelectionModel().getSelectedItems());
        duke.printList();
    }

    /**
     * Handle the marking of done tasks through the GUI
     *
     */
    @FXML
    private void handleDoneTasks() {
        ObservableList<Task> tasks = this.listOfTasks.getSelectionModel().getSelectedItems();
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).done();
        }
        duke.printList();
    }



}

