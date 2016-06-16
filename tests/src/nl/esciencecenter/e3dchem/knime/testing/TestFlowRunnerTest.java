package nl.esciencecenter.e3dchem.knime.testing;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.knime.testing.core.TestrunConfiguration;

public class TestFlowRunnerTest {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
    @Test
    public void test_unsupported_loadsaveload() {
    	ErrorCollector collector = new ErrorCollector();
    	TestrunConfiguration runConfiguration = new TestrunConfiguration();
    	runConfiguration.setLoadSaveLoad(true);
    	
    	thrown.expect(UnsupportedOperationException.class);
        thrown.expectMessage("LoadSaveLoad is not supported");
        new TestFlowRunner(collector, runConfiguration);
    }
}
