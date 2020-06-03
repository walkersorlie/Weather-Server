import json
import requests
import os
from datetime import datetime, timezone
from pymongo import MongoClient



def unix_timestamp_to_local_time(unix_timestamp):
    utc_time = datetime.fromtimestamp(unix_timestamp, timezone.utc)
    return utc_time.astimezone()

def local_timestamp_to_utc_timestamp(local_timestamp):
    utc_datetime = datetime.fromtimestamp(local_timestamp, timezone.utc)
    return utc_datetime.astimezone(timezone.utc).replace(tzinfo=None).timestamp()


if not os.path.isfile('darksky_test_info.json'):
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



data_point_currently = data['currently']
# print(json.dumps(data_point_currently, indent=2))
# print(unix_timestamp_to_local_time(data_point_currently['time']).strftime("%Y-%m-%d %H:%M:%S (%Z)"))


db_url = 'mongodb://localhost:27017'
client = MongoClient(db_url)

db = client.weather_server
collection_currently = db.currently_weather_collection
document_currently = data_point_currently
document_id_currently = collection_currently.insert_one(document_currently).inserted_id
# client.close()


"""
Next 6 days (includes today). Total of 7 days. Starts and ends at midnight
"""
days_data_block = []
for day in data['daily']['data']:
    # print(datetime.utcfromtimestamp(day['time']).strftime("%Y-%m-%d %H:%M:%S (%Z)"))
    # print(unix_timestamp_to_local_time(day['time']).strftime("%Y-%m-%d %H:%M:%S (%Z)"))

    day['time'] = local_timestamp_to_utc_timestamp(day['time'])
    days_data_block.append(day)


collection_daily = db.collection_weather_daily
# result = collection_daily.insert_many(next_three_days)

dt = datetime.now().astimezone()
# print(dt.strftime("%Y-%m-%d %H:%M:%S (%Z)"))
dt = dt.replace(hour=0, minute=0, second=0, microsecond=0)
# print(dt.strftime("%Y-%m-%d %H:%M:%S (%Z)"))
# print(dt.timestamp())
dt_utc = dt.astimezone(timezone.utc).replace(tzinfo=None)
# print(dt_utc)
# print(dt_utc.timestamp())

# for doc in collection_daily.find({"time": {"$lt": dt_utc.timestamp()}}):
#     print(unix_timestamp_to_local_time(doc['time']).strftime("%Y-%m-%d %H:%M:%S (%Z)"))


"""
The next two days, starting and ending on the hour the request was made
"""
hours_data_block = []
for hour in data['hourly']['data']:
    hour['time'] = local_timestamp_to_utc_timestamp(hour['time'])
    hours_data_block.append(hour)


collection_hourly = db.collection_weather_hourly
# collection_hourly_result = collection_hourly.insert_many(hours_data_block)
