package com.example.boot_jpa.controller;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.boot_jpa.exception.FileStorageException;
import com.example.boot_jpa.exception.WordfileNotFoundException;
import com.example.boot_jpa.model.FileStorageProperties;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new WordfileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new WordfileNotFoundException("File not found " + fileName, ex);
        }
    }

public String searchWord(String fileName,String searchText) {
    try {
        Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if(resource.exists()) {
        	File f1=resource.getFile(); //Creation of File Descriptor for input file
            String[] words=null;  //Intialize the word Array
            FileReader fr = new FileReader(f1);  //Creation of File Reader object
            BufferedReader br = new BufferedReader(fr); //Creation of BufferedReader object
            String s;     
     
            int count=0;   //Intialize the word to zero
            while((s=br.readLine())!=null)   //Reading Content from the file
            {
               words=s.split(" ");  //Split the word using space
                for (String word : words) 
                {
                       if (word.equals(searchText))   //Search for the given word
                       {
                         count++;    //If Present increase the count by one
                       }
                }
            }
            fr.close();
            if(count!=0)  //Check for count not equal to zero
            {
            	return "Search text "+searchText+" is present for "+count+ " Times in the file";
            }
            else
            {
            	return "Search text "+searchText+" is not present in the file";
             }
           	
      
        } else {
            throw new WordfileNotFoundException("File not found " + fileName);
        }
    }
    catch(IOException ex)
    {
    	   throw new WordfileNotFoundException("File not found " + fileName);
    	           
    }
	
}
public  String addInDictionary(String fileName,String searchText) throws IOException
{
	   Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
       Resource resource = new UrlResource(filePath.toUri());
       if(resource.exists()) {
       String path=resource.getFile().getAbsolutePath();
    String textToAppend = "\r\n "+searchText; 
    FileWriter fileWriter = new FileWriter(path, true); //Set true for append mode
    PrintWriter printWriter = new PrintWriter(fileWriter);
    printWriter.println(textToAppend);  //New line
    printWriter.close();
    return "Success";
       }else {
    	     throw new WordfileNotFoundException("File not found " + fileName);        
       }
}
}