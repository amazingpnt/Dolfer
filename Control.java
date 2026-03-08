import java.util.*;
import java.io.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.*;


public class Control{
    @FXML
    private Label result;

    @FXML
    private TextField folderPathInput;

    @FXML
    private void chooseFolder(){
        DirectoryChooser folderChooser=new DirectoryChooser();
        folderChooser.setTitle("Selecting Folder");

        Window stage=folderPathInput.getScene().getWindow();
        File selectedDirectory=folderChooser.showDialog(stage);
        
        if(selectedDirectory!=null){
            folderPathInput.setText(selectedDirectory.getAbsolutePath());
        }
        else folderPathInput.setText("Folder path cannot be empty");
    }

    @FXML
    private void organize(){
        int exitCode=OrganizeFiles.organizeFolder(folderPathInput.getText());
        if(exitCode==0) result.setText("Folder has been orgnized");
        else if(exitCode==-1) result.setText("Invalid folder path");
        else if(exitCode==-2) result.setText("Folder is empty");
        else if(exitCode==-3) result.setText("Failed to move a file");
    }
}