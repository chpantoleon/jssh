package gr.cpad.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.cpad.domain.Configuration;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Command(name = "setup", description = "Performs an one-time setup for the tool")
public class Setup implements Runnable {

    @CommandLine.Option(
            names = {"-l", "--location"},
            defaultValue = ".jssh/servers",
            description = "Default location to store server configurations"
    )
    private String location;

    private final static String defaultConfigurationDirectory = ".jssh";

    // TODO refactor this
    @Override
    public void run() {
        String homeDirectory = System.getProperty("user.home");

        if (location.startsWith("..")) {
            throw new RuntimeException("Location should be inside home directory");
        }

        var path = homeDirectory + "/" + location;
        var configuration = new Configuration();
        configuration.setLocation(path);

        File directory = new File(path);
        File configurationDirectory = new File(homeDirectory + "/" + defaultConfigurationDirectory);
        File configurationFile = new File(homeDirectory + "/" + defaultConfigurationDirectory + "/config.json");

        if (!configurationDirectory.exists()) {
            var directoryCreated = configurationDirectory.mkdirs();
            if (!directoryCreated) {
                System.err.printf("Could not create directory at location %s\n", location);
            } else {
                System.out.printf("Configuration directory was created at %s\n", location);
            }
        } else {
            System.out.println("Configuration directory already exists!");
        }

        if (!directory.exists()) {
            var directoryCreated = directory.mkdir();
            if (!directoryCreated) {
                System.err.printf("Could not create directory at location %s", location);
                System.exit(1);
            }
        } else {
            if (!directory.isDirectory()) {
                System.err.printf("The selected location [%s] already exists and it is not a directory\n", location);
                System.exit(1);
            }

            var replaceFullDirectory = "";

            do {
                replaceFullDirectory = System.console().readLine("The directory already exists. Do you want to replace its contents? [y/N]");
            } while (!"y".equalsIgnoreCase(replaceFullDirectory) && !"n".equalsIgnoreCase(replaceFullDirectory) && !"".equalsIgnoreCase(replaceFullDirectory));

            replaceFullDirectory = replaceFullDirectory.toLowerCase();

            if ("y".equals(replaceFullDirectory)) {
                if (directory.delete()) {
                    var directoryCreated = directory.mkdir();
                    if (!directoryCreated) {
                        System.err.printf("Could not create directory at location %s\n", location);
                        System.exit(1);
                    }
                } else {
                    System.err.printf("Could not delete directory at location %s\n", location);
                }
            }
        }

        try (var writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(configurationFile), StandardCharsets.UTF_8))) {
            var objectMapper = new ObjectMapper();
            writer.write(objectMapper.writeValueAsString(configuration));
        } catch (FileNotFoundException e) {
            System.err.println("File not found in directory.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Something went wrong");
            System.exit(1);
        }

    }

}
