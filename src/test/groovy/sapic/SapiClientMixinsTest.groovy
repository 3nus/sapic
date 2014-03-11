package sapic

import groovy.json.JsonSlurper
import gwebmock.MockAsyncHTTPBuilder

import static org.hamcrest.CoreMatchers.instanceOf
import static org.junit.Assert.assertThat

@Mixin(MockAsyncHTTPBuilder)
class SapiClientDefaultConfigTest extends GroovyTestCase {

    def client

    void setUp() {
        super.setUp()
        client = SapiClient.instance
    }

    void tearDown() {

    }

    void testDefaultConfigHost() {
        assertEquals('http://sandbox.iii.com', client.config.api.host)
    }

    void testDefaultConfigRootPath() {
        assertEquals('/iii/sierra-api', client.config.api.rootPath)
    }

    void testDefaultConfigVersion() {
        assertEquals('v0.5', client.config.api.version)
    }

    void testDefaultConfigKey() {
        assertEquals(System.getenv('SIERRA_API_KEY'), client.config.api.key)
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

@Mixin(MockAsyncHTTPBuilder)
class SapiClientEnvConfigTest extends GroovyTestCase {

    def client

    void setUp() {
        super.setUp()
        client = SapiClient.instance
        client.config.reload('local')
    }

    void tearDown() {

    }

    void testDefaultConfigHost() {
        assertEquals('http://localhost', client.config.api.host)
    }

    void testDefaultConfigRootPath() {
        assertEquals('/iii/sierra-api', client.config.api.rootPath)
    }

    void testDefaultConfigVersion() {
        assertEquals('v1', client.config.api.version)
    }

    void testDefaultConfigKey() {
        assertEquals(System.getenv('SIERRA_API_KEY'), client.config.api.key)
    }

}

@Mixin(MockAsyncHTTPBuilder)
class SapiClientEnvCustomConfigTest extends GroovyTestCase {

    def client

    void setUp() {
        super.setUp()
        client = SapiClient.instance
        client.config.reload('superSierraInstall', this.getClass().getResource('/CustomSettings.groovy'))
    }

    void tearDown() {
        client.config.reload()
    }

    void testDefaultConfigHost() {
        assertEquals('http://super-sierra-install.com', client.config.api.host)
    }

    void testDefaultConfigRootPath() {
        assertEquals('/iii/sierra-api', client.config.api.rootPath)
    }

    void testDefaultConfigVersion() {
        assertEquals('v42', client.config.api.version)
    }

    void testDefaultConfigKey() {
        assertEquals('sekrit', client.config.api.key)
    }

}

@Mixin(MockAsyncHTTPBuilder)
class SapiClientMixinsTest extends GroovyTestCase {

    def client

    void setUp() {
        super.setUp()
        client = SapiClient.instance
    }

    void tearDown() {

    }

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
