package solver;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

import java.util.Map;


public class TestArgParser {
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();


    @Test
    public void testShortHelpArg() {
        String[] args = {"-h"};
        exit.expectSystemExitWithStatus(1);
        ArgParser.parseArgs(args);
    }
    @Test
    public void testLongHelpArg() {
        String[] args = {"--help"};
        exit.expectSystemExitWithStatus(1);
        ArgParser.parseArgs(args);
    }
    @Test
    public void testNoArgs(){
        String[] args = {};
        exit.expectSystemExitWithStatus(1);
        ArgParser.parseArgs(args);
    }
}