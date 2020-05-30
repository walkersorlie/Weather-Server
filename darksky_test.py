from darksky.api import DarkSky, DarkSkyAsync
from darksky.types import languages, units, weather
import json
import requests
import os
from datetime import datetime, timezone



def unix_time_to_local_time(unix_time):
    utc_time = datetime.fromtimestamp(unix_time, timezone.utc)
    return utc_time.astimezone()


if not os.path.isfile('/home/walker/Documents/Projects/weather_server/darksky_test_info.json'):
    # API_KEY = "5d6c6c11b5e855d01cd7331b60f33f3a"
    API_KEY = os.environ['DARKSKY_API_KEY']
    latitude = 44.0583333
    longitude = -121.3141667

    headers = {'User-Agent': 'Mozilla/5.0', 'Accept-Encoding': 'gzip'}
    # ?exclude=currently,minutely,hourly,alerts,flags
    url = f"https://api.darksky.net/forecast/{API_KEY}/{latitude},{longitude}"

    response = requests.get(url, headers=headers)
    with open('darksky_test_info.json', 'w') as file:
        file.write(json.dumps(response.json()))


with open('darksky_test_info.json', 'r') as myfile:
    data = json.loads(myfile.read())

# print(json.dumps(data['currently'], sort_keys=True, indent=2))
# print(json.dumps(data['daily'], sort_keys=True, indent=2))

print(json.dumps(data['currently'], indent=2))
print("Feels like: %s °F" % json.dumps(data['currently']['apparentTemperature'], indent=2))
# print(datetime.utcfromtimestamp(data['currently']['time']).strftime('%Y-%m-%d %H:%M:%S'))
utc_time = datetime.fromtimestamp(data['currently']['time'], timezone.utc)
# print(utc_time)
# local_time = utc_time.astimezone()
local_time = unix_time_to_local_time(data['currently']['time'])
# print(local_time)
print(local_time.strftime("%Y-%m-%d %H:%M:%S (%Z)"))

"""
Next three days (might include today as well)
"""
print(json.dumps(data['daily']['data'][0], indent=2))
for x in range(3):
    print("High temp: %s " % data['daily']['data'][x]['temperatureHigh'])
    print("High temp time: %s " % unix_time_to_local_time(data['daily']['data'][x]['temperatureHighTime']).strftime("%Y-%m-%d %H:%M:%S (%Z)"))
    print("Min temp: %s " % data['daily']['data'][x]['temperatureLow'])
    print("Min temp time: %s " % unix_time_to_local_time(data['daily']['data'][x]['temperatureMinTime']).strftime("%Y-%m-%d %H:%M:%S (%Z)"))
    print("Max temp: %s " % data['daily']['data'][x]['temperatureMax'])
    print("Max temp time: %s " % unix_time_to_local_time(data['daily']['data'][x]['temperatureMaxTime']).strftime("%Y-%m-%d %H:%M:%S (%Z)"))
    precip_probability = data['daily']['data'][x]['precipProbability'] * 100
    print(f"Precip probability: {precip_probability}%")
    print("Precip intensity (inches of water per hour): %s " % data['daily']['data'][x]['precipIntensity'])
    if data['daily']['data'][x]['precipIntensity'] > 0:
        print("Precip type: %s " % data['daily']['data'][x]['precipType'])
    print("")
    # print("Date: %s " % unix_time_to_local_time(data['daily']['data'][x]['time']).strftime("%Y-%m-%d %H:%M:%S (%Z)"))

"""
Next 6 hours
"""
# for x in range(5):
#     print(json.dumps(data['hourly']['data'][x], indent=2))
#     time = data['hourly']['data'][x]['time']
#     print(datetime.utcfromtimestamp(time).strftime('%Y-%m-%d %H:%M:%S'))
#
#     utc_time = datetime.fromtimestamp(time, timezone.utc)
#     print(utc_time)
#     local_time = utc_time.astimezone()
#     print(local_time)
#     print(local_time.strftime("%Y-%m-%d %H:%M:%S (%Z)"))
#



"""
darksky = DarkSky(API_KEY)

latitude = 44.0583333
longitude = -121.3141667
forecast = darksky.get_forecast(
    latitude,
    longitude,
    extend=False, # default `False`
    lang=languages.ENGLISH, # default `ENGLISH`
    values_units=units.US, # default `auto`
    exclude=[weather.MINUTELY, weather.ALERTS],
    timezone='America/Los_Angeles'
)

# with open('/home/walker/Documents/Projects/weather_server/darksky_test_info.json', 'w') as file:
#     file.write(forecast)

# with open('test_info.json', 'r') as myfile:
#     data = myfile.read()

print(forecast.timezone)
json_test = ""
for item in forecast.daily:
    print(item)
    json_test = json.dumps(item)
    print(item.summary)

print(forecast.currently.temperature)
print(forecast.currently.summary)
print(json_test)
print(forecast.daily)
"""
