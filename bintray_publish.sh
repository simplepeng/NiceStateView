#!/bin/bash
echo "----------------------------------start----------------------------------"

./gradlew clean build bintrayUpload -PbintrayUser=simplepeng -PbintrayKey=e5e52eecf87d7786b59fe828cab0ee8377934ecd -PdryRun=false

echo "----------------------------------finish----------------------------------"