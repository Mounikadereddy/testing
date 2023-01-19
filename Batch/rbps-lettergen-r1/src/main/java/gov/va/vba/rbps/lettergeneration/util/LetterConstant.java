/*
 * LetterConstant.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.lettergeneration.util;


/**
 *      Constants used to build up the parameters that velocity
 *      uses to process the html templates.
 */
public class LetterConstant {

    public static final String          LETTER_TEMPLATE     = "gov/va/vba/rbps/lettergeneration/template/template.html";

    public static final String          LOGO_PATH_KEY       = "logoPath";
    public static final String          LOGO_PATH           = "gov/va/vba/rbps/lettergeneration/template/image/properVaLogo.PNG";
    public static final String          LETTER_FIELDS_KEY   = "letterFields";
    public static final String          CSS_PATH_KEY        = "cssPath";
    public static final String          CSS_PATH            = "gov/va/vba/rbps/lettergeneration/template/css/common.css";
    public static final String          FONT_PATH_KEY       = "fontPath";
    public static final String          FONT_PATH           = "gov/va/vba/rbps/lettergeneration/template/css/Yesteryear-Regular.ttf";
}
