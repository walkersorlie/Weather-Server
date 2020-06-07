from pymongo import MongoClient
import os


user_password = os.environ['MONGODB_ATLAS_USER_PASSWORD']

# db_url = 'mongodb://localhost:27017'
db_url = f'mongodb+srv://walker:{user_password}@weather-server-y8sjh.mongodb.net/weather-server?retryWrites=true&w=majority'
client = MongoClient(db_url)

if client:
    print("Successfully connected to MongoDB")
