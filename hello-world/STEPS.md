# Steps

## Verify your setup

1. Java 11 or greater is needed.
2. Maven 3.6 or greater.
3. Build the project by invoking `./mvnw install`.

## Running the Launcher

1. `./mvnw -am -pl :launcher exec:exec@launch -Dexec.input=NYJavaSig`
2. Running from the IDE requires setting up a configuration to launch
 `org.moditect.layrry.examples.greeter.launcher.Launcher` with at least 1 argument.

## Running with external configuration

1. `./mvnw -am -pl :runner exec:exec@run-yaml -Dexec.input=NYJavaSig`
2. `./mvnw -am -pl :runner exec:exec@run-toml -Dexec.input=NYJavaSig`

## Running from local repository

1. Delete the `org/moditect/layrry/examples` packages from your local Maven repository.
2. `./mvnw -am -pl :runner exec:exec@run-yaml -Dexec.input=NYJavaSig` should fail now.
3. `./mvnw -am -pl :runner exec:exec@run-yaml-local -Dexec.input=NYJavaSig`.

## Task: Add another module

1. Create a greeter-3.0.0 Maven module.
2. Provide your own version of the Greeter class.
3. Create a `foobar` maven module than consumes greeter-3.0.0 as dependency.
4. `foobar` should have a class `FooBar` that invokes methods from the recently created greeter-3.0.0 module.
5. Update the app module with the foobar dependency.
6. Update the `App` class, it should call the `FooBar` class as well.
7. Decide how you wan to test it:
   - launcher: 
       - update the `pom.xml` to include the `foobar` Maven module.
       - update the `Launcher` class to include the `foobar` module.
   - external: update the `layers.[yml|toml]` files to include the `foobar` module.
8. `./mvnw install`.
9. Launch the application with your chosen mode.