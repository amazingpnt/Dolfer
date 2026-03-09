import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class OrganizeFiles{
    public static String organizeFolder(String folderPath){
        File folder=new File(folderPath);
        if(!folder.exists() || !folder.isDirectory()){
            return "Invalid folder path";
        }

        File[] files=folder.listFiles();
        if(files==null){
            return "Folder is empty";
        }
        for(File file: files){
            if(file.isFile()){
                String name=file.getName();
                String ext=getExtension(name);
                if(ext.isEmpty()) ext="no_extension";

                File extFolder=new File(folder, ext);
                if(!extFolder.exists()){
                    extFolder.mkdir();
                }

                try{
                    Files.move(file.toPath(),
                               new File(extFolder, name).toPath(),
                               StandardCopyOption.REPLACE_EXISTING);
                }catch (IOException e){
                    e.printStackTrace();
                    return "Failed to move file: "+name;
                }
            }
        }
        return "Folder has been organize successfuly";
    }

    private static String getExtension(String filename){
        int lastDot=filename.lastIndexOf('.');
        if(lastDot==-1 || lastDot==filename.length()-1){
            return "";
        }
        return filename.substring(lastDot+1);
    }
}