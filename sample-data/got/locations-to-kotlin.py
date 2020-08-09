import json
import base64
import requests
import random

def print_template(name):
    if any(exclusion in name for exclusion in exclusions):
        return
    
    if random.randint(1, 3) == 1: # 1 of 3 should have a picture
      base64image = try_get_an_image(name)
    else: 
      base64image = ""

    output = f"{name};{base64image}"

    print(output)

def try_get_an_image(name):
    # placeholder until maybe replaced by a real search
    x = random.randint(200, 500)
    y = random.randint(100, 300)
    url = f"https://picsum.photos/{x}/{y}"
    return base64.b64encode(requests.get(url).content).decode('ascii')
   
exclusions = ["To ", "Outside ", "Away ", " to ", "Near ", " of "]

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