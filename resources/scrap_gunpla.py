from urllib.request import urlopen
from bs4 import BeautifulSoup
import re
import json
from json import JSONEncoder
import requests

# Penser à créer une fonction générant un nouvel agent Google à chaque fois
# pour éviter d'être blacklisté


class Kit:
	def __init__(self, name, serie, echelle, fabriquant, date):
	 	self.name = name
	 	self.serie = serie
	 	self.echelle = echelle
	 	self.fabriquant = fabriquant
	 	self.date = date

	def __str__(self):
		return f"Kit => {self.name}\n{self.serie}\n{self.echelle}\n{self.fabriquant}\n{self.date}\n"

class KitEncoder(JSONEncoder):
	def default(self, obj):
		return obj.__dict__

# TODO : fonction qui sur chaque lien recup les infos des gunplas
# surtout la date de sortie, car c'est la seule donnée non présente
# Faire un dictionnaire
# {
# "Nom" : Nom du gunpla
# "Serie" : Nom de la série
# "echelle" : RG, HG etc
# "fabriquant" : bandai par défaut ici
# "Date de sortie " : date
# }
def get_data_for_each_page(url):

	headers = {"User-Agent":"Mozilla/5.0 (X11; Linux x86_64; rv:134.0) Gecko/20100101 Firefox/134.0"}
	response = requests.get(url.rstrip("\n"), headers=headers)
	soup = BeautifulSoup(response.text, 'lxml')

	# print(url)

 	# Name
	title_name = soup.find('h1', class_="p-heading__h1-product")
	# print("title_name", title_name)
	if title_name:
		name = title_name.get_text()
	else:
		return None

	flat_cards = soup.find_all("a", class_="c-card__flat p-card__flat")

	acceptable_series = {
	"age" : "Mobile Suit Gundam Age",
	"builddivers" : "Gundam Build Divers",
	"buildfighters" : "Gundam Build Fighters",
	"crossbone" : "Mobile Suit Crossbone Gundam",
	"digimon" : "DIGIMON Series",
	"doraemon" : "DORAEMON",
	"dragonball" : "DRAGON BALL",
	"g-doan" : "Mobile Suit Gundam : Cucuruz Doan's Island",
	"g-reco" : "Gundam Reconguista In G",
	"g-unit" : "Mobile Suit Gundam Wing Dual Story G-Unit",
	"g-witch" : "Mobile Suit Gundam The Witch From Mercury",
	"geass" : "CODE GEASS",
	"gundambuildmetaverse" : "GUNDAM BUILD METAVERSE",
	"gundamf91" : "Mobile Suit Gundam F91",
	"gunplakun" : "Gunpla-Kun",
	"lgaim" : "Heavy Tank Elgaim",
	"macross" : "Macross",
	"missinglink" : "Mobile Suit Gundam Side Story: Missing Link",
	"narrative" : "Mobile Suit Gundam NT",
	"onepiece" : "ONE PIECE",
	"origin" : "Mobile Suit Gundam The Origin",
	"plannosaurus" : "Plannosaurus",
	"pokemon" : "Pokemon",
	"requiem" : "Mobile Suit Gundam : Requiem for Vengeance",
	"sdgundamworld_heroes" : "SD GUNDAM WORLD HEROES",
	"sentinel" : "Gundam Sentinel",
	"superrobot_og" : "Super Robot Wars OG",
	"tekketsu" : "Mobile Suit Gundam Iron-Blooded Orphans",
	"thunderbolt" : "Mobile Suit Gundam Thunderbolt",
	"toystory4" : "Toy Story 4",
	"ultraman" : "Ultraman",
	"unkosuldays" : "Unkosul Days",
	"v-gundam" : "Mobile Suit Victory Gundam",
	"votoms" : "Armored Trooper Votoms",
	"wataru" : "WATARU",
	"yamato" : "STAR BLAZERS SPACE BATTLESHIP YAMATO Series",
	"yugioh" : "YU-GI-OH",
	"ggundam": "G Gundam",
	"gundam-cca": "Char's Counterattack",
	"seed" : "Gundam Seed",
	"seed-freedom" : "Gundam Seed Freedom",
	"rider" : "Kamen Rider",
	"starwars" : "Star Wars",
	"godzilla" : "Godzilla",
	"gundam" : "Mobile Suit Gundam",
	"z-gundam" : "Mobile Suit Zeta Gundam",
	"unicorn" : "Mobile Suit Gundam Unicorn",
	"endlesswaltz" : "Mobile Suit Gundam Wing",
	"g-0080" : "Mobile Suit Gundam 0080 War In The Pocket",
	"g-0083" : "Mobile Suit Gundam 0083 Stardust Memory",
	"x" : "After War Gundam X",
	"turn-a" : "Turn A Gundam",
	"gundam-zz" : "Mobile Suit Gundam ZZ",
	"gquuuuuux" : "Mobile Suit Gundam GQuuuuuux",
	"sdgundamseries" : "SD GUNDAM SERIES",
	}

	acceptable_scales = {
	"rg_c" : "RG", 
	"hg": "HG", 
	"pg": "PG",
	"mgsd": "MGSD",
	"mgka" : "MG",
	"mgex" : "MG",
	"fullmechanics" : "FULL MECHANICS",
	"bb": "BB SENSHI",
	"entry_grade_g" : "ENTRY GRADE"
	}

	THIRTYMM_scales = {
	"30mm": "30 MINUTE MISSIONS",
	"30ms": "30 MINUTE SISTERS", 
	"30mf": "30 MINUTE FANTASY",
	"30mp": "30 MINUTE PREFERENCE"
	}

	series = "No Series"
	echelle = "No Scale"
	for flat_card in flat_cards:
		link = flat_card.get('href')
		value = link.split("/")[-2]
		# Serie
		if "series" in link:
			if str(value) in acceptable_series:
				series = acceptable_series[str(value)]
			
		# Echelle
		if "brand" in link:
			if str(value) in acceptable_scales:
				echelle = acceptable_scales[str(value)]
			elif str(value) in THIRTYMM_scales:
				series = THIRTYMM_scales[str(value)]
				echelle = "HG"			

	if series == "No Series":
		series = "Gunpla"

	if echelle == "No Scale":
		if "HG" in name or "1/144" in name:
			echelle = "HG"
		elif "RG" in name:
			echelle = "RG"
		elif "MG" in name or "1/100" in name:
			echelle = "MG"

	# Echelle

	# Date de sortie
	# chopper tous les dt dl, faire un dictionnaire puis chopper la valeur nommé Launch Date
	products_detail = soup.select(".pg-products__label")
	# for item in products_detail:
	# 	print(item)

	date = "1980.Jul.01"

	for title in products_detail:
		if "Launch date" in title.get_text():
			date = title.find_next('dd').get_text()

	if(len(date.split(".")) == 2):
		date = date + '.01' # On ajoute le jour s'il n'est pas indiqué

	return Kit(name, series, echelle, "Bandai", date)

# TODO :
if __name__ == '__main__':
	kits = []

	## Ecriture du fichier JSON

	# print("MG" in "MG 1/100 GUNDAM RX-79G")

	with open("all_links_gunpla.txt", "r") as f:
		for url in f:
			kit = get_data_for_each_page(url)
			if kit is not None:
				kits.append(kit)

	# dump kits into json string
	kits_json = json.dumps(kits, indent=4, cls=KitEncoder)

	with open("base_gunpla_new_new.json", "w") as g:
		g.write(kits_json)