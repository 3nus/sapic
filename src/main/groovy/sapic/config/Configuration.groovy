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

    String environment
    def api = new APIConfig()

    // ---------------------------
    // Instantiate a Configuration object with the default config
    def private Configuration() {
        loadDefaultConfig()
    }

    // ---------------------------
    // load the default config
    def loadDefaultConfig() {
        setEnvironment()
        def settings = loadSettings(this.environment)
        applyConfig(settings)
    }

    def loadEnvironment(String environment) {
        setEnvironment(environment)
        def settings = loadSettings(this.environment)
        applyConfig(settings)
    }

    def reload(URL customConfig) {
        setEnvironment()
        def settings = loadSettings(customConfig)
        def customSettings = loadSettings(customConfig)
        settings = settings.merge(customSettings)
        applyConfig(settings)
    }

    def reload(String environment, URL customConfig) {
        setEnvironment(environment)
        def settings = loadSettings(this.environment)
        def customSettings = loadSettings(this.environment, customConfig)
        settings = settings.merge(customSettings)
        applyConfig(settings)
    }

    def private applyConfig(ConfigObject settings) {
        this.api = settings.environment.api
        setApiKey()
        log.info "Loaded configuration for the '${this.environment}' environment: ${this.api}"
    }

    // load default settings file
    def loadSettings() {
        return new ConfigSlurper().parse(this.getClass().getResource('/Settings.groovy').text)
    }

    // load settings for the specified environment
    def loadSettings(String environment) {
        return new ConfigSlurper(environment).parse(this.getClass().getResource('/Settings.groovy').text)
    }

    // load settings for the specified environment using a custom settings file
    def loadSettings(String environment, URL customSettings) {
        return new ConfigSlurper(environment).parse(customSettings)
    }

    // load settings exclusively from the specified file, must contain explicit environment config
    // rather than use the ConfigSlurper environments capability
    def loadSettings(URL customSettings) {
        return new ConfigSlurper().parse(customSettings)
    }

    def private setEnvironment(String environment='sandbox') {
        this.environment = environment ?: System.getenv('SIERRA_API_ENV')
    }

    def private setApiKey() {
        this.api.key = this.api.key ?: System.getenv('SIERRA_API_KEY')
        this.api.key ?: { throw new ConfigurationException("API key not found, is the SIERRA_API_KEY env var set?") }
    }
}
