# What's this?

This is a simple exercise that is meant to help teach the principles of testing
code.

It was deliberately not built in a TDD manner, *this is not production code*.

## Setup

To import the project in IntelliJ goto "File | New Project from Existing Sources" and
choose the `build.gradle` file.

## How to start

You'll need to add a test framework to the Gradle configuration file.

I'd recommend adding a dependency on `io.dropwizard:dropwizard-testing` and
reading the Dropwizard [instructions](https://www.dropwizard.io/1.3.9/docs/manual/testing.html).

The code is not easy to test in its current form, you'll need to refactor
a bit to get started.

## Testing manually

The `testing.sh` script will simulate a GitHub hook notification with an
appropriate payload.

```
  $ ./testing.sh
```

Note that the tokens in the configuration file `config.yml` are _not_ valid, and
you will get an error response from the upstream service.

## Things to think about

 - Why is the code hard to test?
 - Why is it harder to refactor?
 - What should we do if Pushover is down?
 - What if we wanted to notify more than one user?
 - What if we wanted multiple notification methods, i.e. AWS SNS and Pushover?
