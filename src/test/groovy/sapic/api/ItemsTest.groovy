package sapic.api
import groovy.json.JsonSlurper
import gwebmock.MockAsyncHTTPBuilder
import sapic.resources.Item

import static org.hamcrest.CoreMatchers.instanceOf
import static org.junit.Assert.assertThat

@Mixin(MockAsyncHTTPBuilder)
class ItemsTest extends GroovyTestCase {


    Items items
    //def mock

    void setUp() {
        super.setUp()
        items = new sapic.api.Items()
    }

    void tearDown() {

    }

    void test_get_items_returns_Items() {
        def testPath = '/iii/sierra-api/v0.5/items'
        def response = new JsonSlurper().parseText(this.getClass().getResource( '/get_items_response.json' ).text)
        stubApiGet(path: testPath, returns: response)
        mockHTTP.use {
            def items = items.get_items(limit: 5)
            assertThat(items, instanceOf(sapic.resources.Items.class))
        }
    }

    void test_get_bibs_Bibs_contains_Bib_instances() {
        def testPath = '/iii/sierra-api/v0.5/items'
        def response = new JsonSlurper().parseText(this.getClass().getResource( '/get_items_response.json' ).text)
        stubApiGet(path: testPath, returns: response)
        mockHTTP.use {
            def items = items.get_items(limit: 5)
            items.each {
                assertThat(it, instanceOf(Item.class))
            }
        }
    }

    void test_get_items_response() {
        def testPath = '/iii/sierra-api/v0.5/items'
        def response = new JsonSlurper().parseText(this.getClass().getResource( '/get_items_response.json' ).text)
        stubApiGet(path: testPath, returns: response)
        mockHTTP.use {
            def items = items.get_items(limit: 5)
            assert items.size() == 5
        }
    }

    void test_get_items_path() {
        def testPath = '/iii/sierra-api/v0.5/items'
        def response = new JsonSlurper().parseText(this.getClass().getResource( '/get_items_response.json' ).text)
        stubApiGet(path: testPath, returns: response)
        mockHTTP.use {
            def items = items.get_items(limit: 5)
        }
        assertPathWasRequested(testPath)
    }
}
