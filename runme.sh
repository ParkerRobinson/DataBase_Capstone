#!/bin/bash
# Turn on debug
if [ "$1" == "-d" ]; then
  set -x
  shift
fi



# Cleanup in case this has previously been run
docker rm -f postgres >/dev/null 2>&1


# Startup server
clear
cat <<EOF




##########################################
#     Starting PostgreSQL Server         #
##########################################





EOF
docker run -d --name postgres postgres -c 'log_min_duration_statement=0'






# While the server is starting up, clone the git repo
cat <<EOF




##########################################
#       Cloning Git Repository           #
##########################################





EOF
[ -d cs4604-groupahr ] || mkdir cs4604-groupahr
cd cs4604-groupahr 
if [ -d .git ]; then
  git reset --hard HEAD && git pull
else
  git clone -b master https://gitlab+deploy-token-29:3FPpFjzsdZzg76-6V93F@code.vt.edu/brannon7/cs4604-groupahr.git . || exit 1
fi

# Wait for server to initialize
while [ -z "$(docker logs postgres | grep 'database system is ready to accept connections')" ]; do
  sleep 1
done
docker logs postgres






# Install
cat <<EOF
##########################################
#     Connecting using psql client       #
##########################################
# Use Ctrl-D to exit
EOF

if [ -f ./sql/install.sql ]; then
  volume="$(pwd)/sql:/sql"
  docker run -it --rm -v $volume --link postgres:postgres postgres psql -a -h postgres -U postgres -f /sql/install.sql
fi
if [ -f ./sql/load.sql ]; then
  volume="$(pwd)/sql:/sql"
  docker run -it --rm -v $volume --link postgres:postgres postgres psql -a -h postgres -U postgres -f /sql/load.sql
fi
cat <<EOF



##########################################
# Server is still running. 
##########################################
# If you wish to stop the server, 
# run the following command:
# docker rm -f postgres

EOF


# Startup client
cd ./code/java
bash ./runme.sh


cat <<EOF
##########################################
## To view database logs issue command:

docker logs postgres

## To view application logs issue command:

docker logs app

## To login to the database issue command:

docker run -it --rm --link postgres:postgres postgres psql -a -h postgres -U postgres 
##########################################
EOF

