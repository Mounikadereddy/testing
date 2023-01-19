#!/usr/bin/sh

if [ $# -ne 1 ]
then
	echo "Usage: $0 env [devl|intgd|cert|prod]"
	exit 10
fi

whoami=`/usr/ucb/whoami`
if [ $whoami != "weblogic" ]
then
    echo "Sorry, you must run as user 'weblogic'!"
    exit 20
fi

batchFile=/tmp/batchFile.$$
host=`hostname`

env=$1

case $env in
devl|intgd|cert)
	targetHost="ecomsftptst.fsc.va.gov"
	;;
prod)
	targetHost="ecomsftpprd.fsc.va.gov"
	;;
*)
	echo "Error: invalid env [$env]!"
	exit 30
	;;
esac

dstDir="AdobeDoc/${env}/RBPS/ftp"
srcDir="/${dstDir}"

if [ ! -d "$srcDir" ]
then
	echo "Error: directory [$srcDir] does not exist on host [$host]!"
	exit 40
fi 

cd $srcDir

rc=`/usr/bin/find $srcDir -name \*.pdf | wc -l`
if [ $rc -eq 0 ]
then
	echo "No pdf files to transfer. Bye..."
	exit 0
fi
#
# create transfer batch
#
today=`date "+%Y%m%e"`
echo "cd $dstDir" > $batchFile
echo "mkdir $today" >> $batchFile
echo "cd $today" >> $batchFile
echo "put ." >> $batchFile
echo "exit" >> $batchFile
#
# run sftp with the batch 
#
echo "...transferring pdf files for today [$today]"
sftp -B $batchFile weblogic@${targetHost}
retVal=$?
#
# remove pdf files
#
if [ $retVal -eq 0 ]
then
	echo "...Ready to remove EVERYTHING under ftp"
	#echo "...please do this:"
    cd $srcDir
	rm -rf *
else
	echo "Error: failed to complete sftp. retval=" $retVal
	exit 50
fi
rm $batchFile
