package sapic

import groovy.util.logging.Log
import groovyx.net.http.AsyncHTTPBuilder
import groovyx.net.http.URIBuilder

import static groovyx.net.http.ContentType.JSON

@Log(category='SapiClient')
@Singleton(lazy = true)
@Mixin([sapic.api.Bibs, sapic.api.Items])
class SapiClient {

    // --------------
    // Initialization
    // --------------

    // todo: move these properties to params or to a config
    def apiHTTPScheme = "http"
    def apiHost = "http://sandbox.iii.com"
    def apiPathRoot = "/iii/sierra-api"
    def apiVersion = "v0.5"
    def apiKey = System.getenv('SIERRA_API_KEY')

    // initialize the http client
    def httpClient = new AsyncHTTPBuilder(poolSize: 20, uri: this.apiHost, contentType: JSON)

    // -------------
    // HTTP Requests
    // -------------

    // ---------------------------
    // sends an async http get and returns JSON response
    //      accepts: Map [path: path, query:query]
    //      returns: JSON object
    def get(Map args=[:]) {
        def query = args.query

        // if present, inject the apiKey into the query params
        (this.apiKey) ? query.apiKey = this.apiKey : null
        def path = getPathForVerb(verb: 'GET', path: args.path, query: query)

        // make the http request
        def request = this.httpClient.get(path: path, query: query) {resp, json ->
            assert resp.status == 200
            json
        }
        // a bit of the async voodoo. Wait for the request to complete,
        // and return the json per the closure's return
        while ( ! request.done  ) { Thread.sleep 250 }
        request.get()
    }

    // -------
    // HELPERS
    // -------

    // ---------------------------
    // handy path builder and URI logger
    //      accepts: Map [verb: verb, path: path, query: query]
    //      returns: String uriPath
    def getPathForVerb(Map args=[:]) {
        def uri = buildURI(path: args.path, query: args.query)
        def uriPath = uri.getPath()
        log.info "${args.verb}-ing ${uri.toString()}"
        return uriPath
    }

    // ---------------------------
    // builds URI object from args
    //      accepts: Map [path: path, query:query]
    //      returns: URI object
    def buildURI(Map args=[:]) {
        def uri = new URIBuilder(this.apiHost).with {
            scheme = this.apiHTTPScheme
            path = "${this.apiPathRoot}/${this.apiVersion}/${args.path}"
            query = args.query
            return it
        }
        return uri
    }

}
