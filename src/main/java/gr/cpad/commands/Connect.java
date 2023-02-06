package gr.cpad.commands;

import gr.cpad.services.ServerService;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;
import java.io.IOException;

@Command(name = "connect", description = "Connects to a server", aliases = "c")
public class Connect implements Runnable {

    @CommandLine.Parameters(description = "Alias of the connection", defaultValue = "")
    private String alias;

    @Inject
    ServerService serverService;

    @Override
    public void run() {
        var server = serverService.getConfiguration(alias);
        // TODO add terminal to configuration
//        String[] args = new String[] {"gnome-terminal", "--" , "/bin/bash", "-c", "ssh " + server.getUsername() + "@" + server.getIpAddress()};
        String[] args = new String[] {"terminator", "-x" , "/bin/bash", "-c", "ssh " + server.getUsername() + "@" + server.getIpAddress()};
        try {
            new ProcessBuilder(args).start();
        } catch (IOException e) {
            System.err.println("Something went wrong while trying to connect.");
            System.exit(1);
        }
    }

}
