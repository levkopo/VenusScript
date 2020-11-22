using std, fpm, json

setHeader("Content-Type: application/json")

data = null
if(getQuery("r")==null){
    data = {
        "error":1
    }
}else{
    data = {
        "response":getQuery("r")
    }
}

println(jsonEncode(data))