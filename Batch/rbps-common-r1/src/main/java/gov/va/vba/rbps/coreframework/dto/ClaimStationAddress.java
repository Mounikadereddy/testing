/*
 * ClaimStationAddress.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.dto;


import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;


/*
 *      This class is used to hold the values to populate SojAddress.
 *      The claimLocationId is set by the repository populator.
 */
public class ClaimStationAddress implements Serializable{

    private static final long serialVersionUID = 479129020585351038L;


    private static final String                         SPACE           = " ";


    //      station location id - like 1943
    private long        claimLocationId;

    //      Regional Office id - like 335
    private String      stationId;
    private String      name;
    private String      addressLine1;
    private String      addressLine2;
    private String      addressLine3;
    private String      city;
    private String      state;
    private String      zipCode;
    private String      zipPlus4;
    private String      zipSecondSuffix;
    private String      serviceManagerSignature;
    private String      serviceManagerTitle;






    public void calculateAddressLine() {

        assert ! StringUtils.isBlank( name );

        addressLine1 = ( getAddressLine1().contains( name ) ) ? getAddressLine1() : ( name + SPACE + getAddressLine1() );
    }
    public String getAddressLine1() {

        return addressLine1;
    }
    public void setAddressLine1(final String addressLine1) {

        this.addressLine1 = addressLine1;
    }



    public String getAddressLine2() {

        return addressLine2;
    }
    public void setAddressLine2(final String addressLine2) {

        this.addressLine2 = addressLine2;
    }



    public String getAddressLine3() {

        return addressLine3;
    }
    public void setAddressLine3(final String addressLine3) {

        this.addressLine3 = addressLine3;
    }



    public String getCity() {
        return city;
    }
    public void setCity(final String city) {

        this.city = city;
    }



    public String getState() {

        return state;
    }
    public void setState(final String state) {

        this.state = state;
    }



    public String getZipCode() {

        return zipCode;
    }
    public void setZipCode(final String zipCode) {

        this.zipCode = zipCode;
    }



    public String getZipPlus4() {

        return zipPlus4;
    }
    public void setZipPlus4(final String zipPlus4) {

        this.zipPlus4 = zipPlus4;
    }



    public String getZipSecondSuffix() {

        return zipSecondSuffix;
    }
    public void setZipSecondSuffix(final String zipSecondSuffix) {

        this.zipSecondSuffix = zipSecondSuffix;
    }





    public String getZeroFilledClaimStationId() {

        String claimLocationId = String.format( "%03d", getClaimLocationId() );

        return claimLocationId;
    }
    public long getClaimLocationId() {

        return claimLocationId;
    }
    public void setClaimLocationId(final long claimLocationId) {

        this.claimLocationId = claimLocationId;
    }



    public boolean hasSignature() {

        return ! StringUtils.isBlank( getServiceManagerSignature() );
    }
    public String getServiceManagerSignature() {

        return serviceManagerSignature;
    }
    public void setServiceManagerSignature( final String serviceManagerSignature ) {

        this.serviceManagerSignature = serviceManagerSignature;
    }
    public String getServiceManagerTitle() {

        return serviceManagerTitle;
    }
    public void setServiceManagerTitle( final String serviceManagerTitle ) {

        this.serviceManagerTitle = serviceManagerTitle;
    }



    public String getName() {

        return name;
    }
    public void setName( final String name ) {

        this.name = name;
    }



    public String getStationId() {

        return stationId;
    }
    public void setStationId( final String stationId ) {

        this.stationId = stationId;
    }


    @Override
    public String toString() {

//        return new IndentedToStringUtils().toString( this, 1 );

        return new ToStringBuilder( this )
            .append( "claimLocationId",           claimLocationId )
            .append( "stationId",                 stationId )
            .append( "name",                      name )
            .append( "addressLine1",              addressLine1 )
            .append( "addressLine2",              addressLine2 )
            .append( "addressLine3",              addressLine3 )
            .append( "city",                      city )
            .append( "state",                     state )
            .append( "zipCode",                   zipCode )
            .append( "zipPlus4",                  zipPlus4 )
            .append( "zipSecondSuffix",           zipSecondSuffix )
            .append( "serviceManagerSignature",   serviceManagerSignature )
            .toString();
    }
}
