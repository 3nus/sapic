package sapic

import groovy.json.JsonSlurper
import gwebmock.MockAsyncHTTPBuilder

import static org.hamcrest.CoreMatchers.instanceOf
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertThat

class SapiClientInstanceTest extends GroovyTestCase {

    void testNewObject() {
        def client = SapiClient.instance
        assertEquals(SapiClient.class, client.class)
    }

}

@Mixin(MockAsyncHTTPBuilder)
class SapiClientDefaultConfigTest extends GroovyTestCase {

    def client

    void setUp() {
        super.setUp()
        client = SapiClient.instance
        client.reload()
    }

    void tearDown() {

    }

    void testDefaultConfigHost() {
        assertEquals('http://sandbox.iii.com', client.config.host)
    }

    void testDefaultConfigRootPath() {
        assertEquals('/iii/sierra-api', client.config.rootPath)
    }

    void testDefaultConfigVersion() {
        assertEquals('v0.5', client.config.version)
    }

    void testDefaultConfigKey() {
        assertEquals(System.getenv('SIERRA_API_KEY'), client.config.key)
    }

    void testGet() {
        def testPath = 'foo'
        stubApiGet(path: testPath, returns: new JsonSlurper().parseText('{"hi":"there"}'))
        mockHTTP.use {
            def result = client.get(path: testPath, query: [:])
            assertPathWasRequested(testPath)
        }
    }

}

class SapiClientEnvConfigTest extends GroovyTestCase {

    def client

    void setUp() {
        super.setUp()
        client = SapiClient.instance
        client.loadConfig('local')
    }

    void tearDown() { }

    void testDefaultConfigHost() {
        assertEquals('http://localhost', client.config.host)
    }

    void testDefaultConfigRootPath() {
        assertEquals('/iii/sierra-api', client.config.rootPath)
    }

    void testDefaultConfigVersion() {
        assertEquals('v1', client.config.version)
    }

    void testDefaultConfigKey() {
        assertEquals(System.getenv('SIERRA_API_KEY'), client.config.key)
    }

}

class SapiClientCustomConfigTest extends GroovyTestCase {

    def client

    void setUp() {
        super.setUp()
        client = SapiClient.instance
        client.loadConfig(this.getClass().getResource('/CustomSettingsOverwrite.groovy'))
    }

    void tearDown() { }

    void testDefaultConfigHost() {
        assertEquals('http://super-sierra-install.com', client.config.host)
    }

    void testDefaultConfigRootPath() {
        assertEquals('/iii/sierra-api', client.config.rootPath)
    }

    void testDefaultConfigVersion() {
        assertEquals('v42', client.config.version)
    }

    void testDefaultConfigKey() {
        assertEquals('sekrit', client.config.key)
    }

}

class SapiClientEnvCustomConfigTest extends GroovyTestCase {

    def client

    void setUp() {
        super.setUp()
        client = SapiClient.instance
        client.loadConfig('superSierraInstall', this.getClass().getResource('/CustomSettings.groovy'))
    }

    void tearDown() { }

    void testDefaultConfigHost() {
        assertEquals('http://super-sierra-install.com', client.config.host)
    }

    void testDefaultConfigRootPath() {
        assertEquals('/iii/sierra-api', client.config.rootPath)
    }

    void testDefaultConfigVersion() {
        assertEquals('v42', client.config.version)
    }

    void testDefaultConfigKey() {
        assertEquals('sekrit', client.config.key)
    }

}

@Mixin(MockAsyncHTTPBuilder)
class SapiClientMixinsTest extends GroovyTestCase {

    def client

    void setUp() {
        super.setUp()
        client = SapiClient.instance
        client.reload()
    }

    void tearDown() { }

    void test_get_bibs_mixin() {
        def testPath = '/iii/sierra-api/v0.5/bibs'
        def response = new JsonSlurper().parseText(this.getClass().getResource( '/get_bibs_response.json' ).text)
        stubApiGet(path: testPath, returns: response)
        mockHTTP.use {
            def bibs = client.bibs(limit: 5)
            assertThat(bibs, instanceOf(sapic.entities.Bibs.class))

        }
    }

    void test_get_items_mixin() {
        def testPath = '/iii/sierra-api/v0.5/items'
        def response = new JsonSlurper().parseText(this.getClass().getResource( '/get_items_response.json' ).text)
        stubApiGet(path: testPath, returns: response)
        mockHTTP.use {
            def items = client.items(limit: 5)
            assertThat(items, instanceOf(sapic.entities.Items.class))
        }
    }
}
