import json
import requests
import os
from datetime import datetime, timezone
from pymongo import MongoClient
import pytz



def unix_timestamp_to_local_time(unix_timestamp):
    utc_time = datetime.fromtimestamp(unix_timestamp, timezone.utc)
    return utc_time.astimezone()

def local_timestamp_to_utc_timestamp(local_timestamp):
    utc_datetime = datetime.fromtimestamp(local_timestamp, timezone.utc)
    utc_dt = datetime.fromtimestamp(local_timestamp, pytz.utc)
    # return utc_datetime.astimezone(timezone.utc).replace(tzinfo=None).timestamp()
    # print(utc_dt)
    # print(utc_dt.timestamp())
    return utc_dt.timestamp()

def make_api_request():
    API_KEY = os.environ['DARKSKY_API_KEY']
    latitude = 44.0583333
    longitude = -121.3141667

    headers = {'User-Agent': 'Mozilla/5.0', 'Accept-Encoding': 'gzip'}
    # ?exclude=currently,minutely,hourly,alerts,flags
    url = f"https://api.darksky.net/forecast/{API_KEY}/{latitude},{longitude}"

    return requests.get(url, headers=headers)


if not os.path.isfile('darksky_test_info.json'):
    response = make_api_request()

    with open('darksky_test_info.json', 'w') as file:
        file.write(json.dumps(response.json()))


with open('darksky_test_info.json', 'r') as myfile:
    data = json.loads(myfile.read())



user_password = os.environ['MONGODB_ATLAS_USER_PASSWORD']
db_url = f'mongodb+srv://walker:{user_password}@weather-server-y8sjh.mongodb.net/weather_server?retryWrites=true&w=majority'

# db_url = 'mongodb://localhost:27017'
client = MongoClient(db_url)
db = client.weather_server


collection_currently = db.collection_weather_currently

data_point_currently = data['currently']
data_point_currently['time'] = local_timestamp_to_utc_timestamp(data_point_currently['time'])
document_id_currently = collection_currently.insert_one(data_point_currently).inserted_id



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
collection_daily_result = collection_daily.insert_many(days_data_block)


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
collection_hourly_result = collection_hourly.insert_many(hours_data_block)


"""
Alerts data block, if present
"""
if 'alerts' in data:
    print(json_dumps(data['alerts'], indent=2))
    data['alerts']['time'] = local_timestamp_to_utc_timestamp(data['alerts']['time'])

    collection_alerts = db.collection_alerts
    collection_alerts_result = collection_alerts.insert_one(data['alerts']).inserted_id


client.close()
