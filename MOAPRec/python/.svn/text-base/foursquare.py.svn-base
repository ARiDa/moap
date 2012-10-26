# https://api.foursquare.com/v2/venues/search?near=milano&oauth_token=RF5M1SJYRKMAUQ2IROFHI2BUPCDVCK2GSXOTAPRVDZVIFGZK&v=20120510
import urllib
import urllib2
import json
# path = 'https://api.foursquare.com/v2/venues'
# args = None
# print urllib2.urlopen(path).read()
url = 'https://api.foursquare.com/v2/venues/search'
data = {}
# data['ll'] = '44.3,37.2'
data['near'] = 'milan,italy'
data['oauth_token'] = 'RF5M1SJYRKMAUQ2IROFHI2BUPCDVCK2GSXOTAPRVDZVIFGZK'
data['v'] = 20120510
url_values = urllib.urlencode(data)
full_url = url+ '?' +url_values
print full_url
data = urllib2.urlopen(full_url)
http_response =  data.read()
data_json = json.loads(http_response)

# print data_json['meta']
# print data_json['notifications']
print 2

response = data_json['response']

id = ''
name = ''
lat = ''
lon = ''
cat = ''
checkins = ''

venues = response['venues']
fil = open('/Users/igobrilhante/venues.csv', 'w')
fil.write('id,name,lat,lon,checkins,cat\n')
print 3
for venue in venues:
	id = str(venue['id'])
	name = unicode(venue['name'])
	lat = str(venue['location']['lat'])
	lon = str(venue['location']['lat'])
	checkins = str(venue['stats']['checkinsCount'])
	cat = ''
	for category in venue['categories']:
		if cat != '':
			cat += '_'+unicode(category['name'])
		else:
			cat = unicode(category['name'])
	name = name.encode('utf-8')
	cat = cat.encode('utf-8')
	fil.write(id+','+name+','+lat+','+lon+','+checkins+','+cat+'\n')
fil.close