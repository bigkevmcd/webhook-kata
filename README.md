# What's this?

This is a simple exercise that is meant to help teach the principles of testing
code.

It was deliberately not built in a TDD manner, *this is not production code*.

The `testing.sh` script will simulate a GitHub hook notification with an
appropriate payload.

```shell
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
