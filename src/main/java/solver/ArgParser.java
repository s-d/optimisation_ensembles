package solver;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

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

    private static Namespace namespace;

    static ArgumentParser initParser() {
        ArgumentParser parser = ArgumentParsers.newFor("optimisation_ensembles")
                .singleMetavar(true)
                .fromFilePrefix("@")
                .build()
                .defaultHelp(true)
                .description("Test Ensembles of Algorithms or Heuristics on optimisation problems");

        parser.addArgument("experiment")
                .type(Arguments.caseInsensitiveEnumType(Experiments.class))
                .help("experiment to be tested")
                .required(true);

        parser.addArgument("problem")
                .type(Arguments.caseInsensitiveEnumType(Problems.class))
                .help("problem domain to be tested")
                .required(true);

        parser.addArgument("iterations")
                .type(Integer.class)
                .help("number of experiment executions")
                .setDefault(50);

        parser.addArgument("-i", "--index")
                .type(Integer.class)
                .help("starting ensemble ID")
                .setDefault(0);

        return parser;
    }

    public static void main(String[] args) {
        ArgumentParser parser = initParser();
        try {
            Namespace res = parser.parseArgs(args);
            System.out.println(res);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

    }
}