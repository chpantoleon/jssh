package gr.cpad.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gr.cpad.services.ServerService;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;

@Command(name = "print", aliases = "p", description = "Prints the selected ssh connection")
public class Print implements Runnable {

    @CommandLine.Parameters(description = "Alias of the connection", defaultValue = "")
    private String alias;

    @Inject
    ServerService serverService;

    @Override
    public void run() {
        try {
            var server = serverService.getConfiguration(alias);
            var objectMapper = new ObjectMapper();
            System.out.println(objectMapper.writeValueAsString(server));
        } catch (JsonProcessingException e) {
            System.err.println("Could not process entity");
            System.exit(1);
        }

    }

}
