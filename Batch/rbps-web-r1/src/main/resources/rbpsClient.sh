#! /bin/ksh

rbpsJar="RbpsClient.jar"
JAVA_HOME=/opt/java/current

if [ $# -ne 1 ]
then
    echo "Usage: $0 dev|cert|intg|prep|prod|perf "
    exit 1
fi

env=$1
url_env=$1

case $1 in
dev|cert|prep|prod|perf)
    ;;
intg)
    env="idev"
    ;;
*)
    echo "Unkown env [$env]!"
    exit 2
    ;;
esac

jarDir="/apps/VBAIntDomain/${env}/current/scripts"

if [ ! -d $jarDir ]
then
    echo "$jarDir does not exist!"
    exit 3
fi

cd $jarDir

if [ ! -f $rbpsJar ]
then
    echo "$rbpsJar does not exist in directory $jarDir."
 
    exit 4
fi

url="http://bep${url_env}.vba.va.gov:80/RbpsServices/RbpsWS"

#$JAVA_HOME/bin/java -jar $rbpsJar $url >/dev/null 2>&1
$JAVA_HOME/bin/java -jar $rbpsJar $url 
