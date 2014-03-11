package sapic.config

import groovy.util.logging.Log

class ConfigurationException extends Exception {
    public ConfigurationException(String message) {
        super(message);
    }
}

@Log(category='sapic.config.Configuration')
class DefaultConfiguration {

    String environment
    String scheme
    String host
    Integer port
    String rootPath
    String version
    String key

    // ---------------------------
    // Instantiate a Configuration object with the default config
    def DefaultConfiguration() {
        loadConfig()
    }

    // ---------------------------
    // load the default config
    def loadConfig() {
        applyEnvironment()
        def settings = loadSettings(this.environment)
        applyConfig(settings)
    }

    def loadConfig(String environment) {
        applyEnvironment(environment)
        def settings = loadSettings(this.environment)
        applyConfig(settings)
    }

    def applyConfig(ConfigObject settings) {
        settings.environment.api.each { k, v ->
            this."$k" = v
        }
        applyKey()
        log.info "Loaded configuration for the '${this.environment}' environment: ${settings.environment.api}"
    }

    // load default settings file
    def loadSettings() {
        return new ConfigSlurper().parse(this.getClass().getResource('/Settings.groovy').text)
    }

    // load settings for the specified environment
    def loadSettings(String environment) {
        return new ConfigSlurper(environment).parse(this.getClass().getResource('/Settings.groovy').text)
    }

    def applyEnvironment(String environment='sandbox') {
        this.environment = environment ?: System.getenv('SIERRA_API_ENV')
    }

    def applyKey(String key=null) {
        this.key = this.key ?: key ?: System.getenv('SIERRA_API_KEY')
        //this.key = key ?: System.getenv('SIERRA_API_KEY')
        this.key ?: { throw new ConfigurationException("API key not found, is the SIERRA_API_KEY env var set?") }
    }

}

@Log(category='sapic.config.Configuration')
class CustomConfiguration extends DefaultConfiguration {

    def loadConfig(URL customConfig) {
        applyEnvironment()
        def settings = loadSettings(customConfig)
        def customSettings = loadSettings(customConfig)
        settings = settings.merge(customSettings)
        applyConfig(settings)
    }

    def loadConfig(String environment, URL customConfig) {
        applyEnvironment(environment)
        def settings = loadSettings(this.environment)
        def customSettings = loadSettings(this.environment, customConfig)
        settings = settings.merge(customSettings)
        applyConfig(settings)
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

}