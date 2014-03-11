package sapic.config

class ConfigurationExceptionTest extends GroovyTestCase {

    void setUp() {
        super.setUp()

    }

    void tearDown() {

    }

    void testRequestedPathException() {
        def exc = new ConfigurationException("foo")
        assertEquals("foo", exc.message)
    }

}
