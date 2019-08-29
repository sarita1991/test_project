package com.example.boot_jpa;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.boot_jpa.controller.FileStorageService;
import com.example.boot_jpa.model.UploadFileResponse;


import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
@RestController
public class DictionaryController {
	@Autowired
	private FileStorageService fileStorageService;


@PostMapping("/uploadFile") // Map ONLY GET Requests
public UploadFileResponse  addDictionaryFile (@RequestParam("file") MultipartFile file) {
	String fileName = fileStorageService.storeFile(file);
	 String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
             .path("/downloadFile/")
             .path(fileName)
             .toUriString();
	return new UploadFileResponse(fileName,fileDownloadUri,file.getContentType(),file.getSize());
}
@GetMapping("/searchDictionary/{fileName:.+}/{searchText}")
public String searchInDictionary(@PathVariable String fileName, HttpServletRequest request,@PathVariable String searchText) {
    // Load file as Resource
return fileStorageService.searchWord(fileName, searchText);
}
@GetMapping("/downloadFile/{fileName:.+}")
public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
    // Load file as Resource
    Resource resource = fileStorageService.loadFileAsResource(fileName);

    // Try to determine file's content type
    String contentType = null;
    try {
        contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
    } catch (IOException ex) {
        
    }

    // Fallback to the default content type if type could not be determined
    if(contentType == null) {
        contentType = "application/octet-stream";
    }

    return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
}
@GetMapping("/addWordInDictionary/{fileName:.+}/{searchText}")
public String addInFile(@PathVariable String fileName, HttpServletRequest request,@PathVariable String searchText) throws IOException {
    // Load file as Resource
return fileStorageService.addInDictionary(fileName, searchText);
}

}
