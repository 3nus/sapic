package sapic

import groovy.util.logging.Log
import groovyx.net.http.AsyncHTTPBuilder
import groovyx.net.http.URIBuilder
import sapic.config.Configuration

import static groovyx.net.http.ContentType.JSON

@Log
@Mixin([sapic.api.Bibs, sapic.api.Items])
@Singleton(lazy=true)
class SapiClient {

    // --------------
    // Initialization
    // --------------
    def config = Configuration.instance
    def httpClient = new AsyncHTTPBuilder(poolSize: 20, uri: this.config.api.host, contentType: JSON)

    // -------------
    // Configuration
    // -------------

    // ---------------------------
    // load specified environment configuration from default config
    //      accepts: String
    def loadConfig(String environment) {
        config.loadEnvironment(environment)
    }

    // ---------------------------
    // load configuration from custom config file (as url)
    //      accepts: String
    def loadConfig(URL customSettings) {
        config.reload(customSettings)
    }

    // ---------------------------
    // load specified environment configuration from custom config file
    //      accepts: String
    def loadConfig(String environment, URL customSettings) {
        config.reload(environment, customSettings)
    }

    // -------------
    // HTTP Requests
    // -------------

    // ---------------------------
    // sends an async http get and returns JSON response
    //      accepts: Map [path: path, query:query]
    //      returns: JSON object
    def get(Map args=[:]) {

        def query = args.query
        def uri = buildURI(verb: 'GET', path: args.path, query: query)

        // make the http request
        def request = this.httpClient.get(path: uri.getPath(), query: uri.getQuery()) {resp, json ->
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
    // builds URI object from args
    //      accepts: Map [path: path, query:query]
    //      returns: URI object
    def buildURI(Map args=[:]) {

        def queryParams = args.query
        queryParams.apiKey = this.config.api.key

        def uri = new URIBuilder(this.config.api.host).with {
            scheme = this.config.api.scheme
            port = this.config.api.port
            path = "${this.config.api.rootPath}/${this.config.api.version}/${args.path}"
            query = queryParams
            return it
        }
        log.info "${args.verb}-ing ${uri.toString()}"
        return uri
    }

}
