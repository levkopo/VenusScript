using json
using std

array = jsonDecode('[{"value":10},{"value":20}]')
println("Size: " + size(array))

print("Values:")
for i in array {
    print(i["value"])
}

println()
println("From json: "+jsonEncode(array))
