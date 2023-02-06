package gr.cpad.services;

import java.io.File;
import java.io.IOException;


public interface FileSystemInterface {

    boolean saveFile(String filePath, String content);

    File getFile(String filePath);

    String getFileContent(String filePath) throws IOException;

}
