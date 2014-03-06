package sapic.api

import sapic.SapiClient

// ------------
// Implements: /iii/sierra-api/v0.5/bibs
// Docs: http://sandbox.iii.com/docs/Default.htm#retrieveBib.htm%3FTocPath%3DSierra%20API%20Reference%7COperations%7C_____1
// Sample call:
//      $ curl "http://sandbox.iii.com:80/iii/sierra-api/v0.5/bibs?apiKey=<value>"
//      {"entries":[{"id":1000001,"updatedDate":"2009-07-06T15:30:13Z","createdDate":"2003-05-08T15:55:00Z","deleted":false,"suppressed":false,"title":"Hey, what's wrong with this one?","author":"Wojciechowska, Maia, 1927-","materialType":{"code":"a","value":"Book"},"bibLevel":{"code":"m","value":"MONOGRAPH"},"publishYear":1969,"catalogDate":"1990-10-10","country":"New York (State)"},{"id":1000002,"updatedDate":"2008-07-17T04:05:25Z","createdDate":"2003-05-08T15:55:13Z","deleted":false,"suppressed":false,"title":"The organization of the boot and shoe industry in Massachusetts before 1875.","author":"Hazard, Blanche Evans.","materialType":{"code":"a","value":"Book"},"bibLevel":{"code":"m","value":"MONOGRAPH"},"publishYear":1969,"catalogDate":"1990-10-10","country":"New York (State)"},{"id":1000003,"updatedDate":"2004-10-04T19:17:45Z","createdDate":"2003-05-08T15:55:13Z","deleted":false,"suppressed":false,"title":"Monotheism and Moses,","author":"Christen, Robert J.","materialType":{"code":"a","value":"Book"},"bibLevel":{"code":"m","value":"MONOGRAPH"},"publishYear":1969,"catalogDate":"1990-10-10","country":"Massachusetts"},{"id":1000004,"updatedDate":"2006-07-17T16:34:10Z","createdDate":"2003-05-08T15:55:13Z","deleted":false,"suppressed":false,"title":"Socialism in Cuba,","author":"Huberman, Leo, 1903-1968.","materialType":{"code":"a","value":"Book"},"bibLevel":{"code":"m","value":"MONOGRAPH"},"publishYear":1969,"catalogDate":"1990-10-10","country":"New York (State)"},{"id":1000005,"updatedDate":"2004-10-04T19:17:45Z","createdDate":"2003-05-08T15:55:13Z","deleted":false,"suppressed":false,"title":"The Soviet economy; structure, principles, problems.","author":"Spulber, Nicolas.","materialType":{"code":"a","value":"Book"},"bibLevel":{"code":"m","value":"MONOGRAPH"},"publishYear":1969,"catalogDate":"1990-10-10","country":"New York (State)"}]}

class Bibs extends APIBase {

    // ---------------------------
    // requests /iii/sierra-api/v0.5/bibs and returns a sapic.resource.Bibs object
    //      accepts: Map [limit: Integer limit, offset: Integer offset]
    //      returns: sapic.resource.Bibs object
    // Todo: implement search and filter parameters in APIBase
    def get_bibs(Map args=[:]) {
        def path = 'bibs'
        return new sapic.resources.Bibs().fromResponse(SapiClient.instance.get(path: path, query: buildQueryParameters(args)))
    }

    /*
    def get_bib(id) {}

    def get_marc(id) {}
    */

}
