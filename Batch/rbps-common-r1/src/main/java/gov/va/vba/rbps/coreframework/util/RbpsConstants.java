/*
 * RpbsConstants.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.util;


import org.apache.commons.lang.builder.ToStringStyle;


/**
 *      All RBPS final static constant should be registered and coming from here
 */
public final class RbpsConstants {

    public static final String  VERSION = "RBPS_BATCH_12_0_M";


    /**
     *      RBPS Application name: use this any time
     *      you need to set AppName
     */
    public static final String RBPS_APP_NAME = "RBPS";

    /**
     *      When updating Claim status, use this
     *      hard coded PROC TYPE.
     */
    public static final String PROC_TYPE = "DEPCHG";


    /**
     *      When a service requires a Payee Code, use this
     *      value until a later release requires payments
     *      to dependents.
     */
    public static final String PAYEE_CODE = "00";


    /**
     *      When a service requires an Award Type, use this
     *      value until a later release requires working
     *      with a deceased veteran.
     */
    public static String AWARD_TYPE    = "CPL";


    /**
     *      Used in RbpsRepositoryHydrator to set mailing address to Veteran
     *      and to check veteran indicator, disability rate indicator and so on
     */
    public static final String MAILING_ADDRESS      = "Mailing";
    public static final String INDICATOR_Y          = "Y";


    /**
     *      Claim status value. Use one of these
     *      to check or update Claim status
     */
    public static final String CLAIM_STATUS_PROCESS_STARTED             = "Started";
//    public static final String CLAIM_STATUS_PROCESS_STARTED             = "RBPS";
    public static final String CLAIM_STATUS_PROCESSING_CANCELLED        = "Cancel";
    public static final String CLAIM_STATUS_PROCESSING_COMPLETE         = "Complete";
    public static final String CLAIM_STATUS_PROCESSING_EXPIRED          = "Expired";
    public static final String CLAIM_STATUS_REQUIRES_MANUAL_PROCESSING  = "Manual";
//    public static final String CLAIM_STATUS_REQUIRES_MANUAL_PROCESSING  = "MANUAL_RBPS";
    //public static final String CLAIM_STATUS_RULES_ENGINE_IN_PROGRESS = "RBPS";

    /**
     *      Input of UserInformation Web Service
     */
    public static final String CLAIM_STATUS_READY_FOR_RBPS  = "Ready";
    public static final String USER_IDENTITY_TYPE           = "vnpProcTypeCd";
    public static final String USER_IDENTITY_VALUE          = "DEPCHG";

    /**
     *      Used to extract claim label and proc id from SoapFaultException
     */
    public static final String CLAIM_LABEL  = "claimLabel==";
    public static final String PROC_ID      = "procId==";

    /**
     *      Input of MAP-D Web Service
     */
    public static final String NOTE_TYPE = "CLMDVLNOTE";

    /**
     *      Formatting for output
     */
    public final static String BAR_FORMAT = "----------------------------------------------------------------------------";

    /*
     *      Input of UpdateBenifitClaim web service
     */
    public static final String DISPOSITION          = "M";
    public static final String SECTION_UNIT_NO      = "335";
    public static final String FOLDER_WITH_CLAIM    = "N";
    public static final String BENEFIT_CLAIM_TYPE   = "1";
    public static final String DATE_FORMAT          = "MM/dd/yyyy";
    public static final String PCLR_REASON_CODE		= "65";

    /** 
     * 647228/298341, Completed Claims with Reject Claim Labels (RTC  298341) - ESCP 545 
     * **/     
    public static final String PCLR_REASON_CODE_LTR	= "57";
    
    public static final ToStringStyle       RBPS_TO_STRING_STYLE = new SaiToStringStyle();
    
    //ccr 1789
    public static final String CHECK_PAYMENT="CHECK";
    public static final String EFT_PAYMENT="EFT";
}
