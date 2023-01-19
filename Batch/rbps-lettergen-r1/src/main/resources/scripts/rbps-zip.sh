#! /bin/ksh

if [ $# -ne 1 ]
then
	echo "Usage: $0 [devl|intgd|cert|prep|prod|perf]"
	exit 10
fi

whoami=`/usr/ucb/whoami`
if [ $whoami != "weblogic" ]
then
	echo "Sorry, you must run as user 'weblogic'!"
	exit 20
fi

rbps_env=$1
dom_env=$rbps_env

case $rbps_env in
devl)
	dom_env="dev"
	;;
cert|prep|prod|perf)
	;;
intgd)
	dom_env="idev"
	;;
*)
	echo "Eror: unknown env [$rbps_env]."
	exit 30
	;;
esac

cmd=rbps-vva.sh
cmdDir="/apps/VBAIntDomain/${dom_env}/current/scripts"
logDir="/weblogs/${dom_env}/domains/VBAIntDomain/VBAIntCluster/RBPS"

#echo rbps_env=$rbps_env
#echo dom_env=$dom_env
#echo cmdDir=$cmdDir
#echo logDir=$logDir
#echo cmd=$cmd

if [ ! -d $cmdDir ]
then
	echo "Error: cmdDir [$cmdDir] does not exist!"
	exit 40
fi

cd $cmdDir

if [ ! -f $cmd ]
then
	echo "Error: $cmd does not exist in [$cmdDir] !"
	exit 50
fi

if [ ! -d $logDir ]
then
	echo "...creating logDir [$logDir]"
	mkdir -p $logDir
fi
#
# cmd rbps_env logDir
#
./$cmd  $rbps_env $logDir
