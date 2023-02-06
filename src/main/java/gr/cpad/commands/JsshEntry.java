package gr.cpad.commands;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Spec;

@TopCommand
@Command(
        mixinStandardHelpOptions = true,
        subcommands = {
                Connect.class,
                Save.class,
                Print.class,
                Delete.class,
                List.class,
                Setup.class
        }
)
public class JsshEntry implements Runnable {

    @Spec
    CommandLine.Model.CommandSpec spec;

    // If no arguments are passed then the help text shows up in the command line
    @Override
    public void run() {
        spec.commandLine().usage(System.out);
    }

}
