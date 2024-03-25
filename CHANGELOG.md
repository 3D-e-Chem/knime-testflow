# Change Log
All notable changes to this project will be documented in this file.
This project adheres to [Semantic Versioning](http://semver.org/).
The file is formatted as described on http://keepachangelog.com/.

## [1.0.5] - 2024-03-25

### Fixes

* IllegalArgumentException can be thrown by WorkflowTest#getHeapUsage() when heap is full ([#14](https://github.com/3D-e-Chem/knime-testflow/issues/14))

## [1.0.4] - 2023-11-20

### Changes

- Builds by default with KNIME 5.1, can be configured to build with KNIME 4.7 [#10](https://github.com/3D-e-Chem/knime-testflow/issues/10)

## [Unreleased]

### Changes

- Requires KNIME 4.0 [#7](https://github.com/3D-e-Chem/knime-testflow/issues/7)

## [1.0.2] - 2017-03-07

### Changes

- Requires KNIME 3.3

### Fixes

- Disabling check log message will leak log into next test (#5)
- NoSuchMethodError setMountpointRoot (#4)

## [1.0.1] - 2017-01-31

### Fixes

- Workaround for OSX SWT issues (#3)

## [1.0.0] - 2016-06-17

Initial release.

### Added

* Testing 2 workflows with several check permutations (#1)
