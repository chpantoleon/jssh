package gr.cpad.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.cpad.domain.Server;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class ServerService {

    @Inject
    FileSystemInterface fileSystemService;

    @Inject
    ConfigurationService configurationService;

    public boolean storeConfiguration(Server server) {
        try {
            var objectMapper = new ObjectMapper();
            var content = objectMapper.writeValueAsString(server);
            fileSystemService.saveFile(configurationService.getServersDirectoryPath(), content);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Server getConfiguration(String alias) {
        try {
            var serverConfigurationFile = fileSystemService.getFile(configurationService.getServersDirectoryPath() + "/" + alias + ".json");
            return new ObjectMapper()
                    .readValue(
                            fileSystemService.getFileContent(
                                    serverConfigurationFile
                                            .getAbsoluteFile()
                                            .toPath()
                                            .toString()
                            ),
                            Server.class
                    );
        } catch (IOException e) {
            return null;
        }
    }

    public Set<String> getAllConfigurations() {
        try {
            var serverConfigurationFile = fileSystemService.getFile(configurationService.getServersDirectoryPath());
            return Stream.of(serverConfigurationFile.listFiles())
                    .filter(file -> !file.isDirectory())
                    .map(file -> file.getName().replace(".json", ""))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            System.err.println("Something went wrong while fetching server directory.");
            return null;
        }
    }

    public boolean deleteConfiguration(String alias) {
        try {
            var serverConfigurationFile = fileSystemService.getFile(configurationService.getServersDirectoryPath() + "/" + alias + ".json");
            return serverConfigurationFile.delete();
        } catch (IOException e) {
            return false;
        }
    }

}
