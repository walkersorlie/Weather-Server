import json
import requests
import os
import argparse
from datetime import datetime, timezone
from pymongo import MongoClient
import pytz



def unix_timestamp_to_local_time(unix_timestamp):
    utc_time = datetime.fromtimestamp(unix_timestamp, timezone.utc)
    return utc_time.astimezone()


def local_timestamp_to_utc_timestamp(local_timestamp):
    utc_datetime = datetime.fromtimestamp(local_timestamp, timezone.utc)
    utc_dt = datetime.fromtimestamp(local_timestamp, pytz.utc)
    return utc_dt.timestamp()


def make_api_request():
    API_KEY = os.environ['DARKSKY_API_KEY']
    latitude = 44.0583333
    longitude = -121.3141667

    headers = {'User-Agent': 'Mozilla/5.0', 'Accept-Encoding': 'gzip'}
    # ?exclude=currently,minutely,hourly,alerts,flags
    url = f"https://api.darksky.net/forecast/{API_KEY}/{latitude},{longitude}"

    return requests.get(url, headers=headers)


def daily(db, data):
    """
    Next 6 days (includes today). Total of 7 days. Starts and ends at midnight

    Only include today and the next 4 days
    """
    days_data_block = []
    for x, day in enumerate(data['daily']['data']):
        if x > 4:
            break

        day['time'] = local_timestamp_to_utc_timestamp(day['time'])
        days_data_block.append(day)

    collection_daily = db.collection_weather_daily
    collection_daily_result = collection_daily.insert_many(days_data_block)


def hourly(db, data):
    """
    The next two days, starting and ending on the hour the request was made

    Only include request hour and next 7 hours
    """
    hours_data_block = []
    for x, hour in enumerate(data['hourly']['data']):
        if x > 7:
            break

        hour['time'] = local_timestamp_to_utc_timestamp(hour['time'])
        hours_data_block.append(hour)

    collection_hourly = db.collection_weather_hourly
    collection_hourly_result = collection_hourly.insert_many(hours_data_block)


def alerts(db, data):
    """
    Alerts data block, if present
    """
    if 'alerts' in data:
        alerts_block = []
        collection_alerts_result = []
        for alert in data['alerts']:
            # print(json.dumps(alert, indent=2))
            alert['time'] = local_timestamp_to_utc_timestamp(alert['time'])
            alert['expires'] = local_timestamp_to_utc_timestamp(alert['expires'])
            alerts_block.append(alert)


        collection_alerts = db.collection_alerts
        collection_alerts_result = collection_alerts.insert_many(alerts_block)


def currently(db, data):
    data_point_currently = data['currently']
    data_point_currently['time'] = local_timestamp_to_utc_timestamp(data_point_currently['time'])
    collection_currently = db.collection_weather_currently
    document_id_currently = collection_currently.insert_one(data_point_currently).inserted_id


def all(db, data):
    currently(db, data)
    hourly(db, data)
    daily(db, data)
    alerts(db, data)


def main(args):
    response = make_api_request()
    data = response.json()

    user_password = os.environ['MONGODB_ATLAS_USER_PASSWORD']
    db_url = f'mongodb+srv://walker:{user_password}@weather-server-y8sjh.mongodb.net/weather_server?retryWrites=true&w=majority'
    client = MongoClient(db_url)
    db = client.weather_server

    for func in args.f:
        globals()[func](db, data)


    client.close()


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument('-f', nargs='*', choices=['all', 'currently', 'hourly', 'daily', 'alerts'], type=str)

    # for _, value in parser.parse_args()._get_kwargs():
    #     if value is not None:
    #         print(value)
    args = parser.parse_args()
    main(args)
