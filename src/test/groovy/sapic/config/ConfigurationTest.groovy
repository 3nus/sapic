package sapic.config

/**
 * Created by aterrell on 3/10/14.
 */
class DefaultConfigurationTest extends GroovyTestCase {

    def config

    void setUp() {
        super.setUp()
        config = new DefaultConfiguration()
    }

    void tearDown() {

    }

    void testConfigurationApiHost() {
        assertEquals('http://sandbox.iii.com', config.host)
    }

    void testDefaultConfigurationApiRootPath() {
        assertEquals('/iii/sierra-api', config.rootPath)
    }

    void testConfigurationApiVersion() {
        assertEquals('v0.5', config.version)
    }

    void testConfigurationApiKey() {
        assertEquals(System.getenv('SIERRA_API_KEY'), config.key)
    }

}

class DefaultConfigurationConfigurationEnvTest extends GroovyTestCase {

    def config

    void setUp() {
        super.setUp()
        config = new DefaultConfiguration()
        config.loadConfig('sandbox')
    }

    void tearDown() {
    }

    void testConfigurationApiHostViaEnvironment() {
        assertEquals('http://sandbox.iii.com', config.host)
    }

    void testConfigurationApiKey() {
        assertEquals(System.getenv('SIERRA_API_KEY'), config.key)
    }

}

class CustomConfigurationWithEnvTest extends GroovyTestCase {

    def config

    void setUp() {
        super.setUp()
        def customSettings = this.getClass().getResource('/CustomSettings.groovy')
        config = new CustomConfiguration()
        config.loadConfig('superSierraInstall', customSettings)
    }

    void tearDown() {

    }

    void testConfigurationApiHostViaEnvironment() {
        assertEquals('http://super-sierra-install.com', config.host)
    }

    void testConfigurationApiKey() {
        assertEquals('sekrit', config.key)
    }

}

class CustomConfigurationTest extends GroovyTestCase {

    def config

    void setUp() {
        super.setUp()
        def customSettings = this.getClass().getResource('/CustomSettingsOverwrite.groovy')
        config = new CustomConfiguration()
        config.loadConfig(customSettings)
    }

    void tearDown() {

    }

    void testConfigurationApiHostViaEnvironment() {
        assertEquals('http://super-sierra-install.com', config.host)
    }

    void testConfigurationApiKey() {
        assertEquals('sekrit', config.key)
    }

}
