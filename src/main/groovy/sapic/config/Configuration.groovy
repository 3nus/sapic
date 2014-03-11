package sapic.config

import groovy.util.logging.Log

class ConfigurationException extends Exception {
    public ConfigurationException(String message) {
        super(message);
    }
}

class APIConfig {
    String scheme
    String host
    Integer port
    String rootPath
    String version
    String key
}

@Log(category='sapic.config.Configuration')
@Singleton(lazy=true)
class Configuration {

    def environment
    def api = new APIConfig()

    def Configuration() {
        setEnvironment()
        def settings = loadSettings(this.environment)
        this.api = settings.environment.api
        setApiKey()
        log.info "Loaded configuration for the '${this.environment}' environment: ${this.api}"
    }

    def reload() {
        setEnvironment()
        def settings = loadSettings(this.environment)
        this.api = settings.environment.api
        setApiKey()
        log.info "Loaded configuration for the '${this.environment}' environment: ${this.api}"
    }

    def reload(String environment) {
        setEnvironment(environment)
        def settings = loadSettings(environment)
        def customSettings = loadSettings(environment)
        settings = settings.merge(customSettings)
        this.api = settings.environment.api
        setApiKey()
        log.info "Loaded configuration for the '${this.environment}' environment: ${this.api}"
    }

    def reload(URL customConfig) {
        setEnvironment()
        def settings = loadSettings(customConfig)
        def customSettings = loadSettings(customConfig)
        settings = settings.merge(customSettings)
        this.api = settings.environment.api
        setApiKey()
        log.info "Loaded configuration for a custom environment: ${this.api}"
    }

    def reload(String environment, URL customConfig) {
        setEnvironment(environment)
        def settings = loadSettings(environment)
        def customSettings = loadSettings(environment, customConfig)
        settings = settings.merge(customSettings)
        this.api = settings.environment.api
        setApiKey()
        log.info "Loaded configuration for the '${this.environment}' environment: ${this.api}"
    }

    def loadSettings(String environment) {
        return new ConfigSlurper(environment).parse(this.getClass().getResource('/Settings.groovy').text)
    }

    def loadSettings(String environment, URL customSettings) {
        return new ConfigSlurper(environment).parse(customSettings)
    }

    def loadSettings(URL customSettings) {
        return new ConfigSlurper().parse(customSettings)
    }

    def private setEnvironment(String environment='sandbox') {
        this.environment = environment ?: System.getenv('SIERRA_API_ENV')
    }

    def private setApiKey() {
        this.api.key = this.api.key ?: System.getenv('SIERRA_API_KEY')
    }
}
