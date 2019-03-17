package run;

import engine.ElementEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Launcher {
    private Logger LOGGER = LoggerFactory.getLogger(Launcher.class);
    private ElementEngine elementEngine;

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        if (args.length == 2) {
            launcher.elementEngine = new ElementEngine(args[0], args[1]);
            launcher.elementEngine.run();
        } else {
            launcher.LOGGER.error("Need two html file paths");
        }
    }
}
