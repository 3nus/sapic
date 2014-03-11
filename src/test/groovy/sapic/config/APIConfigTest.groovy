package sapic.config

/**
 * Created by aterrell on 3/10/14.
 */
class APIConfigTest extends GroovyTestCase {

    def config

    void setUp() {
        super.setUp()
        config = new APIConfig()
    }

    void tearDown() {

    }

    void testAPIConfigPoperties() {
        def expected = [rootPath:null, class:'class sapic.config.APIConfig', scheme:null, port:null, version:null, key:null, host:null]
        assertInspect(config.properties, "['rootPath':null, 'class':class sapic.config.APIConfig, 'scheme':null, 'port':null, 'version':null, 'key':null, 'host':null]")
    }

}
