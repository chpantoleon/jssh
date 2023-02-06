package gr.cpad.services;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@ApplicationScoped
public class FileSystemService implements FileSystemInterface {

    public boolean saveFile(String filePath, String content) {
        var serverConfigurationFile = new File(filePath);

        try (var writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(serverConfigurationFile), StandardCharsets.UTF_8))) {
            var objectMapper = new ObjectMapper();
            writer.write(objectMapper.writeValueAsString(content));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public File getFile(String filePath) {
        return new File(filePath);
    }

    public String getFileContent(String filePath) throws IOException {
        return Files.readString(Path.of(filePath), StandardCharsets.UTF_8);
    }

}
