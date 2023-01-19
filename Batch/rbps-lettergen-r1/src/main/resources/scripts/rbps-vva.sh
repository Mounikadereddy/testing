#!/usr/bin/bash

#
#    Send pdf files to Virtual VA.
#

# ----------------------------------------------------------------------

#
#    Usage.
#
usage()
{
    trap '' 0

    echo
    echo "Usage: ${MYNAME}  <environment {devl,prod,etc.}> <log directory>"
    echo

    exit ${1:-1}
}


if [ $# -ne 2 ]
then
  # echo "Usage: `basename $0` {arg}"
  usage 0
fi



#
#    Check arguments.
#
while [ ! -z "$*" ]
do
    case ${1} in

    #
    #    Print out the usage.
    #
    -h|-help|--help)

        usage 0
        ;;

    *)
        export ENV="${1}"
        export LOGDIR="${2}"
        shift
        ;;

    esac
    shift

done

export DEST_DIR

DEST_DIR="/AdobeDoc/${ENV}/RBPS"

if [ "$(hostname)" = "vbadev" ]
then
    DEST_DIR="/vbadata/cnpltrs/${ENV}/RBPS"
fi


export TOP
# script_dir=$(cd ${0%/*} && echo $PWD/${0##*/})
script_location="$(cd "${0%/*}" 2>/dev/null; echo "$PWD"/"${0##*/}")"
script_dir="$(dirname $script_location)"
export script_dir

TOP="$(dirname $script_dir)"
echo "script_location is ${script_location}"
echo "script_dir is ${script_dir}"
echo "TOP is ${TOP}"

#
#    Source in core functions.
#
if [ -z "${TOP}" ]
then
    echo "Can't figure out TOP."

    exit 1
fi


if [ ! -d "${TOP}" ]
then
    error "TOP >${TOP}< is NOT a directory."
fi

if [ -z "${FUNCTIONS}" ]
then
    FUNCTIONS="${script_dir}/rbps-functions.sh"
fi

if [ ! -d "${LOGDIR}" ]
then
    error "LOGDIR >${LOGDIR}< is NOT a directory."
fi
# this used to be true, but now they are passing LOGDIR on the command line.
# if [ -z "${LOGDIR}" ]
# then
#     LOGDIR="${TOP}/logs"
# fi

if [ -f ${FUNCTIONS} ]
then
    . ${FUNCTIONS}
else
    echo "FUNCTIONS file [${FUNCTIONS}] not found."

    exit 1
fi

# ----------------------------------------------------------------------

#numDocs=`wc -l ${TOP} | awk '{print $1}'`


STAGE_TOTAl=7

cd $DEST_DIR
initializeDirectoryStructure
setupLogFile
movePdfFilesToPending
moveCsvFilesToPending
concatenateCsvFilesIntoJobsCsv
( cd $PENDING; createZipFile )
removeOldFiles


#
#    OK, we're done
#
stageMessage "Done."

cleanup 0
