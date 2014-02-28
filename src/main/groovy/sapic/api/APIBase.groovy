package sapic.api

class APIBase {

    // ---------------------------
    // builds HashMap of query parameters
    //      accepts: Map [limit: Integer limit, offset: Integer offset]
    //      returns: HashMap queryParams
    // Todo: implement search and filter parameters in APIBase
    def buildQueryParameters(Map args=[:]) {
        HashMap queryParams = []
        args.limit ? queryParams << ['limit': args.limit] : null
        args.offset ? queryParams << ['offset': args.offset] : null
        return queryParams
    }
}
