#! /bin/ksh
# ------------------------------------------------------------------------------
#  Script : RBPSBatch.sh
#
#  Purpose: Start any RBPS clients currently not running. RBPS Client creates a file  named RBPSBatchProcess_X in the 
#       logs directory where X is the client number. This file is removed by the client when it terminates. This script 
#       will restart the client X if the RBPSBatchProcess_X is not present in the logs directory.
#
#  Usage: RBPSBatch.sh <URL_ENV> <ENV> <NUM_CLIENTS>
#
#  URL_ENV = bepdev, bepwebdevl, beplinktest, bepwebtest,  bepprotest or webapps
#
#  LOGS_DIR: /AdobeDoc/<ENV>/RBPS/batch_process 
#
##----------------------------------------------------------------------------------
##  
##  $Id: %PM%,v %PIV%, %PRT%  %AUTHOR% Exp $
##  
##-----------------------------------------------------------------------------------

# function to check if the script is already running
check_if_already_running() {
    IAm="${0##*/}"
    LF="$LOGS_DIR/$IAm.lock"

    echo  "scriptname=$IAm"
    echo  "lockfile=$LF"

    set -C
    if  ! echo $$ > "$LF"
    then
      printf "%s: Exiting, Already running as pid %s.\n" "$IAm" "$(cat "$LF")" >&2
      exit 1
    fi
    set +C

    trap 'rm -f "$LF"' EXIT
    trap 'echo "$IAm: Terminated by ABRT signal; $LF removed." >&2
        exit 134' ABRT
    trap 'echo "$IAm: Terminated by HUP signal; $LF removed." >&2
        exit 129' HUP
    trap 'echo "$IAm: Terminated by INT signal; $LF removed." >&2
      exit 130' INT
    trap 'echo "$IAm: Terminated by QUIT signal; $LF removed." >&2
        exit 131' QUIT
    trap 'echo "$IAm: Terminated by TERM signal; $LF removed." >&2
        exit 143' TERM
}

# function to check and restart RBPS Clients if they are not running
start_rbps_clients() {
    MY_URL_UNV=$1
    MAX_CLIENTS=$2
    RBPS_JAR="RbpsClient.jar"
    JAVA_HOME=/opt/java/1.6.0-29

    MY_URL="http://${URL_ENV}.vba.va.gov/RbpsServices/RbpsWS"
    
    cd  /apps/VBAIntDomain/$ENV/current/scripts
    
    if [ ! -f $RBPS_JAR ]
    then
        print "$RBPS_JAR does not exist in this directory."
        exit 1
    fi
   

	
    integer FILE_COUNT=1
    while [ $FILE_COUNT -le $MAX_CLIENTS ]
    do
        if [ -f $LOGS_DIR/RBPSBatchProcess_$FILE_COUNT ]
        then
            print " RBPSBatchProcess_$FILE_COUNT already exists"
        else
	    print " $LOGS_DIR/RBPSBatchProcess_$FILE_COUNT does not exist"
	    print "calling $JAVA_HOME/bin/java -jar $RBPS_JAR $MY_URL $FILE_COUNT $MAX_CLIENTS"
            $JAVA_HOME/bin/java -jar $RBPS_JAR $MY_URL $FILE_COUNT $MAX_CLIENTS &
            
        fi
        (( FILE_COUNT = FILE_COUNT + 1 ))
        print "FILE_COUNT = $FILE_COUNT"
        sleep 5
    done
}

# start of main process
if [ $# -lt 3 ]
then
    print "USAGE: $0 <URL_ENV> <ENV> <NUM_CLIENTS>"
    print "URL_ENV = bepdev, bepwebdevl, beplinktest, bepwebtest,  bepprotest or webapps"
    exit 1
fi

URL_ENV=$1
ENV=$2
NUM_CLIENTS=$3

echo "URL_ENV=$URL_ENV, ENV=$ENV, NUM_CLIENTS=$NUM_CLIENTS"

#set the LOG_DIR for the curent ENV
LOGS_DIR=/AdobeDoc/$ENV/RBPS/batch_process 
echo "LOGS_DIR=$LOGS_DIR"

if [ ! -d $LOGS_DIR ]
then
    print "directory $LOGS_DIR does not exist."
    exit 1
fi

# check if number of clients is greater than zero
if [ $NUM_CLIENTS -le 0 ]
then
    print "NUM_CLIENTS must be greater than zero"
    print "USAGE: $0 <URL_ENV> <ENV> <NUM_CLIENTS>"
    print "URL_ENV = bepdev, bepwebdevl, beplinktest, bepwebtest,  bepprotest or webapps"
    exit
fi

#check if this script is already running
check_if_already_running

# if any RBPS client is not running, start the client
start_rbps_clients $URL_ENV $NUM_CLIENTS
