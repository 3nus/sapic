package sapic
import groovy.json.JsonSlurper
import gwebmock.MockAsyncHTTPBuilder

@Mixin(MockAsyncHTTPBuilder)
class SapiClientTest extends GroovyTestCase {

    def client

    void setUp() {
        super.setUp()
        this.client = SapiClient.getInstance()

    }

    void tearDown() {

    }

    void testBibs() {
        def dir = new File(getClass().protectionDomain.codeSource.location.path).parent
        stub_api_get(path: 'bibs', returns: new JsonSlurper().parse(new File(dir + '/sapic/get_bibs_response.json')))
        mockHTTP.use {
            def bibs = this.client.get_bibs(limit: 5)
            assert bibs.size() == 5
        }
    }

    void testItems() {
        def items = this.client.get_items(limit: 5)
        assert items.response
        assertNull items.total
        assertNull items.start
        assert items.size() == 5
    }
}
