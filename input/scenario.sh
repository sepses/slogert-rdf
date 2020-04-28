#!/usr/bin/env bash


## == transformation duration

function getDuration() {
    if (( $2 > 3600 )) ; then
        let "hours=$2/3600"
        let "minutes=($2%3600)/60"
        let "seconds=($2%3600)%60"
        echo "$1 completed in $hours hour(s), $minutes minute(s) and $seconds second(s)"
    elif (( duration > 60 )) ; then
        let "minutes=($2%3600)/60"
        let "seconds=($2%3600)%60"
        echo "$1 completed in $minutes minute(s) and $seconds second(s)"
    else
        echo "$1 completed in $2 seconds"
    fi
}

## == transform Raw to structured

start=$SECONDS
java -jar target/slogert2rdf-0.1.0-SNAPSHOT-jar-with-dependencies.jar "slogert-owl.ttl" "input/vsftpd.log_structured.ttl" "output/ftp.ttl"
duration=$(( SECONDS - start ))
getDuration "transform-ftp" $duration

start=$SECONDS
java -jar target/slogert2rdf-0.1.0-SNAPSHOT-jar-with-dependencies.jar "slogert-owl.ttl" "input/auth.log_structured.ttl" "output/auth.ttl"
duration=$(( SECONDS - start ))
getDuration "transform-authlog" $duration

start=$SECONDS
java -jar target/slogert2rdf-0.1.0-SNAPSHOT-jar-with-dependencies.jar "slogert-owl.ttl" "input/kern.log_structured.ttl" "output/kern.ttl"
duration=$(( SECONDS - start ))
getDuration "transform-kern" $duration

start=$SECONDS
java -jar target/slogert2rdf-0.1.0-SNAPSHOT-jar-with-dependencies.jar "slogert-owl.ttl" "input/sys.log_structured.ttl" "output/syslog.ttl"
duration=$(( SECONDS - start ))
getDuration "transform-syslog" $duration

start=$SECONDS
java -jar target/slogert2rdf-0.1.0-SNAPSHOT-jar-with-dependencies.jar "slogert-owl.ttl" "input/apache-host.log_structured.ttl" "output/apache-host.ttl"
duration=$(( SECONDS - start ))
getDuration "transform-apache-host" $duration

start=$SECONDS
java -jar target/slogert2rdf-0.1.0-SNAPSHOT-jar-with-dependencies.jar "slogert-owl.ttl" "input/apache-error.log_structured.ttl" "output/apache-error.ttl"
duration=$(( SECONDS - start ))
getDuration "transform-apache-error" $duration

start=$SECONDS
java -jar target/slogert2rdf-0.1.0-SNAPSHOT-jar-with-dependencies.jar "slogert-owl.ttl" "input/apache-access.log_structured.ttl" "output/apache-access.ttl"
duration=$(( SECONDS - start ))
getDuration "transform-apache-access" $duration

start=$SECONDS
java -jar target/slogert2rdf-0.1.0-SNAPSHOT-jar-with-dependencies.jar "slogert-owl.ttl" "input/audit.log_structured.ttl" "output/audit.ttl"
duration=$(( SECONDS - start ))
getDuration "transform-auditlog" $duration

