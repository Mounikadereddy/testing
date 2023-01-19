#!/usr/bin/bash

#
#   common funtions that are useful when writing shell scripts.
#

#
#    What is the name of the script?
#
myname=`basename $0`
MYNAME=${myname}


#
#   The root directory for the rbps scripts.
#   From Ashok.
#
TOP=${TOP:-/opt/rbps}
export TOP
TMPDIR=$LOGDIR/tmp
export TMPDIR


#
#    Name of the host, this is run on.
#
HOST=`hostname`
export HOST


#
#    Which platform is this?
#
PLATFORM=`uname -s`
export PLATFORM


#
#    Where the script was run from.
#
RUNDIR=`pwd`



#
#    ----- standard variables -----
#
FALSE=1
TRUE=0



#
#    ----- Case-formatting functions. -----
#
toLower()
{
    echo "${1}" | tr '[A-Z]' '[a-z]'
}


toUpper()
{
    echo "${1}" | tr '[a-z]' '[A-Z]'
}


capitalize()
{
    str="${1}"

    tmp=`echo "${str}" | cut -c 1`
    firstChar=`toUpper ${tmp}`

    tmp=`echo ${str} | cut -c 2-`
    restOfChars=`toLower ${tmp}`

    echo ${firstChar}${restOfChars}
}



#
#    ----- date formatting functions -----
#

#
#    Return date in YY_MM_DD_HH_MM_SS format.
#
#    Useful for temporary files, etc.
#
dateFormat1()
{
    echo `date '+%Y_%m_%d_%H_%M_%S'`
}


#
#    Return number of seconds since epoch.
#
#    Solaris doesn't support this format. :(
#
# dateFormat2()
# {
#     echo `date '+%s'`
# }


#
#    Forget the year: MM_DD_HH_MM_SS format.
#
dateFormat3()
{
    echo `date '+%m_%d_%H_%M_%S'`
}


#
#    MM_DD format.
#
dateFormat4()
{
    echo `date '+%m_%d'`
}


#
#    HH_MM format.
#
dateFormat5()
{
    echo `date '+%H_%M'`
}


#
#    YYYY:MM:DD format.
#
dateFormat6()
{
    echo `date '+%Y:%m:%d'`
}


#
#    YYYY_MM_DD format.
#
dateFormat7()
{
    echo `date '+%Y_%m_%d'`
}
TODAY=`dateFormat7`
export TODAY


#
#    MMDDHHMM format.
#
dateFormat8()
{
    echo `date '+%m%d%H%M'`
}


#
#   YYmmdd format
#
dateFormat9()
{
    echo `date '+%Y%m%d'`
}

#
#    Simple variables.
#
YEAR=`date '+%Y'`
MONTH=`date '+%m'`
DAY=`date '+%d'`
export YEAR MONTH DAY



#
#    ----- standard variables -----
#

#
#    Double check. Won't get too far w/o TOP being defined.
#
if [ -z "${TOP}" ]
then
    echo "You need to define TOP in your environment."

    exit 1
fi

# START_SINCE_EPOCH=`dateFormat2`
# export START_SINCE_EPOCH


#
#    A directory for temporary files.
#
TMPDIR=${TMPDIR:-/tmp}
export TMPDIR


#
#    Some temporary files
#
TMP=${TMPDIR}/RBPS.`dateFormat7`.${MYNAME}_$$.tmp
TMP1=${TMP}1
TMP2=${TMP}2
TMP3=${TMP}3
TMP4=${TMP}4
TMP5=${TMP}5

#
#    Some temporary files (but fixed name format, i.e, no pid)
#
TMP_FIXED=${TMPDIR}/RBPS.${TODAY}.${MYNAME}.tmp
TMP_FIXED1=${TMP_FIXED}1
TMP_FIXED2=${TMP_FIXED}2
TMP_FIXED3=${TMP_FIXED}3
TMP_FIXED4=${TMP_FIXED}4
TMP_FIXED5=${TMP_FIXED}5


#
#    The key user, defaults to unix user (will be the same on site).
#
#USER=`configValDefault USER "${USER}"`
export USER



#
#    Variable we actually use to email.
#
#WHO_TO_EMAIL=support@samsix.com
#export WHO_TO_EMAIL




#
#    Standard 'ENABLED' flags.
#

#
#    Sometimes we want to email stuff, sometimes not.
#
EMAIL_ENABLED="false"


#
#    Sometimes we want to email 'try' failures.
#
TRY_EMAIL_ENABLED="false"


#
#    And at various stages.
#
STAGE_EMAIL_ENABLED="false"


#
#    And at various stages.
#
STAGE_MESSAGE_ENABLED="true"


#
#    And with error() calls?
#
ERROR_EMAIL_ENABLED="false"


#
#    And with warning() calls?
#
WARNING_EMAIL_ENABLED="false"


#
#    And with debugMsg() calls?
#
DEBUG_LOGGING_ENABLED="true"



#
#    Convenience variable for the S6 bin directory.
#
BINDIR=${TOP}/bin
export BINDIR


#
#    Show the end time (which is really just the mail time).
#
getEmailSubjectHeader()
{
    endTime="`dateFormat4` `dateFormat5`"

    echo "${EMAIL_SUBJECT_HEADER} ${endTime}]:"
}


#
#    Maximum mail file size, in bytes.
#
MAX_MAIL_FILE_SIZE=5000000


#
#    Running file.
#
RUNNING_FILE=${TMPDIR}/${MYNAME}.RUNNING
export RUNNING_FILE


#
#    Stop file.
#
STOP_FILE=${TMPDIR}/${MYNAME}.STOP
export STOP_FILE


#
#    Variable to override 'running' check.
#
FORCE_RUN="false"
export FORCE_RUN


#
#    Stage section values.
#
STAGE_NUM=${STAGE_NUM:-0}
STAGE_NUM=0
export STAGE_NUM

STAGE_TOTAL=${STAGE_TOTAL:-0}
export STAGE_TOTAL


#
#    Return Value checker.
#
RETVAL_TOTAL=0
export RETVAL_TOTAL



#
#    In case the scripts that source these functions in don't set it.
#
SCRIPT_START_TIME="`dateFormat1`"

EMAIL_SUBJECT_HEADER="RBPS_MSG: [HOST=$HOST]: ${MYNAME} [`dateFormat4` `dateFormat5` --"
export EMAIL_SUBJECT_HEADER



###################################################################
#    UNIX FUNCTIONS
###################################################################



#
#    Clean up before exiting.
#
cleanup()
{
    #
    #    Lose some of the traps, in case we get stuck in this function.
    #
    trap '' 0

    errorCode=${1}

    if [ -z "${2}" ]
    then
        checkLog="1"
    else
        checkLog=${2}
    fi

    #
    #    Ignore log check unless we have a non-zero error code.
    #
    if [ "${errorCode}" != "0" ]
    then
        if [ "${checkLog}" != "0" ]
        then
            checkLogForErrors
        fi
    else

        #
        #    Only delete temp files and dirs on non-error, so we have
        #    something to check if something goes wrong.
        #
        /bin/rm -rf ${TMP}*
        /bin/rm -rf ${TMP_FIXED}*
    fi

    #
    #    Seeing as we are stopping, remove any 'running' flag.
    #
    if [ "${RUNNING_FILE}" != "" ]
    then
        /bin/rm -f ${RUNNING_FILE}
    fi

    exit ${errorCode:-1}
}


if [ "${TRAP}" = "" ]
then
    #
    #    make sure interrupts clean up properly
    #
    TRAP="cleanup 1"
fi

trap '${TRAP}' 0 1 2 15


#
#    Print error message.
#
error()
{
    msg="${1}"

    echo "ERROR: [${MYNAME}]: ${msg}"

    cleanup 1
}


#
#    Print/email error message.
#
errorSendLog()
{
    if [ -z "${1}" ]
    then
        retval=1
    else
        retval=${1}
    fi

    echo "ERROR: Check log file [${LOG}]"

    subject="RBPS_MSG: ERROR: ${MYNAME} did not complete successfully."

    if [ "${ERROR_EMAIL_ENABLED}" = "true" ]
    then
        subjectHeader=`getEmailSubjectHeader`

        mailFile -s "${subject}" -t ${WHO_TO_EMAIL} --as "support" -f ${LOG}

        echo "Log file [${LOG}] mailed to [${WHO_TO_EMAIL}]"
    fi

    cleanup ${retval} 0

    return ${retval}
}



#
#    Print warning message.
#
warning()
{
    msg="WARNING: [${MYNAME}]: ${1}"

    if [ -f "${LOG}" ] && [ -w "${LOG}" ]
    then
        if [ ! "${SILENT}" = "true" ]
        then
            echo -e          | tee -a ${LOG}
            echo -e "${msg}" | tee -a ${LOG}
        else
            echo -e          >> ${LOG}
            echo -e "${msg}" >> ${LOG}
        fi
    else
        echo -e "${msg}"
    fi
}



#
#    ----- Set up a log file -----
#
setupLogFile()
{
    action=${1}

    #
    #    Need the Host so when we save the file
    #    we know where it was from and so it does
    #    not clash with similar process output from
    #    other machines.
    #
    HOST=${HOST:-"HOST"}

    now=`dateFormat7`
    logName=${HOST}.${MYNAME}.${now}.log
#    logName=${MYNAME}.log

    #
    #   Use LOGDIR, if it is a directory.
    #
    if [ -d "${LOGDIR}" ]
    then
        LOG=${LOG:-"${LOGDIR}/${logName}"}
    else
        #
        #    else, use the 'logs' directory in the home directory
        #    is it exists.
        #
        if [ -d "${HOME}/logs" ]
        then
            LOG=${LOG:-"${HOME}/logs/${logName}"}
        else
            #
            #    else use the temporary directory.
            #
            LOG=${LOG:-"${TMPDIR}/${logName}"}
        fi
    fi

    export LOG

    #
    #    ----- Do we want to start a new logfile? -----
    #
    case ${action} in

    truncate)
        #
        #    ----- Start a new log file -----
        #
        > ${LOG}

        ;;

    *)
        touch ${LOG}
        ;;
    esac

    #
    #    Send this to the logfile as a marker.
    #
    infoLine="${HOST}.${MYNAME}.${now}.log"

    echo "=================================" >> ${LOG}
    echo "=================================" >> ${LOG}
    echo "=================================" >> ${LOG}
    echo "${LOG}"   >> ${LOG}
    echo "${infoLine}" >> ${LOG}
}



#
#    ----- Set up a log file -----
#
setupLocalLogfile()
{
    action=${1}

    #
    #    Need the Host so when we save the file
    #    we know where it was from and so it does
    #    not clash with similar process output from
    #    other machines.
    #
    logName=${HOST}.${MYNAME}.$$.log

    #
    #   Use LOGDIR, if it is a directory.
    #
    if [ -d "${LOGDIR}" ]
    then
        LOCAL_LOG=${LOCAL_LOG:-"${LOGDIR}/${logName}"}
    else
        #
        #    Else, use the 'logs' directory in the home directory
        #    is it exists.
        #
        if [ -d "${HOME}/logs" ]
        then
            LOCAL_LOG=${LOCAL_LOG:-"${HOME}/logs/${logName}"}
        else
            #
            #    Else use the temporary directory.
            #
            LOCAL_LOG=${LOCAL_LOG:-"${TMPDIR}/${logName}"}
        fi
    fi

    export LOCAL_LOG

    #
    #    ----- Do we want to start a new logfile? -----
    #
    case ${action} in

    truncate)
        #
        #    ----- Start a new log file -----
        #
        > ${LOCAL_LOG}

        ;;

    *)
        touch ${LOCAL_LOG}
        ;;
    esac
}



#
#    Echo preceded by a time-stamp.
#
date_message()
{
    ts=`date '+%m-%d %H:%M:%S'`
    message="${1}"
    now=`date +%s`
    msg="[---${MYNAME} [${HOST}] ${ts}---]:  ${message}"

    if [ -f "${LOG}" ] && [ -w "${LOG}" ]
    then
        #
        #    Take into account Solaris boxes and their
        #    version of echo.
        #
        if [ ! "${SILENT}" = "true" ]
        then
            echo -e          | tee -a ${LOG}
            echo -e "${msg}" | tee -a ${LOG}
        else
            echo -e          >> ${LOG}
            echo -e "${msg}" >> ${LOG}
        fi
    else
        echo -e "${msg}"
    fi
}



#
#    Kill a named process.
#
killproc()
{
    #
    #    Usage
    #
    if [ $# = 0 ]
    then
        echo "Usage: $0 {program}"
        return 1
    fi

    #
    #    Define variables
    #
    base=`basename $1`
    pidlist=`whatpid $1`

    pid=
    for apid in $pidlist
    do
       [ -d /proc/$apid ] && pid="$pid $apid"
    done

    #
    #    Kill it.
    #
    if [ "$pid" != "" ]
    then
        if checkpid "$pid" 2>&1
        then
            echo "Killing processes [$pid]"
            kill -9 $pid
        fi
    fi
}



#
#    Return the pid for a program
#
whatpid()
{
    base=`basename $1`

    if [ $# = 0 ]
    then
        echo "Usage: whatpid {program}"
        return 1
    fi

    #
    #    First try "/var/run/*.pid" files.
    #
    if [ -f /var/run/${base}.pid ]
    then
        pid=`cat /var/run/${base}.pid | { read jjj ; echo $jjj ; }`

        if [ "$pid" != "" ]
        then
            echo $pid
            return 0
        fi
    fi

    #
    #    Next try "pidof".
    #
    pid=`pidof -o $$ -o $PPID -o %PPID -x ${base}`

    if [ "$pid" != "" ]
    then
        echo $pid
        return 0
    fi
}



#
#    Check a pid exists on Linux.
#
#    SAI: 8/29: We could make this different, depending
#    on 'uname -s' ?
#
checkpid()
{
    while [ "$1" ]
    do
       [ -d /proc/$1 ] && return 0
       shift
    done

    return 1
}



#
#    Check if a directory exists and is writable.
#
bad_dir()
{
    dir=${1}

    if dir_exists "${dir}"
    then
        return 1
    fi

    if [ ! -w "${dir}" ]
    then
        return 0
    fi

    return 1
}



#
#    Check we have a valid directory.
#
checkDir()
{
    dirName=${1}
    msg=${2}

    if bad_dir ${dirName}
    then
        if [ "${msg}" != "" ]
        then
            error "Bad directory <${dirName}> [ ${msg} ]"
        else
            error "Bad directory <${dirName}>"
        fi
    fi
}



#
#    Given a list of suffixes, delete the empty
#    versions of those files.
#
clearEmptyFiles()
{
    suffixList="${1}"

    if [ "${suffixList}" != "" ]
    then
        for suffix in "${1}"
        do
            for aFile in *.${suffix}
            do
                if [ ! -s ${i} ] && [ -f ${i} ]
                then
                    /bin/rm -vf ${i}
                fi
            done
        done
    fi
}




#
#    Look at each element in a list and compare with another
#    list. If there are any common elements, return true.
#
inlist()
{
    vars="$1"
    thislist="$2"

    for thisvar in ${vars}
    do
        res=`echo ${thislist} | grep ${thisvar}`

        if [ ! "${res}" = "" ]
        then
            return 0
        fi
    done

    return 1
}



#
#    Check if a directory exists and is readable
#
dir_exists()
{
    dir=${1}

    if [ "${dir}" = "" ]
    then
        return 1
    fi

    if [ ! -d "${dir}" ]
    then
        return 1
    fi

    return 0
}



#
#    Check if someone is a particular user
#
userIs()
{
    #
    #    What username do we want
    #
    want=${1}

    #
    #    leave if they fail to send us a parameter
    #
    if [ "${want}" = "" ]
    then
        return 1
    fi

    #
    #    actual
    #
    iam=`id | awk '{print $1}' | sed 's/^.*(//g' | sed 's/).*$//g'`

    #
    #    How do they compare?
    #
    if [ "${iam}" = "${want}" ]
    then
        return 0
    fi

    return 1
}



#
#    Small function to bail, if there are problems.
#
#    Saves doing lots of checks after code and if
#    we forget to do the checks, spewing on unnecessarily.
#
try()
{
    eval $*

    #
    #    TBD SAI: should we tee this to a log file?
    #
    status=$?

    if [ "$status" -ne "0" ]
    then
        mailFile=${TMP}.tryFailureEmail
        >${mailFile}

        subjectDescription="ERROR: the ${1} command did not complete successfully. [return code=${status}]"

        echo                                                      >> ${mailFile}
        echo "Running program: ${MYNAME}"                         >> ${mailFile}
        echo                                                      >> ${mailFile}
        echo '!!! '${subjectDescription}                          >> ${mailFile}
        echo '!!! '"(\"$*\")"                                     >> ${mailFile}
        echo '!!! '"Since this is a critical task, I'm stopping." >> ${mailFile}
        echo                                                      >> ${mailFile}

        cat ${mailFile} >&2

        cleanup 1
    fi
}




#
#    Small function to bail, if there are problems.
#
#    Saves doing lots of checks after code and if
#    we forget to do the checks, spewing on unnecessarily.
#
checkStatus()
{
    #
    #    TBD SAI: should we tee this to a log file?
    #
    status=${1}
    file=${2}

    if [ "$status" -ne 0 ]
    then
        mailFile=${TMP}.tryFailureEmail
        >${mailFile}

        subjectDescription="ERROR: the ${1} command did not complete successfully. [return code=${status}]"

        echo                                                      >> ${mailFile}
        echo "Running program: ${MYNAME}"                         >> ${mailFile}
        echo                                                      >> ${mailFile}
        echo '!!! '${subjectDescription}                          >> ${mailFile}
        echo '!!! '"(\"$*\")"                                     >> ${mailFile}
        echo '!!! '"Since this is a critical task, I'm stopping." >> ${mailFile}
        echo                                                      >> ${mailFile}

        cat ${mailFile} >&2

        cleanup 1
    fi
}



#
#    Increment the stage number.
#
incrStageNum()
{
    stage=`echo "${STAGE_NUM} + 1" | bc -l`
    echo ${stage}
}



#
#    Indicate where we are in a script.
#
stageMessage()
{
    msg="${1}"

    secMsg=
    if [ ! -z "${START_SINCE_EPOCH}" ]
    then
        now=`dateFormat2`
        secs=`echo "${now} - ${START_SINCE_EPOCH} " | bc -l `
        secMsg="[${secs}]"
    fi

    STAGE_NUM=`incrStageNum ${STAGE_NUM}`

    date=`dateFormat4`
    subject="RBPS STAGE MESSAGE: [${date}] [${MYNAME}] [${STAGE_NUM} of ${STAGE_TOTAL}] ${secMsg}"

    #
    #    To the screen too?
    #
    if [ "${STAGE_MESSAGE_ENABLED}" = "true" ]
    then
        date_message "${msg}"
    fi
}



#
#    Report any potential errors found in the logfile.
#
checkLogForErrors()
{
    log="${LOG}"

    if [ "${1}" != "" ]
    then
        log="${1}"
    fi

    #
    #    Skip this if there is no log (as with things like pg.load)
    #
    if [ "${log}" != "" ] && [ -s "${log}" ] && [ -f "${log}" ]
    then
        msg="Checking for issues in ${log} ..."

        #
        #    Just in case we are sharing our log file, check for errors only
        #    on lines that echo our program name.
        #
        grep ERROR ${log} > ${TMP1}

        if [ -s "${TMP1}" ]
        then
            msg="${msg}\n-------------------------------------------------------"
            msg="${msg}\n------------------ ERRORS found -----------------------"
            msg="${msg}\n-------------------------------------------------------"
            msg="${msg}\n `cat ${TMP1}`"
            msg="${msg}\n-------------------------------------------------------"
            date_message "${msg}"

            return 1
        else
            msg="${msg}\nNone found."
            date_message "${msg}"
        fi
    fi
}



#
#    Is this script already running?
#
checkIfAlreadyRunning()
{
    #
    #    Change running name if they pass in a parameter.
    #
    #    This is so different wrappers to a core script
    #    can run simultaneously.
    #
    runflag=${1}

    if [ "${runflag}" != "" ]
    then
        RUNNING_FILE=${RUNNING_FILE}.${runflag}
        export RUNNING_FILE
    fi


    #
    #    We check this to avoid swamping the machine.
    #
    if [ -f "${RUNNING_FILE}" ]
    then
        #
        #    Check if we want to ignore this check?
        #
        if [ "${FORCE_RUN}" != "true" ]
        then
            mailFile=${TMP_FIXED1}.checkIfAlreadyRunning
            > ${mailFile}

            echo ""                                            >> ${mailFile}
            echo "WARNING: ${MYNAME} is already running."      >> ${mailFile}
            echo ""                                            >> ${mailFile}

            echo ""                                            >> ${mailFile}
            echo "If you believe this not to be the case,run:" >> ${mailFile}
            echo ""                                            >> ${mailFile}
            echo "/bin/rm -f ${RUNNING_FILE}"               >> ${mailFile}
            echo ""                                            >> ${mailFile}

            #
            #    Only need this to screen, for now.
            #
            cat ${mailFile} >&2

            echo "Details:"                                    >> ${mailFile}
            ls -lat ${RUNNING_FILE}                         >> ${mailFile}
            echo ""                                            >> ${mailFile}

            echo "Process table:"                              >> ${mailFile}
            /bin/ps auxwww                                     >> ${mailFile}
            echo ""                                            >> ${mailFile}

            #
            #    Mail info off, appropriately.
            #
            checkDate=`dateFormat3`

            subject="RBPS_MSG: WARNING: CRON: [${checkDate}] ${MYNAME} is already running. [HOST=${HOST}] [`date`]"
            msg=`cat ${mailFile}`

            if [ $? = 0 ]
            then
                /bin/rm -f ${mailFile}
            fi

            #
            #    Need to alter the 'trap' to switch off the call to cleanup()
            #    on this call to exit, because that would remove the
            #    'running' flag-file.
            #
            trap '' 0
            exit 9
        fi
    else
        #
        #    Create the 'running file'
        #
        > ${RUNNING_FILE}
    fi
}



#
#       Get the number of files in a directory.
#
numFilesInDir()
{
    dir=${1}
    fileType=${2}
    # echo $fileType

    if bad_dir ${dir}
    then
        error "Bad directory <${dirName}>"
        return -1
    fi

    if [ x"$fileType" = "x" ]
    then
        numFiles=`ls -1 $dir | wc -l`
    else
       numFiles=`ls -1 $dir/*.${fileType} 2>/dev/null | wc -l`
    fi

    echo $numFiles
}


#
#    Get file size.
#
getFileSize()
{
    file=${1}

    #
    #    If the file exists get it's size.
    #
    if [ -f "${file}" ]
    then
        size=`cksum ${file} | awk '{print $2}'`
    else
        size=-1
    fi

    echo ${size}
}



#
#    Check a file isn't too big to email.
#
checkSize()
{
    file=${1}
    maxSize=${2:-${MAX_MAIL_FILE_SIZE}}

    fileSize=`getFileSize ${file}`

    if [ "${fileSize}" -gt "${maxSize}" ]
    then
        mailFile=${TMP1}.checkSize
        > ${mailFile}

        echo "File >${file}<: Too big to email" >> ${mailFile}
        echo ""                                 >> ${mailFile}
        echo "File size=${fileSize}"            >> ${mailFile}
        echo "Max  size=${maxSize}"             >> ${mailFile}
        echo ""                                 >> ${mailFile}
        #echo "`date`"                           >> ${mailFile}
        #echo ""                                 >> ${mailFile}
        echo "HOST=${HOST}"                     >> ${mailFile}
        echo "MYNAME=${MYNAME}"                 >> ${mailFile}
        echo ""                                 >> ${mailFile}
        ls -lat ${file}                         >> ${mailFile}
        echo ""                                 >> ${mailFile}

        subject="RBPS_MSG: ${MYNAME}: FILE TOO BIG TO EMAIL: [HOST=${HOST}] [`date`]"
        mailFile -s "${subject}" -t ${WHO_TO_EMAIL} -f ${mailFile}

        if [ $? = 0 ]
        then
            /bin/rm -f ${mailFile}
        fi

        return 1
    fi

    return 0
}



#
#    Just output under debug.
#
debugMsg()
{
    if [ "${DEBUG_LOGGING_ENABLED}" = "true" ]
    then
        echo "DEBUG: [${MYNAME}]:" $*
    fi
}


#
#    Is a variable set to true or not?
#
isTrue()
{
    variable=${1}

    varValue=`toLower $variable`

    if [ "${varValue}" = "true" ]
    then
        echo $TRUE
    else
        echo $FALSE
    fi
}


createUniqueLogDir()
{
    #
    #    Do we want to start at a particular top level directory?
    #
    prefix=${1}

    if [ -z ${prefix} ]
    then
        prefix=/tmp
    fi

    #
    #    Create a unique log directory..
    #
    suffix=rbps/${YEAR}/${MONTH}/${DAY}

    logDir=$LOGDIR/${suffix}

    #
    #    Create the logdir if needed.
    #
    if [ ! -d "${logDir}" ]
    then
        mkdir -p ${logDir}
    fi

    echo ${logDir}
}



createUniqueLogFile()
{
    #
    #    Create a unique logfile.
    #
    logDir=`createUniqueLogDir`

    #
    #    NOTE: I add in the MYNAME again (to the logfile
    #          name), because when I save the file I want to
    #          know what created it.
    #
    now=`dateFormat1`
    logFile=${logDir}/${MYNAME}.log

    echo "${logDir}/${HOST}.${MYNAME}.${now}.log" >${logFile}
    # >${logFile}
    echo ${logFile}
}



#
#    Return the location of the central log file.
#
getCentralLogFile()
{
    TOP_LOGDIR=${TOP_LOGDIR:-/tmp}

    centralLogFile=${TOP_LOGDIR}/${HOST}.${YEAR}.${MONTH}.CENTRAL.LOG

    echo ${centralLogFile}
}



#
#    central logging...
#
logIt()
{
    msg="${1}"

    if [ -z "${msg}" ]
    then
        msg="NO MESSAGE"
    fi

    #
    #    log line details format.
    #
    now=`dateFormat1`
    logLine="LOG|MYNAME=${MYNAME}|$now|MSG=$msg"

    #
    #    TBD:SAI cron an email of this logfile.
    #
#    centralLogFile=`getCentralLogFile`

    #
    #    Append to log file
    #
#    echo "${logLine}" >> ${centralLogFile}
    echo "${logLine}" >> $LOG
}



#
#       Make sure all our directories are there.
#
makeDir()
{
    dir="${1}"
    if [ -z "${dir}" ]
    then
      date_message "Must give a directory to makeDir"
    fi

    if [ -f "${dir}" ]
    then
        echo "${dir} is a file"
        rm -f "${dir}"
    fi

    if [ ! -d "${dir}" ]
    then
        mkdir $dir
    fi

    date_message "Made directory $dir"
}
initializeDirectoryStructure()
{
    export PENDING
    PENDING="$DEST_DIR/$(dateFormat7)"
    stageMessage "Initialize the rbps directory structure $PENDING"

    for i in $PENDING
    do
        dir="$i"
        # echo "making directory $dir"
        makeDir $dir
    done
}


#
#       Move all the files in the pending dir to the sending dir.
#       Maybe it should be that we move all the pdf and csv files
#       only, but I don't know if that's necessary.
#
movePdfFilesToPending()
{
    stageMessage "Move all pdf files from $DEST_DIR to $PENDING"

    numFiles=`numFilesInDir $DEST_DIR pdf`

    date_message "Moving $numFiles pdf files"
    if [ $numFiles = 0 ]
    then
        return 0
    fi

    mv $DEST_DIR/*.pdf $PENDING
    date_message "Moved $numFiles pdf files"
}
moveCsvFilesToPending()
{
    stageMessage "Move all csv files from $DEST_DIR to $PENDING"

    numFiles=`numFilesInDir $DEST_DIR csv`

    date_message "Moving $numFiles csv files"
    if [ $numFiles = 0 ]
    then
        return 0
    fi

    mv $DEST_DIR/*.csv $PENDING
    date_message "Moved $numFiles csv files"
}


#
#       concatenate all the individual csv files into job.csv
#
concatenateCsvFilesIntoJobsCsv()
{
    stageMessage "Concatenate all the csv files into job.csv"

    rm -f $PENDING/job.csv

    numFiles=`numFilesInDir $PENDING csv`

    if [ $numFiles = 0 ]
    then
        date_message "There are no csv files in $PENDING, so not concatenating into job.csv."

        return 0
    fi

    cat $PENDING/*.csv > $PENDING/job.csv
}



haveFilesToSend()
{
    numFiles=`numFilesInDir $sending`


}



#
#       Create a zip file with the job.csv and *.pdf files.
#
createZipFile()
{
    zip_file="$DEST_DIR/rbps_$(dateFormat9).zip"
    stageMessage "Create zip file $zip_file in $DEST_DIR"


    if [ ! -d "${dir}" ]
    then
        date_message "$DEST_DIR does not exist."

        return 0
    fi

    numFiles=`numFilesInDir $PENDING csv`

    if [ $numFiles = 0 ]
    then
        date_message "There are no csv files in $PENDING, so not making zip file."

        return 0
    fi

    rm -f $zip_file
    zip_output=`zip $zip_file job.csv *.pdf `
    date_message "$zip_output"
}



#
#       Remove files older than 90 days.
#
removeOldFiles()
{
    stageMessage "Removing all files older than 90 days in $DEST_DIR and subdirs"


    if [ ! -d "${dir}" ]
    then
        date_message "$DEST_DIR does not exist, not removing old files."

        return
    fi

    find $DEST_DIR -mtime +90 -print -exec rm -rf {} \;
}



#    =================================================================

#
#    NOTE: KEEP THIS SECTION LAST IN THE FILE.
#


#
#    This brings in specific functions for different platforms.
#
#    It is OK for these files NOT to exist.
#
platformFunctionFile=${BINDIR}/${PLATFORM}.functions

if [ -f "${platformFunctionFile}" ]
then
    . ${platformFunctionFile}
fi


#
#    This brings in specific functions for this particular command.
#
#    It is OK for these files NOT to exist.
#
cmdFunctionFile=${BINDIR}/${FUNCTION}.functions

if [ -f "${cmdFunctionFile}" ]
then
    . ${cmdFunctionFile}
fi




#
#    This is to allow settings for specific machines, making
#    it easier for us to override some stuff here in development;
#    particularly emailing.
#
hostFunctionFile=${BINDIR}/${HOST}.functions

if [ -f "${hostFunctionFile}" ]
then
    . ${hostFunctionFile}
fi
