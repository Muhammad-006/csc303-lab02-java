# Lab 02 - Java programming fundamentals (`lab02-java`)

Plain Java, no Android and no Gradle. Run everything from this folder with the terminal.

## Files
| File | Purpose |
|------|---------|
| `Main.java` | Tasks 1-7: types, control flow, arrays/collections, objects, polymorphism, exceptions, test matrix T1-T8 |
| `Device.java` | Private fields, validating constructor, getters, `summary()` |
| `Connectable.java` | Interface: `connect()`, `isConnected()` |
| `MobileDevice.java` | Subclass of `Device` that implements `Connectable` |
| `.gitignore` | Excludes `*.class` build output |

## Compile and run
```
javac Main.java Device.java Connectable.java MobileDevice.java
java -cp . Main
```

## Toolchain (fill in from your machine)
```
java -version
javac -version
```
| Item | Value |
|------|-------|
| java version | |
| javac version | |
| Git commit ID | |

## Test results (T1-T8)
Running `Main` prints every test as `ID | Input | Expected | Actual | PASS/FAIL`
and ends with a count. Expected final line: `Summary: 23 passed, 0 failed`.

| ID | Input / action | Expected | Result |
|----|----------------|----------|--------|
| T1 | batteryStatus(0), (19), (20), (100) | LOW, LOW, READY, READY | PASS |
| T2 | batteryStatus(-1), (101) | IllegalArgumentException each | PASS |
| T3 | averageBattery({15, 80, 45}) | 46.666... internally, 46.67 displayed | PASS |
| T4 | averageBattery({}), null, {15, 101} | IllegalArgumentException each | PASS |
| T5 | new Device("Sensor-A", 15) | correct getters and summary | PASS |
| T6 | null/blank name, battery -1 or 101 | constructor rejects each | PASS |
| T7 | new MobileDevice; connect(); connect() | false, true, true | PASS |
| T8 | parse "80", "abc", "120" | READY; format message; range message | PASS |

## One issue and its fix
`java` and `javac` were not recognised in the terminal because the JDK `bin` folder was not on
`PATH`. The version commands exposed the problem; setting `JAVA_HOME` and adding `%JAVA_HOME%\bin`
to `PATH` fixed it.
