package simpleTune.gui;

import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.filechooser.FileFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import simpleTune.gui.data.ApplicationStateManager;
import simpleTune.gui.interfaces.TuningEntity;

public class SimpleTuneFileFilter extends FileFilter {
	private Vector<String> suffixList = new Vector<String>();
	private Log logger = LogFactory.getLog(getClass());
	
	public SimpleTuneFileFilter(){
		
		// Prepare list of known suffixes
		Vector<TuningEntity> tuningEntities = ApplicationStateManager.getTuningEntities();
		Iterator<TuningEntity> iterator = tuningEntities.iterator();
		while(iterator.hasNext()){
			TuningEntity tuningEntity = iterator.next();
			String[] fileSuffixes = tuningEntity.getFileSuffixes();
			for(int i = 0; i < fileSuffixes.length; i ++){
				this.suffixList.add(fileSuffixes[i].toLowerCase());
			}
		}
	}
	
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = getExtension(f);
        if (extension != null) {
            if (this.suffixList.contains(extension.toLowerCase())){
                    return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public String getDescription() {
    	String message = "Supported File Extensions : ";
    	Iterator<String> iterator = this.suffixList.iterator();
    	int counter = 0;
    	while(iterator.hasNext()){
    		counter++;
    		String temp = iterator.next();
    		
    		if(this.suffixList.size() == counter){
    			message += temp;
    		}else{
    			message += temp+",";
    		}
    		
    	}
    	
        return message;   
    }
    
    private String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}

