package com;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class OrganizeFiles{
    private static String flattenFolder(String folderPath){
        File folder=new File(folderPath);

        if(!folder.exists() || !folder.isDirectory()){
            return "Invalid folder path";
        }

        Path rootPath=folder.toPath();

        try(Stream<Path> paths=Files.walk(rootPath)){
            Path[] nestedFiles=paths
                    .filter(Files::isRegularFile)
                    .filter(path -> !path.getParent().equals(rootPath))
                    .toArray(Path[]::new);

            for(Path sourcePath:nestedFiles){
                String filename=sourcePath.getFileName().toString();
                Path targetPath=rootPath.resolve(filename);

                if(Files.exists(targetPath)){
                    targetPath=getNonConflictingPath(rootPath, filename);
                }

                Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }
        }catch(IOException e){
            e.printStackTrace();
            return "Failed to flatten folder";
        }

        return "Folder has been flattened successfully";
    }

    public static String organizeFolder(String folderPath){
        flattenFolder(folderPath);
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

    private static Path getNonConflictingPath(Path rootPath, String filename){
        String name=filename;
        String ext="";

        int lastDot=filename.lastIndexOf('.');
        if(lastDot>0){
            name=filename.substring(0, lastDot);
            ext=filename.substring(lastDot);
        }

        int counter=1;
        Path candidate=rootPath.resolve(filename);
        while(Files.exists(candidate)){
            candidate=rootPath.resolve(name+"_"+counter+ext);
            counter++;
        }
        return candidate;
    }
}
