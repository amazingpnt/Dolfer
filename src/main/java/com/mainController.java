package com;

import java.io.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.input.*; // Required for Drag and Drop

public class mainController {

    @FXML
    private Label result;

    @FXML
    private VBox mainContainer; // Reference to the main layout for styling

    @FXML
    private Label folderPathInput; // Using a Label for display, as it can be empty

    // === Method 1: The "Hybrid" Button Browse ===
    @FXML
    private void chooseFolder() {
        DirectoryChooser folderChooser = new DirectoryChooser();
        folderChooser.setTitle("Selecting Folder");

        // Fixed null pointer: Ensure getScene() isn't null. mainContainer is best
        Window stage = mainContainer.getScene().getWindow();
        File selectedDirectory = folderChooser.showDialog(stage);

        if (selectedDirectory != null) {
            updatePathAndUI(selectedDirectory.getAbsolutePath());
        }
        // Result is handled by updatePathAndUI, so we don't need a separate set
    }

    // === Method 2: Handle "Organize" Button Click ===
    @FXML
    private void organize() {
        String path = folderPathInput.getText();
        if (path.isEmpty() || path.equals("Drop a folder here")) {
            result.setText("⚠️ Please select a valid folder first.");
            result.getStyleClass().add("result-error");
            result.getStyleClass().remove("result-success");
            return;
        }

        // Call the functional logic from OrganizeFiles
        String exitMessage = OrganizeFiles.organizeFolder(path);
        
        // Use styled feedback
        result.setText(exitMessage);
        result.getStyleClass().add("result-success");
        result.getStyleClass().remove("result-error");
    }

    // =========================================================
    // === Method 3: Add Drag and Drop Functionality ===
    // =========================================================
    
    @FXML
    public void handleDragOver(DragEvent event) {
        // Only accept the drag if it's a file list (not text/images)
        if (event.getGestureSource() != mainContainer && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
            // Visual feedback: change the container's style when hovering
            mainContainer.getStyleClass().add("drop-hover");
        }
        event.consume();
    }

    @FXML
    public void handleDragDropped(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        
        if (db.hasFiles()) {
            // Get the first dropped file
            File firstFile = db.getFiles().get(0);
            
            // Validate: Must be a DIRECTORY
            if (firstFile.isDirectory()) {
                updatePathAndUI(firstFile.getAbsolutePath());
                success = true;
            } else {
                result.setText("❌ Error: You must drop a *folder*, not a file.");
                result.getStyleClass().add("result-error");
                result.getStyleClass().remove("result-success");
            }
        }
        
        event.setDropCompleted(success);
        
        // Remove the visual hovering feedback when finished
        mainContainer.getStyleClass().remove("drop-hover");
        event.consume();
    }

    // Common UI updater used by both Browse and Drag/Drop
    private void updatePathAndUI(String path) {
        folderPathInput.setText(path);
        // Clean up any error messages when a path is found
        result.setText("");
        result.getStyleClass().remove("result-error");
        result.getStyleClass().remove("result-success");
    }
}