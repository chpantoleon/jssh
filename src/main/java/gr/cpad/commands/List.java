package gr.cpad.commands;

import gr.cpad.services.ServerService;
import picocli.CommandLine.Command;

import javax.inject.Inject;

@Command(name = "list", aliases = "l", description = "Prints all connections")
public class List implements Runnable {

    @Inject
    ServerService serverService;

    @Override
    public void run() {
        serverService.getAllConfigurations().forEach(System.out::println);
    }

}
