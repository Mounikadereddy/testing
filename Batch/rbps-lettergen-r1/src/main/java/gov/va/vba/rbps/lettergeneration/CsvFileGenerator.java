/*
 * CsvFileGenerator.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;

import gov.va.vba.rbps.lettergeneration.batching.util.BatchingUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;


/**
 *      Generate the csv contents for the pdf file.
 *
 *      This will contain information such as:
 *
 *      <table border="1" style="width: 70%; margin: 10px; border-style: solid; border-width: 1px;">
 *          <tr>
 *              <th>Field Name</th>
 *              <th>Width</th>
 *              <th>Desc/Example</th>
 *          </tr>
 *
 *          <tr>
 *              <td>file name</td>
 *              <td>18</td>
 *              <td>16155027_20110714.pdf</td>
 *          </tr>
 *
 *          <tr>
 *              <td>claim number</td>
 *              <td>8 or 9 (claimId or SSN)</td>
 *              <td>16155027</td>
 *          </tr>
 *
 *          <tr>
 *              <td>doc type</td>
 *              <td>3</td>
 *              <td>353</td>
 *          </tr>
 *
 *          <tr>
 *              <td>document subject</td>
 *              <td>50</td>
 *              <td>DD214:Attached to VONAPP 2 form<br/>Marriage Certificate: Attached to VONAPP2 form</td>
 *          </tr>
 *
 *          <tr>
 *              <td>source comment</td>
 *              <td>50</td>
 *              <td>RBPS</td>
 *          </tr>
 *
 *          <tr>
 *              <td>receipt date</td>
 *              <td>10</td>
 *              <td>11/30/2011</td>
 *          </tr>
 *
 *          <tr>
 *              <td>SOJ (station of jurisdiction)</td>
 *              <td>3</td>
 *              <td>335</td>
 *          </tr>
 *
 *          <tr>
 *              <td>note</td>
 *              <td>80</td>
 *              <td>
 *                  SOJ, veteran name, claimant name<br/>
 *                  335, Robert T. Jones; Emily W. Williams
 *              </td>
 *          </tr>
 *      </table>
 *
 *      This is a sample sent to us from Katy McBurney on the AWARDS team:
 *
 *      <ul style="list-style-type: none;">
 *          <li>"FileName"~"ClaimNumber"~"SourceComment"~"DocumentDate"~"InsertedBy"</li>
 *          <li>"FileName1.pdf"~"01234567"~"Rules Based Processing System"~"04/05/11"~"VSCFNAME"</li>
 *          <li>"FileName2.pdf"~"12345678"~"Rules Based Processing System"~"04/15/11"~"VSCSNAME"</li>
 *          <li>"FileName3.pdf"~"23456789"~"Rules Based Processing System"~"04/25/11"~"VSCTNAME"</li>
 *          <li>"FileName4.pdf"~"012345678"~"Rules Based Processing System"~"05/01/11"~"VSCFNAME"</li>
 *          <li>"FileName5.pdf"~"123456789"~"Rules Based Processing System"~"05/02/11"~"VSCFNAME"</li>
 *          <li>"FileName6.pdf"~"234567890"~""~"05/03/11"~"VSCSNAME"</li>
 *      </ul>
 *
 *      We are using:
 *
 *      <ul>
 *          <li>file name</li>
 *          <li>claim number</li>
 *          <li>document type</li>
 *          <li>source comment</li>
 *          <li>receipt date</li>
 *      </ul>
 */
public class CsvFileGenerator {


    public static final String VIRTUAL_VA_DOC_TYPE_NOTIFICATION_LETTER = "184";
    public static final String VIRTUAL_VA_DOC_TYPE_AWARD_PRINT         = "508";


    private static final String RULES_BASED_PROCESSING_SYSTEM_INSERTED_BY = "Rules Based Processing System";


    private List<String>    fieldsList = new ArrayList<String>();



    /**
     *      Given the pdf file name and the parameters to the velocity
     *      template, generate the csv file which is used to tell
     *      Virtual VA how to handle the pdf file.
     *
     *      @param letterFields
     *      @param pdfFileName
     *      @param csvFileName
     */
    public void generateCsvFile( final String           fileNumber,
                                 final String           pdfFileName,
                                 final String           csvFileName,
                                 final String           docType ) {

        try {

            String csvLine = buildCsvLine( fileNumber, pdfFileName, docType );

//            System.out.println( String.format( "csv line for %s:\n\t%s", csvFileName, csvLine ) );

            FileUtils.writeStringToFile( new File( csvFileName ), csvLine );
        }
        catch ( Throwable ex ) {

            throw new UnableToCreateCsvFileException( pdfFileName, ex );
        }
    }




    /**
     *      Construct the one line in the csv file that tells Virtual VA
     *      how to handle the incoming pdf file.
     *
     *      @param letterFields
     *      @param pdfFileName
     *      @return
     */
    public String buildCsvLine( final String    fileNumber,
                                final String    pdfFileName,
                                final String    docType ) {

        addToFieldsList( FilenameUtils.getName( pdfFileName ) );
        addToFieldsList( "" + fileNumber );
        addToFieldsList( docType );
        addToFieldsList( getFormattedDate() );
        addToFieldsList( RULES_BASED_PROCESSING_SYSTEM_INSERTED_BY );

        String  csvLine = new BatchingUtils().join(  fieldsList, "~" ) + "\n";

        return csvLine;
    }


    /**
     *      Add a value to the one line in the csv file, but make sure
     *      it's quoted.
     *
     *      @param value to be added to the csv line
     */
    private void addToFieldsList( final String    value ) {

        fieldsList.add( wrapQuotes( value ) );
    }


    /**
     *      Return the date formatted in a way that's suitable for
     *      the Virtual VA system.  "MM/dd/yyyy".
     *
     *      @return the formatted date
     */
    public String getFormattedDate() {

        return DateFormatUtils.format( new Date(), "MM/dd/yyyy" );
    }


    /**
     *      Make sure that a value that goes into a field is wrapped
     *      in quotes.
     *
     *      @param field - the value of the field that is to be wrapped in quotes
     *      @return the value now wrapped in quotes
     */
    public String wrapQuotes( final String    field ) {

        return String.format( "\"%s\"", field );
    }
}
