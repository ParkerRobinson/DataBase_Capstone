#!/bin/bash
chmod -R 777 ./auto
docker build -t app -f ./Dockerfile ../.. 
docker rm -f app 
cat <<EOF












Starting application...
EOF
docker run -d --rm -p 8080:8080 \
  -v $(pwd)/maven:/maven \
  -v $(pwd)/auto:/code \
  --link postgres:postgres \
  --name app \
  app $*

echo -n "waiting for application to startup."
i=1
while [ $i -lt 100 ]; do
  i=$(($i + 1))
  echo -n "."
  if [ $(docker logs app 2>&1 |grep -c -- '--Application Started--') -ne 0 ]; then
    echo "Started!"
    break
  fi
  sleep 1
done
echo
docker logs app
