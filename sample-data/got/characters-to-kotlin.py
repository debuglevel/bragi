import json
import base64
import requests

def print_template(character):
    name = character["characterName"]

    try:
        alias = character["nickname"]
    except KeyError:
        alias = ""

    try:
        characterImageThumb = character["characterImageThumb"]
        base64image = get_as_base64(characterImageThumb)
    except KeyError:
        base64image = ""
    except:
        base64image = ""

    variableName = "`"+name+"`"

    output = f"{name};{alias};{base64image}"

    print(output)

def get_as_base64(url):
    return base64.b64encode(requests.get(url).content).decode('ascii')

characters_file = open("characters.json")
c = json.load(characters_file)

cdict = {}

for character in c["characters"]:
    name = character["characterName"]
    cdict[name] = character

for character in cdict:
    print_template(cdict[character])

