[![Build Status](https://travis-ci.org/carueda/tscfg.svg?branch=master)](https://travis-ci.org/carueda/tscfg)

## what is this?

tscfg is a command line tool that takes a configuration specification 
parseable by [Typesafe Config](https://github.com/typesafehub/config)
and generates all the Java boiler-plate to make the definitions 
available in type safe POJOs.

Typesafe Config is used by the tool for the generation, and required for compilation 
and execution of the generated classes in your code.

### status

This tool was an motivated by the lack of something similar to 
[PureConfig](https://github.com/melrief/pureconfig)
but for java (please point to any that I have missed!)
It's usable to some extent but can be improved in several ways
(for example, missing types include lists, durations, ..).
Feel free to play, fork, enter issues, submit PRs, etc.

Of course, avoiding boiler-plate is in general much easier in Scala 
than in Java!
(PureConfig, for example, uses case classes for the configuration spec).

In tscfg's approach, the configuration spec is itself also captured in a configuration file
so the familiar syntax/format (as supported by Typesafe Config) is still used.
With this input the tool generates corresponding POJO classes. 


## configuration spec

It's a regular configuration file where each field value indicates the
corresponding type and, optionally, a default value, for example:

```properties
endpoint {
  # a required String
  path = "string"

  # a String with default value "http://example.net"
  url = "String | http://example.net"

  # an optional Integer with default value null
  serial = "int?"

  interface {
    # an int with default value 8080
    port = "int | 8080"
  }
}
```

Any unrecognized explicit type is still processed by inferring the type according to the given value.
So, any existing regular configuration file can be given to tscfg.

> note: not implemented yet; any unrecognized type is handled as a required string (ie.,
as if "string" was given)

## generation

```shell
$ java -jar tscfg-x.y.z.jar

tscfg x.y.z
USAGE:
   tscfg.Main --spec inputFile [--packageName pn] [--className cn] [--destDir dd]
   Defaults:
     packageName:  example
     className:    ExampleCfg
     destDir:      /tmp
 Output is written to $destDir/$className.java
```

So, with the example above saved in `def.example.conf` we can run:

```shell
$ java -jar tscfg-x.y.z.jar --spec def.example.conf

parsing: def.example.conf
generating: /tmp/ExampleCfg.java
```

to generate the class `example.ExampleCfg`.

## using the generated configuration objects

```java
File configFile = new File("my.conf");

// usual Typesafe Config mechanism to load the file
Config tsConfig = ConfigFactory.parseFile(configFile).resolve();
```

To access the configuration fields, instead of:
```java
Config endpoint = tsConfig.getConfig("endpoint");
String path    = endpoint.getString("path");
String url     = endpoint.hasPath("url")    ? endpoint.getString("url") : "http://example.net";
Integer serial = endpoint.hasPath("serial") ? endpoint.getInt("serial") : null;
int port       = endpoint.hasPath("port")   ? endpoint.getInt("interface.port") : 8080;
```

you can:
```java
ExampleCfg cfg = new ExampleCfg(tsConfig);
```
which will make all verifications about required settings and associated types.

then, while enjoying full type safety and the code completion and navigation capabilities of your IDE:
```java
String path    = cfg.endpoint.path;
String url     = cfg.endpoint.url;
Integer serial = cfg.endpoint.serial;
int port       = cfg.endpoint.interface_.port;
```

> note that java reserved words are appended "_"

An object reference will never be null if the corresponding field is required according to
the specification. It will only be null if it is marked optional with no default value and
has been omitted in the input configuration.
 
The generated code looks [like this](https://github.com/carueda/tscfg/blob/master/src/main/java/tscfg/example/ExampleCfg.java). 
Example of use [here](https://github.com/carueda/tscfg/blob/master/src/main/java/tscfg/example/Use.java).

## tests

- [ExampleSpec](https://github.com/carueda/tscfg/blob/master/src/test/scala/tscfg/example/ExampleSpec.scala). 
- [AccessorSpec](https://github.com/carueda/tscfg/blob/master/src/test/scala/tscfg/AccessorSpec.scala). 
- [KeySpec](https://github.com/carueda/tscfg/blob/master/src/test/scala/tscfg/KeySpec.scala). 
