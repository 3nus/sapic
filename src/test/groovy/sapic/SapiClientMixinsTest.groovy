package sapic

import groovy.json.JsonSlurper
import gwebmock.MockAsyncHTTPBuilder

import static org.hamcrest.CoreMatchers.instanceOf
import static org.junit.Assert.assertThat

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
            def bibs = client.get_bibs(limit: 5)
            assertThat(bibs, instanceOf(sapic.resources.Bibs.class))

        }
    }

    void test_get_items_mixin() {
        def testPath = '/iii/sierra-api/v0.5/items'
        def response = new JsonSlurper().parseText(this.getClass().getResource( '/get_items_response.json' ).text)
        stubApiGet(path: testPath, returns: response)
        mockHTTP.use {
            def items = client.get_items(limit: 5)
            assertThat(items, instanceOf(sapic.resources.Items.class))
        }
    }
}
