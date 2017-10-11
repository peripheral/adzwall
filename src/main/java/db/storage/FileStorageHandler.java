package db.storage;

import java.io.File;

import javax.management.RuntimeErrorException;

import db.entities.Advertisement;
import db.entities.MediaFile;

public class FileStorageHandler {

	public void releaseResources(Advertisement ad) {
		String basePath = System.getProperty("jboss.server.data.dir");
		File newFile;
		for(MediaFile fName:ad.getAttachedFiles()){
			try{
				newFile = new File(basePath+"/media/"+ad.getOwnerEmail()+"/"+ad.getId()+"/"+fName);
				newFile.delete();
			}catch(Exception e){
				throw new RuntimeException("Failed to delete file.",e);
			}
		}
		try{
			newFile = new File(basePath+"/media/"+ad.getOwnerEmail()+"/"+ad.getId());
			newFile.delete();
		}catch(Exception e){
			throw new RuntimeException("Failed to delete file.",e);
		}
		
	}
}
