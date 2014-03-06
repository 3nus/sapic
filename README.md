sapic
=====

Groovy Sierra API Client. Get it? S.API.C. Heh.

Sapic is an example of interfacing with the Innovative's [Sierra](http://sierra.iii.com/) API using a groovy client library. The library is in its infancy, implementing only the basic get requests for /bibs and /items.

Using the AsyncHTTPBuilder() and its explicit JSON parsing capabilities, sapic wraps the Sierra API with simple methods and marshalls the JSON responses into Groovy objects, which in turn will provide simple interactive methods for acting on the objects.

Configuring sapic
-----------------

Sapic currently has hardcoded parameters as properties on SapiClient(), which point the client at Innovative's sandbox environment. This will change soon, don't worry. The apiKey is set via an environmental variable (SIERRA_API_KEY) for now.

Sample usage
------------

```groovy
import sapic.SapiClient
import sapic.resources.Bib
import sapic.resources.Bibs

def client = new SapiClient()
def bibs = client.get_bibs(limit: 5)
assert bibs.class == sapic.resources.Bibs
assert bibs[0].class == sapic.resources.Bib

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
class=class sapic.resources.Bib
materialType={value=Book, code=a}
country=New York (State)
deletedDate=null
author=Wojciechowska, Maia, 1927-
```