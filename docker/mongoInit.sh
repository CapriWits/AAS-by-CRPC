#!/bin/bash

# Init MongoDB data
docker exec mongo mongorestore --username=admin --password=admin /data/db/mongodump

# Check if the data is initialized successfully
#docker exec mongo mongosh -u admin -p admin --eval "use EAS_By_CRPC; db.Schedule.find().pretty();"

