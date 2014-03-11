package sapic

import groovy.util.logging.Log
import groovyx.net.http.AsyncHTTPBuilder
import groovyx.net.http.URIBuilder
import sapic.config.CustomConfiguration
import sapic.config.DefaultConfiguration

import static groovyx.net.http.ContentType.JSON

@Log
@Mixin([sapic.api.Bibs, sapic.api.Items])
@Singleton(lazy=true)
class SapiClient {

    // --------------
    // Initialization
    // --------------
    def config = new DefaultConfiguration()
    def httpClient = new AsyncHTTPBuilder(poolSize: 20, uri: this.config.host, contentType: JSON)

    // -------------
    // Configuration
    // -------------
    def reload() {
        config = new DefaultConfiguration()
    }

    // ---------------------------
    // load specified environment configuration from default config
    //      accepts: String
    def loadConfig(String environment) {
        config.loadConfig(environment)
    }

    // ---------------------------
    // load configuration from custom config file (as url)
    //      accepts: String
    def loadConfig(URL customSettings) {
        config = new CustomConfiguration()
        config.loadConfig(customSettings)
    }

    // ---------------------------
    // load specified environment configuration from custom config file
    //      accepts: String
    def loadConfig(String environment, URL customSettings) {
        config = new CustomConfiguration()
        config.loadConfig(environment, customSettings)
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
        queryParams.apiKey = this.config.key

        def uri = new URIBuilder(this.config.host).with {
            scheme = this.config.scheme
            port = this.config.port
            path = "${this.config.rootPath}/${this.config.version}/${args.path}"
            query = queryParams
            return it
        }
        log.info "${args.verb}-ing ${uri.toString()}"
        return uri
    }

}
