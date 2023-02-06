package gr.cpad.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.cpad.domain.Configuration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;

@ApplicationScoped
public class ConfigurationService {

    private final static String defaultConfigurationDirectory = "/.jssh/config.json";

    @Inject
    FileSystemInterface fileSystemService;

    public String getConfigPath() {
        var homeDirectory = System.getProperty("user.home");
        return homeDirectory + defaultConfigurationDirectory;
    }

    public Configuration getConfiguration() throws IOException {
        try {
            var contents = fileSystemService.getFileContent(getConfigPath());
            var objectMapper = new ObjectMapper();
            return objectMapper.readValue(contents, Configuration.class);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public String getServersDirectoryPath() throws IOException {
        return getConfiguration().getLocation();
    }

}
