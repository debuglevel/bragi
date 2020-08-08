import json

def print_template(name):
    output = f"{name}"

    print(output)

locations_file = open("locations.json")
j = json.load(locations_file)

ldict = {}

for region in j["regions"]:
    name = region["location"]
    ldict[name] = name

    for subregion in region["subLocation"]:
        if subregion != "":
          ldict[subregion] = subregion

for location in ldict:
    print_template(ldict[location])