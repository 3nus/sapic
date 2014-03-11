package sapic.entities

// ---------------------------
// a collection of entities, extending ArrayList
//
class Collection extends ArrayList<Bib> {

    def total
    def start
    def entries
    def response
    def klass

    // ---------------------------
    // magic voodoo to load an instance of a specific class
    //      accepts: Map [path: path, query:query]
    //      returns: class instance
    def loadKlass(object) {
        return Class.forName(this.klass).newInstance(object)
    }


    // ---------------------------
    // maps a HashMap (serialized from json) into an ArrayList with properties
    //      accepts: HashMap [path: path, query:query]
    //      returns: this
    def fromResponse(HashMap payload) {

        // store the raw (serialized) payload
        response = payload

        // store the top level keys
        total = payload.total
        start = payload.start
        entries = payload.entries

        // append each member of entries to this
        payload.entries.each {
            this.add(loadKlass(it))
        }

        return this
    }
}