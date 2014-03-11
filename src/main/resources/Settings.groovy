environment {
    api {
        scheme = 'http'
        host = null
        port = 80
        rootPath = '/iii/sierra-api'
        version = null
        key = null
    }
}

environments {
    sandbox {
        environment {
            api{
                host = 'http://sandbox.iii.com'
                rootPath = '/iii/sierra-api'
                version = 'v0.5'
            }
        }
    }
    local {
        environment {
            api{
                host = 'http://localhost'
                version = 'v1'
            }
        }
    }
}
