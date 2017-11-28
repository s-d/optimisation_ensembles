package solver;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.util.Map;

class ArgParser {

    enum Problems {
        BIN,
        FLO,
        SAT
    }

    enum Experiments {
        ALG,
        ENS,
        ELT,
        RAN
    }

    private static ArgumentParser argParser;
    private static Namespace argResults;

    private static ArgumentParser initParser() {
        argParser = ArgumentParsers.newFor("optimisation_ensembles")
                .singleMetavar(true)
                .fromFilePrefix("@")
                .build()
                .defaultHelp(true)
                .description("Test Ensembles of Algorithms or Heuristics on optimisation problems");

        argParser.addArgument("experiment")
                .type(Arguments.caseInsensitiveEnumType(Experiments.class))
                .help("experiment to be tested")
                .required(true);

        argParser.addArgument("problem")
                .type(Arguments.caseInsensitiveEnumType(Problems.class))
                .help("problem domain to be tested")
                .required(true);

        argParser.addArgument("iterations")
                .type(Integer.class)
                .help("number of experiment executions");

        argParser.addArgument("-i", "--index")
                .type(Integer.class)
                .help("starting ensemble ID")
                .setDefault(0);

        return argParser;
    }

    static Map<String, Object> parseArgs(String[] args) {
        ArgumentParser parser = initParser();
        try {
            argResults = parser.parseArgs(args);
            validateArgs();
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
        return argResults.getAttrs();
    }

    private static void validateArgs() {
        if ((int) (argResults.getAttrs().get("iterations")) < 0) {
            System.out.println("iterations must be a positive integer. ");
            argParser.printUsage();
            System.exit(1);
        }
        if((int) argResults.getAttrs().get("index") < 0){
            System.out.println("index must be a positive integer. ");
            argParser.printUsage();
            System.exit(1);
        }
    }
}