sapic
=====

Groovy Sierra API Client. Get it? S.API.C. Heh.

Sapic is an example of interfacing with the Innovative's [Sierra](http://sierra.iii.com/) API using a groovy client library. The library is in its infancy, implementing only the basic get requests for /bibs and /items.

Using the AsyncHTTPBuilder() and its explicit JSON parsing capabilities, sapic wraps the Sierra API with simple methods and marshalls the JSON responses into Groovy objects, which in turn will provide simple interactive methods for acting on the objects.

Configuring sapic
-----------------

Sapic contains an embedded default configuration file that facilitates testing against either the Innovative Sierra API sandbox, or against localhost. Using default configurations requires setting the apiKey via an environment variable:
```bash
export SIERRA_API_KEY=yourapikey
```
The sandbox can be hit with no configuration changes needed after grabbing a handle on the SapiClient singleton instance. To switch to localhost:
```groovy
import sapic.SapiClient

def client = SapiClient.instance
client.loadConfig('localhost')
```

Custom configurations, through which one can point a SapiClient instance at any Sierra API instance, can be instantiated in via config files placed on the file system.

To load a custom configuration file:
```groovy
import sapic.SapiClient

def client = SapiClient.instance
client.loadConfig(new File('/CustomSettings.groovy').toURL())

>>> Mar 12, 2014 8:57:59 AM sun.reflect.NativeMethodAccessorImpl invoke0
>>> INFO: Loaded configuration for a custom environment: [scheme:http, host:http://super-sierra-install.com, port:80, rootPath:/iii/sierra-api, version:v42, key:sekrit]
```

In a configuration file which implements the ConfigSlurper environments pattern, one can also switch between multiple environments:
```groovy
import sapic.SapiClient

def client = SapiClient.instance
client.loadConfig('my_sierra_env', new File('/CustomSettings.groovy').toURL())
client.loadConfig('my_other_sierra_env', new File('/CustomSettings.groovy').toURL())
```

Sample usage
------------

```groovy
import sapic.SapiClient
import sapic.entities.Bib
import sapic.entities.Bibs

def client = new SapiClient()
def bibs = client.get_bibs(limit: 5)
assert bibs.class == sapic.entities.Bibs
assert bibs[0].class == sapic.entities.Bib

bibs[0].properties.each {
    println it
}

createdDate=2003-05-08T08:55:00.000-07:00
catalogDate=1990-10-10T00:00:00.000-07:00
orderInfo=null
suppressed=false
title=Hey, what's wrong with this one?
marc=null
varFields=null
id=1000001
fixedFields=null
bibLevel={value=MONOGRAPH, code=m}
publishYear=1969
deleted=false
updatedDate=2009-07-06T08:30:13.000-07:00
lang=null
class=class sapic.entities.Bib
materialType={value=Book, code=a}
country=New York (State)
deletedDate=null
author=Wojciechowska, Maia, 1927-
```
