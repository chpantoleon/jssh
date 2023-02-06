package gr.cpad.commands;

import gr.cpad.domain.Server;
import gr.cpad.services.ServerService;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.inject.Inject;

@Command(name = "save", aliases = "s", description = "Saves the ssh connection configuration")
public class Save implements Runnable {

    private final static String defaultConfigurationDirectory = "/.jssh/config.json";

    @CommandLine.Parameters(description = "Server connection alias")
    private String alias;

    @CommandLine.Option(
            names = {"-s", "--server"},
            description = "Server address",
            required = true
    )
    private String ipAddress;

    @CommandLine.Option(
            names = {"-u", "--username"},
            description = "Connection username",
            required = true
    )
    private String username;

    @CommandLine.Option(
            names = {"-p", "--port"},
            description = "SSH port"
    )
    private String port;

    @CommandLine.Option(
            names = {"-k", "--key"},
            description = "Private key path"
    )
    private String keyPath;

    @Inject
    ServerService serverService;

    @Override
    public void run() {
        var server = new Server();
        server.setAlias(alias);
        server.setIpAddress(ipAddress);
        server.setUsername(username);
        server.setPort(port);
        server.setKeyPath(keyPath);
        server.setUsesNonDefaultPrivateKey(null != keyPath);

        if (serverService.getConfiguration(alias) != null) {
            System.err.println("Server already exists");
            System.exit(1);
        }

        var result = serverService.storeConfiguration(server);

        System.exit(result ? 0 : 1);
    }

}
