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
    
    private int pythonCaller(String path){
        try{
            List<String> command=List.of("python", "Python/organizer.py", path);

            ProcessBuilder processBuilder=new ProcessBuilder(command);
            Process process=processBuilder.start();
            int exitCode=process.waitFor();

            return exitCode;
        }
        catch(IOException | InterruptedException e){
            e.printStackTrace();
        }
        return -1;
    }

    @FXML
    private void chooseFolder(){
        DirectoryChooser folderChooser=new DirectoryChooser();
        folderChooser.setTitle("Selecting Folder");

        Window stage=folderPathInput.getScene().getWindow();
        File selectedDirectory=folderChooser.showDialog(stage);
        
        if(selectedDirectory!=null){
            folderPathInput.setText(selectedDirectory.getAbsolutePath());
        }
        else folderPathInput.setText("YOU MUST CHOOSE OR ENTER A FOLDER");
    }

    @FXML
    private void organize(){
        int exitCode=pythonCaller(folderPathInput.getText());
        if(exitCode==0) result.setText("Folder has been orgnized");
        else result.setText("An error occured");

    }
}