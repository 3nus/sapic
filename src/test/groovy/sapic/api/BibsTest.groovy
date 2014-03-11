package sapic.api
import groovy.json.JsonSlurper
import gwebmock.MockAsyncHTTPBuilder
import sapic.entities.Bib

import static org.hamcrest.CoreMatchers.instanceOf
import static org.junit.Assert.assertThat

class GetBibsTest extends GroovyTestCase {

    def bibs
    def mock

    void setUp() {
        super.setUp()
        bibs = new sapic.api.Bibs()
        mock = new MockAsyncHTTPBuilder()
    }

    void tearDown() {

    }

    void test_get_bibs_returns_Bibs() {
        def testPath = '/iii/sierra-api/v0.5/bibs'
        def response = new JsonSlurper().parseText(this.getClass().getResource( '/get_bibs_response.json' ).text)
        mock.stubApiGet(path: testPath, returns: response)
        mock.mockHTTP.use {
            def bibs = bibs.bibs(limit: 5)
            assertThat(bibs, instanceOf(sapic.entities.Bibs.class))
        }
    }

    void test_get_bibs_Bibs_contains_Bib_instances() {
        def testPath = '/iii/sierra-api/v0.5/bibs'
        def response = new JsonSlurper().parseText(this.getClass().getResource( '/get_bibs_response.json' ).text)
        mock.stubApiGet(path: testPath, returns: response)
        mock.mockHTTP.use {
            def bibs = bibs.bibs(limit: 5)
            bibs.each {
                assertThat(it, instanceOf(Bib.class))
            }
        }
    }

    void test_get_bibs_response() {
        def testPath = '/iii/sierra-api/v0.5/bibs'
        def response = new JsonSlurper().parseText(this.getClass().getResource( '/get_bibs_response.json' ).text)
        mock.stubApiGet(path: testPath, returns: response)
        mock.mockHTTP.use {
            def bibs = bibs.bibs(limit: 5)
            assertEquals(5, bibs.size())
        }
    }

    void test_get_bibs_path() {
        def testPath = '/iii/sierra-api/v0.5/bibs'
        def response = new JsonSlurper().parseText(this.getClass().getResource( '/get_bibs_response.json' ).text)
        mock.stubApiGet(path: testPath, returns: response)
        mock.mockHTTP.use {
            def bibs = bibs.bibs(limit: 5)
        }
        mock.assertPathWasRequested()
    }
}
