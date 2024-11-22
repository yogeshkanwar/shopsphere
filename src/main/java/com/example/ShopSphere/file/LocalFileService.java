package com.example.ShopSphere.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LocalFileService implements FileService{

	protected final Path root = Paths.get("../uploads/static");

	@PostConstruct
	public void init() throws IOException {
		Files.createDirectories(root);
	}

	public String save(MultipartFile file,String appendPath) throws IOException {
		String fileName=getFileName(file); 
		
		Files.createDirectories(Paths.get("../uploads/static/"+appendPath));
		Files.copy(file.getInputStream(), this.root.resolve(appendPath+"/"+fileName),StandardCopyOption.REPLACE_EXISTING);
		
		return getPath(appendPath, fileName);
	}

	public void delete(String filePath) throws IOException {
		File file=new File("../uploads/static/"+filePath);
		if(file.exists()) file.delete();
	}
}
