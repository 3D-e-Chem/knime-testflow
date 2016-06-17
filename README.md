Test Knime workflows from a Junit test.

[![Build Status](https://travis-ci.org/3D-e-Chem/knime-testflow.svg?branch=master)](https://travis-ci.org/3D-e-Chem/knime-testflow)

The Knime Testing Framework can run a test workflow either:
* Inside Knime, if you right-click on a workflow in your local workspace, you can select "Run as workflow test".
* From the command line, using `knime -application org.knime.testing.NGTestflowRunner -root <workflow dir>`.

This repo gives you another option run a test workflow inside of a Junit @Test method declaration.

This project uses [Eclipse Tycho](https://www.eclipse.org/tycho/) to perform build steps.

# Usage

Using the plugin requires several steps.

## 1. Add repository

This plugin is available in the `https://3d-e-chem.github.io/updates` update site.

To make use of in a Tycho based project add to the `<repositories>` tag of the `pom.xml` file the following:
```
<repository>
    <id>3d-e-chem</id>
    <layout>p2</layout>
    <url>https://3d-e-chem.github.io/updates</url>
</repository>
```

## 2. Add dependency to tests

In the `Require-Bundle` attribute of the `META-INF/MANIFEST.MF` of the tests module add
```
nl.esciencecenter.e3dchem.knime.testing.plugin;bundle-version="[1.0.0,2.0.0)",
```

## 3. Add test workflow

Create a test workflow as described in the "Testing Framework" manual that you get when you install the "KNIME Testing Framework" (look in plugins/org.knime.testing_x.y.z/Regression Tests.pdf).

Place the workflow as a directory inside the `src/knime/` directory of the tests module.

## 4. Add test

Create a new test class and inside the class put the following:
```
@Rule
public ErrorCollector collector = new ErrorCollector();
private TestFlowRunner runner;

@Before
public void setUp() {
    TestrunConfiguration runConfiguration = new TestrunConfiguration();
    runner = new TestFlowRunner(collector, runConfiguration);
}

@Test
public void test_simple() throws IOException, InvalidSettingsException, CanceledExecutionException,
        UnsupportedWorkflowVersionException, LockFailedException, InterruptedException {
    File workflowDir = new File("src/knime/my-workflow-test");
    runner.runTestWorkflow(workflowDir);
}
```

This will test the workflow put in `src/knime/my-workflow-test` in the previous step.

This will run minimal checks, to check more configure `runConfiguration` object.  
For example add some more checks by adding 
```
runConfiguration.setTestDialogs(true);
runConfiguration.setReportDeprecatedNodes(true);
runConfiguration.setCheckMemoryLeaks(true);
```

## 5. Run tests

```
mvn verify
```

The test results can be found in the `T E S T S` section of the standard output.

## 6. Add GUI testing to Travis-CI.

As you might have noticed during the previouse step, running test will quickly show some dialogs and windows.
To show graphical user elements an X-server is required, sadly Travis-CI does not run an X-server. 
A temporary X-server can be run with Xvfb, which is luckily available on all Travis-CI environments.

Prepend `xvfb-run` before the `mvn verify` command in the `.travis.yml` file.

For example
```
script: xvfb-run mvn verify -B
```

# Build

```
mvn verify
```

An Eclipse update site will be made in `p2/target/repository` repository.
The update site can be used to perform a local installation.

# Development

Steps to get development environment setup:

1. Download KNIME SDK from https://www.knime.org/downloads/overview
2. Install/Extract/start KNIME SDK
3. Start SDK
4. Install m2e (Maven integration for Eclipse)

    1. Goto Help > Install new software ...
    2. Make sure Update site is http://update.knime.org/analytics-platform/3.1 is in the pull down list otherwise add it
    3. Select --all sites-- in work with pulldown
    4. Select m2e (Maven integration for Eclipse)
    5. Install software & restart

5. Import this repo as an Existing Maven project

During import the Tycho Eclipse providers must be installed.

# New release

1. Update versions in pom files with `mvn org.eclipse.tycho:tycho-versions-plugin:set-version -DnewVersion=<version>` command.
2. Manually update version of "source" feature in `p2/category.xml` file.
3. Commit and push changes
3. Create package with `mvn package`, will create update site in `p2/target/repository`
4. Append new release to an update site
  1. Make clone of an update site repo
  2. Append release to the update site with `mvn install -Dtarget.update.site=<path to update site>`
5. Commit and push changes in this repo and update site repo.

