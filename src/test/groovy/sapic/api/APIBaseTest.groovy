package sapic.api

class APIBaseTest extends GroovyTestCase {

    def apiBase = new APIBase()

    void setUp() {
        super.setUp()
    }

    void tearDown() {

    }

    void testBuildQueryParametersWithEmptyArgs() {
        HashMap expected = [:]
        def params = apiBase.buildQueryParameters()
        assertEquals(params, expected)
    }

    void testBuildQueryParametersWithLimit() {
        HashMap expected = [limit: 5]
        def params = apiBase.buildQueryParameters(limit: 5)
        assertEquals(params, expected)
    }

    void testBuildQueryParametersWithLimitAndOffset() {
        HashMap expected = [limit: 5, offset: 10]
        def params = apiBase.buildQueryParameters(limit: 5, offset: 10)
        assertEquals(params, expected)
    }

    void testBuildQueryParametersWithUnmappedParams() {
        HashMap expected = [:]
        def params = apiBase.buildQueryParameters(foo: 5, bar: 10)
        assertEquals(params, expected)
    }
}
