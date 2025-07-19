from urllib.request import urlopen
from bs4 import BeautifulSoup
import re
import json
from json import JSONEncoder
import requests

url = "https://global.bandai-hobby.net/en-us/item_all/?p="


all_links_gunpla = []

url_limit = "https://global.bandai-hobby.net/en-us/item/01_5977/"

def get_links_bandai(url):
	page = urlopen(url)
	html_bytes = page.read()
	html = html_bytes.decode("utf-8")

	

	headers = {"User-Agent":"Mozilla/5.0 (X11; Linux x86_64; rv:134.0) Gecko/20100101 Firefox/134.0"}
	response = requests.get(url, headers=headers)
	with open('out.html', 'w') as f:
		print(response.text, file=f)
	soup = BeautifulSoup(response.text, 'lxml')
	with open('minus.html', 'w') as f:
		print(soup, file=f)
	# soup = BeautifulSoup(html, "html.parser")
	links = soup.find_all('a', class_="c-card p-card -landscape")
	links_gunpla = []
	for link in links:
		links_gunpla.append(link.get('href'))

	all_links_gunpla.extend(links_gunpla)

def write_to_file():
	for i in range (100, 110):
		url_page = url + str(i)
		print(url_page)
		get_links_bandai(url_page)
		if url_limit in all_links_gunpla:
			break

	with open("all_links_gunpla_new.txt", "w") as f:
		for link in all_links_gunpla:
			f.write(link + '\n')

if __name__ == '__main__':
	write_to_file()
