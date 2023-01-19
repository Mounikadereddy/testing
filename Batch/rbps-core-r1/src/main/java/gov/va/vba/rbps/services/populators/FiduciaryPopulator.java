package gov.va.vba.rbps.services.populators;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindFiduciaryResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.ShrinqfPersonOrg;

public class FiduciaryPopulator {


       private static Logger logger = Logger.getLogger(FiduciaryPopulator.class);



        public void populateFiduciary(final FindFiduciaryResponse findFiduciaryResponse, RbpsRepository repository) {

        	repository.setIsFiduciary(false);
        	repository.setFiduciaryName( null );
        	repository.setFiduciaryPrepositionalPhrase( null );
        	repository.setFiduciaryAttentionText( null );
            
            CommonUtils.log( logger, "FiduciaryPopulator: response: >\n" + CommonUtils.stringBuilder( findFiduciaryResponse.getReturn() ) + "<" );
            ShrinqfPersonOrg fiduciaryInfo = findFiduciaryResponse.getReturn();

            if (findFiduciaryResponse == null || findFiduciaryResponse.getReturn() == null ) {

                CommonUtils.log( logger, "FiduciaryPopulator: FindFiduciaryResponse is null." );
                return;
            }

            recordFiduciaryInfo( fiduciaryInfo, repository );
        }


        public void recordFiduciaryInfo( final ShrinqfPersonOrg fiduciaryInfo, RbpsRepository repository ) {

        	repository.setIsFiduciary(true);

            String  personOrgName                   =   fiduciaryInfo.getPersonOrgName();
            String  fiduciaryprepositionalPhrase    =   fiduciaryInfo.getPrepositionalPhraseName();
            String  personOrgAttentionText          =   fiduciaryInfo.getPersonOrgAttentionText();
            
            CommonUtils.log( logger, "fiduciaryInfo personOrgName: " + fiduciaryInfo.getPersonOrgName() );
            CommonUtils.log( logger, "fiduciaryInfo fiduciaryprepositionalPhrase: " + fiduciaryInfo.getPrepositionalPhraseName() );
            CommonUtils.log( logger, "fiduciaryInfo personOrgAttentionText: " + fiduciaryInfo.getPersonOrgAttentionText() );
            
            personOrgName                           =   fixName( personOrgName );
            fiduciaryprepositionalPhrase            =   fixName( fiduciaryprepositionalPhrase );
            
            repository.setFiduciaryName( CommonUtils.removeExtraSpacesFromString( personOrgName ) );
            repository.setFiduciaryPrepositionalPhrase( fiduciaryprepositionalPhrase );
            repository.setFiduciaryAttentionText( personOrgAttentionText );
        }


        public String fixName( String personOrgName ) {

            personOrgName = personOrgName.replaceAll( "[0-9\\-]", "" );

            return personOrgName.trim();
        }
}
