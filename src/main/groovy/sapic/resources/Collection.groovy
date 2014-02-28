package sapic.resources

class Collection extends ArrayList<Bib> {

    def total
    def start
    def entries
    def response
    def klass

    def loadKlass(object) {
        return Class.forName(this.klass).newInstance(object)
    }

    def fromResponse(HashMap payload) {
        response = payload
        total = payload.total
        start = payload.start
        entries = payload.entries
        payload.entries.each {
            this.add(loadKlass(it))
        }

        return this
    }
}