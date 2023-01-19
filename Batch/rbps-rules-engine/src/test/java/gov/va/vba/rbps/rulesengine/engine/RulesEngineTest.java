package gov.va.vba.rbps.rulesengine.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import gov.va.vba.rbps.coreframework.xom.RuleExceptionMessages;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import junit.framework.Assert;
import org.junit.Test;

import java.io.File;

public class RulesEngineTest {

    final static String VETERAN_INPUT_PATH = "src/test/resources/veteran/input/";
    final static String VETERAN_OUTPUT_PATH = "src/test/resources/veteran/output/";

    final static String SPOUSE_INPUT_PATH = "src/test/resources/spouse/input/";
    final static String SPOUSE_OUTPUT_PATH = "src/test/resources/spouse/output/";

    public RulesEngineTest() {

    }

    @Test
    public void executeVeteranScenarios() throws Exception {
        File folder = new File(VETERAN_INPUT_PATH);

        for(File file: folder.listFiles()) {
            String inputFileName = file.getName();

            if(!file.isDirectory()) {
                // map to java object
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                Veteran veteran = mapper.readValue(new File(VETERAN_INPUT_PATH + inputFileName), Veteran.class);
                RuleExceptionMessages expectedExceptionMessages = mapper.readValue(new File(VETERAN_OUTPUT_PATH + "Exceptions" + inputFileName), RuleExceptionMessages.class);

                // call execute method in RulesEngine.java
                RulesEngine rulesEngine = new RulesEngine();
                Response response = rulesEngine.execute(veteran);

                // getOutputParameters exception messages
                RuleExceptionMessages actualExceptionMessages = (RuleExceptionMessages) response.getOutputParameters().get("Exceptions");

                // assert the right exceptionMessages were generated
                // assert the log file to make sure it took the right paths
                for (String exceptionMessage : expectedExceptionMessages.getMessages()) {
                    Assert.assertTrue(actualExceptionMessages.getMessages().contains(exceptionMessage));
                }
            }
        }

    }


    @Test
    public void executeSpouseScenarios() throws Exception {
        File folder = new File(SPOUSE_INPUT_PATH);

        for(File file: folder.listFiles()) {

        }

    }
/*    @Test
    void Scenario1Test() throws Exception {
        // getOutputParameters scenario yaml file
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        // map to java object
        Veteran veteran = mapper.readValue(new File("src/test/resources/veteran/input/ExceptionsVeteranScenario1.yaml"), Veteran.class);
        RuleExceptionMessages expectedExceptionMessages = mapper.readValue(new File("src/test/resources/veteran/output/ExceptionsVeteranScenario1.yaml"), RuleExceptionMessages.class);

        // call execute method in RulesEngine.java
        RulesEngine rulesEngine = new RulesEngine();
        Map<String, Object> response = rulesEngine.execute(veteran, new VeteranCommonDates());

        // getOutputParameters exception messages
        RuleExceptionMessages actualExceptionMessages = (RuleExceptionMessages) response.getOutputParameters("Exceptions");

        // assert the right exceptionMessages were generated
        // assert the log file to make sure it took the right paths
        for(String exceptionMessage: actualExceptionMessages.getMessages()) {
            Assert.assertTrue(expectedExceptionMessages.getMessages().contains(exceptionMessage));
        }

    }*/
}