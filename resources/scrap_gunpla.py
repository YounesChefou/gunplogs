from urllib.request import urlopen
from bs4 import BeautifulSoup
import re
import json
from json import JSONEncoder

# url = "https://global.bandai-hobby.net/en-us/item_all/?p="

# Penser à créer une fonction générant un nouvel agent Google à chaque fois
# pour éviter d'être blacklisté

# all_links_gunpla = []

def get_links_bandai(url):
	page = urlopen(url)
	html_bytes = page.read()
	html = html_bytes.decode("utf-8")

	soup = BeautifulSoup(html, "html.parser")
	links = soup.find_all('a', class_="c-card p-card -landscape")
	links_gunpla = []
	for link in links:
		links_gunpla.append(link.get('href'))

	all_links_gunpla.extend(links_gunpla)

def write_to_file():
	for i in range (1, 100):
		url_page = url + str(i)
		get_links_bandai(url_page)

	with open("all_links_gunpla.txt", "w") as f:
		for link in all_links_gunpla:
			f.write(link + '\n')

class Kit:
	def __init__(self, nom, serie, echelle, fabriquant, date):
	 	self.nom = nom
	 	self.serie = serie
	 	self.echelle = echelle
	 	self.fabriquant = fabriquant
	 	self.date = date

	def __str__(self):
		return f"Kit => {self.nom}\n{self.serie}\n{self.echelle}\n{self.fabriquant}\n{self.date}\n"

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
	page = urlopen(url).read().decode("utf-8")
	soup = BeautifulSoup(page, "lxml")

 	# Name
	name = soup.find('h1', class_="p-heading__h1-product").get_text()

 	# Echelles possibles


	flat_cards = soup.find_all("a", class_="c-card__flat p-card__flat")

	acceptable_scales = {"rg_c", "hg", "mgsd", "mgka", "mgex", "fullmechanics", "bb", "mgsd", "entry_grade_g", "30mm", "30ms", "30mf"}
	series = "boom"
	echelle = "boom"
	for flat_card in flat_cards:
		link = flat_card.get('href')
		value = link.split("/")[-2]
		# Serie
		if "series" in link:
			series = str(value)

		# Echelle
		if "brand" in link:
			if str(value) in acceptable_scales:
				echelle = value
			else:
				echelle = "boom" + str(value)
			# rg_c, hg, mgsd, mgka, mgex, fullmechanics, bb, mgsd, entry_grade_g, 30mm, 30ms, 30mf

	# Echelle

	# Date de sortie
	# chopper tous les dt dl, faire un dictionnaire puis chopper la valeur nommé Launch Date
	products_detail = soup.find("dl", class_="pg-products__detail")
	keys, values = [], []
	i = 0;
	for detail in products_detail:
		str_detail = str(detail).strip()
		if str_detail == "":
			continue
		# i += 1
		# print(str(i) + " => |" + str(detail) + "|")
		if "dt" in str_detail:
			keys.append(detail.get_text().strip())
		if "dd" in str_detail:
			text = detail.get_text()
			values.append(text)

	dico = dict(zip(keys,values))
	if "Launch date" in dico.keys():
		date = dico["Launch date"]
	else:
		date = "boom"

	return Kit(name, series, echelle, "Bandai", date)

# TODO :
if __name__ == '__main__':
	kits = []
	with open("all_links_gunpla.txt", "r") as f:
		for url in f:
			kits.append(get_data_for_each_page(url))

	# url = "https://global.bandai-hobby.net/en-us/item/01_5967/"
	# kits.append(get_data_for_each_page(url))
	# url = "https://global.bandai-hobby.net/en-us/item/01_5949/"
	# kits.append(get_data_for_each_page(url))
	# dump kits into json string
	kits_json = json.dumps(kits, indent=4, cls=KitEncoder)

	with open("base_gunpla.json", "w") as g:
		g.write(kits_json)

# 1 => quand vide, ne rien faire

# 2 => <dt class="pg-products__label"><span class="pg-products__labelInner">Price</span></dt> quand dt, recup le text et le mettre dans clé
# 3 => 

# 4 => <dd class="pg-products__labelTxt">4,200                      Yen                    </dd> quand dd 
# 5 => 

# 6 => <dt class="pg-products__label"><span class="pg-products__labelInner">Launch date</span></dt>
# 7 => 

# 8 => <dd class="pg-products__labelTxt">2025.Sep</dd>
# 9 => 

# 10 => <dt class="pg-products__label"><span class="pg-products__labelInner">Age</span></dt>
# 11 => 

# 12 => <dd class="pg-products__labelTxt">over the age of 15</dd>
# 13 => 

	