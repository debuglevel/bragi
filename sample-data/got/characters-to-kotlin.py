import json

def print_template(character):
    name = character["characterName"]

    try:
        alias = character["nickname"]
    except KeyError:
        alias = ""

    variableName = "`"+name+"`"

    output = f"{name};{alias}"

    print(output)


characters_file = open("characters.json")
c = json.load(characters_file)

cdict = {}

for character in c["characters"]:
    name = character["characterName"]
    cdict[name] = character

for character in cdict:
    print_template(cdict[character])

