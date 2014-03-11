package sapic.config

/**
 * Created by aterrell on 3/10/14.
 */
class ConfigurationDefaultsTest extends GroovyTestCase {

    def config

    void setUp() {
        super.setUp()
        config = new Configuration()
    }

    void tearDown() {

    }

    void testConfigurationApiHost() {
        assertEquals('http://sandbox.iii.com', config.api.host)
    }

    void testDefaultConfigurationApiRootPath() {
        assertEquals('/iii/sierra-api', config.api.rootPath)
    }

    void testConfigurationApiVersion() {
        assertEquals('v0.5', config.api.version)
    }

    void testConfigurationApiKey() {
        assertEquals(System.getenv('SIERRA_API_KEY'), config.api.key)
    }

}

class ConfigurationWithEnvTest extends GroovyTestCase {

    def config

    void setUp() {
        super.setUp()
        config = new Configuration()
        config.reload('sandbox')
    }

    void tearDown() {
        config.reload()
    }

    void testConfigurationApiHostViaEnvironment() {
        assertEquals('http://sandbox.iii.com', config.api.host)
    }

    void testConfigurationApiKey() {
        assertEquals(System.getenv('SIERRA_API_KEY'), config.api.key)
    }

}

class ConfigurationWithEnvAndCustomSettingsTest extends GroovyTestCase {

    def config

    void setUp() {
        super.setUp()
        def customSettings = this.getClass().getResource('/CustomSettings.groovy')
        config = new Configuration()
        config.reload('superSierraInstall', customSettings)
    }

    void tearDown() {

    }

    void testConfigurationApiHostViaEnvironment() {
        assertEquals('http://super-sierra-install.com', config.api.host)
    }

    void testConfigurationApiKey() {
        assertEquals('sekrit', config.api.key)
    }

}

class ConfigurationWithCustomSettingsTest extends GroovyTestCase {

    def config

    void setUp() {
        super.setUp()
        def customSettings = this.getClass().getResource('/CustomSettingsOverwrite.groovy')
        config = new Configuration()
        config.reload(customSettings)
    }

    void tearDown() {

    }

    void testConfigurationApiHostViaEnvironment() {
        assertEquals('http://super-sierra-install.com', config.api.host)
    }

    void testConfigurationApiKey() {
        assertEquals('sekrit', config.api.key)
    }

}
