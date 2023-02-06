package gr.cpad.commands;

import gr.cpad.services.ServerService;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;

@Command(name = "delete", aliases = "d", description = "Deletes a saved configuration")
public class Delete implements Runnable {

    @CommandLine.Parameters(description = "Alias of the connection", defaultValue = "")
    private String alias;

    @Inject
    ServerService serverService;

    @Override
    public void run() {
        if (serverService.deleteConfiguration(alias)) {
            System.out.printf("Configuration %s was successfully deleted\n", alias);
        } else {
            System.err.printf("Configuration %s was not deleted\n", alias);
        }
    }

}
