/*
 * LogUtils
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.util;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.CorporateDependent;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.Veteran;

import java.util.ArrayList;
import java.util.List;


/**
 *      Wanted to abstract out that log method.  A base
 *      class for those who can use it.
 */
public class LogUtils {

    private static Logger logger = Logger.getLogger(LogUtils.class);


    public static final int         PLUS_ONE                = 4;

    private static boolean          logToStdout             = true;
    
    // no longer needed
    //private static boolean          accumulateLogMessages   = false;
    //private static RbpsRepository   repository              = null;
    //protected CommonUtils           utils                   = new CommonUtils();
    
    private static List<String>     logMessages;
    private static boolean          globalLogit             = true;
    private static int              stackLevel              = 0;
    protected boolean               logit                   = true;
    private Logger                  otherLogger             = null;


    public LogUtils( final Logger       logger,
                     final boolean      logit ) {

        otherLogger     = logger;
        this.logit      = logit;
    }


    //
    //      Just in case something gets screwed up.
    //      don't want to infinite loop (or just large loop)
    //      when indenting the enter/exit.
    //
    public static void newClaim() {

        stackLevel = 0;
    }


    public void debugEnter( RbpsRepository   repository) {

        debugEnter( CommonUtils.getStackLocation( 2 ), repository );
    }


    public void debugEnter( final String    stackLevelLabel,  RbpsRepository   repository ) {

        debug( getLogger(), indentByStackLevel() + "----> Entering "+ stackLevelLabel + "...", repository );

        incrementStackLevel( 1 );
    }


    public void debugExit( RbpsRepository   repository) {

        debugExit( CommonUtils.getStackLocation( 2 ), repository );
    }


    public void debugExit( final String    stackLevelLabel,  RbpsRepository   repository ) {

        incrementStackLevel( -1 );

        debug( getLogger(), indentByStackLevel() + "<---- Exiting "+ stackLevelLabel + "...", repository );
    }


    private static final String indentByStackLevel() {

        String  indent = "";

        for ( int ii = 0; ii < stackLevel; ii++ ) {

            indent += "  ";
        }

        return indent;
    }


    private void incrementStackLevel( final int    by ) {

        stackLevel += by;
        stackLevel = Math.min( stackLevel, 15 );
    }


    public void log( final String msg,  RbpsRepository   repository ) {

        log( msg, PLUS_ONE, repository );
    }
    
    public void log( final String       msg,
                     final Throwable    ex,  
                     RbpsRepository   repository ) {

        log( msg, ex, PLUS_ONE, repository );
    }

    public void debug( final String msg,  RbpsRepository   repository ) {

        debug( msg, PLUS_ONE, repository );
    }


    public void logParent( final String msg, RbpsRepository   repository ) {

        log( msg, PLUS_ONE + 1,  repository );
    }


    public void log( final String   msg,
                     final int      levelsUp,
                     RbpsRepository   repository) {

        if ( ! shouldLog() ) {

            return;
        }

        log( getLogger(), constructLoggingMessage( msg, levelsUp ), repository );
    }


    public void log( final String       msg,
                     final Throwable    ex,
                     final int          levelsUp,
                     RbpsRepository   repository) {

        if ( ! shouldLog() ) {

            return;
        }

        log( getLogger(), constructLoggingMessage( msg, levelsUp ), ex, repository );
    }


    public void debug( final String   msg,
                       final int      levelsUp,
                       RbpsRepository   repository) {

        if ( ! shouldLog() ) {

            return;
        }

        debug( getLogger(), constructLoggingMessage( msg, levelsUp ), repository );
    }


    public static final String constructLoggingMessage( final String msg, final int levelsUp ) {

        return "\n" + RbpsConstants.BAR_FORMAT + "\n        " + CommonUtils.getStackLocation( levelsUp ) + ":\n\t    " + msg;
    }


    private Logger getLogger() {

        if ( otherLogger != null ) {

            return otherLogger;
        }

        return logger;
    }


    public void log( final Logger       logger,
                     final String       msg, RbpsRepository   repository  ) {
    	
    	String message	=  "[ currentThread: " + Thread.currentThread().getName() + "] " + msg;

        if ( ! shouldLog() ) {

            return;
        }

        if ( logger != null ) {

            logger.debug( message );
        }

//        if ( logToStdout ) {
//            System.out.println( message );
//        }
//
//        if ( accumulateLogMessages ) {
//
//            getLogMessages().add(  msg  );
//        }

        addToRepository( message, repository );
    }


    public void log( final Logger       logger,
                     final String       msg,
                     final Throwable    ex,
                     RbpsRepository   repository ) {
    	
    	String message	=  "[ currentThread: " + Thread.currentThread().getName() + "] " + msg;

        if ( ! shouldLog() ) {

            return;
        }

        if ( logger != null ) {

            logger.debug( message, ex );
        }

//        if ( logToStdout ) {
//            System.out.println( message );
//            ex.printStackTrace();
//        }
//
//        if ( accumulateLogMessages ) {
//
//            getLogMessages().add(  msg  );
//        }

        addToRepository( message, repository );
    }


    private void debug( final Logger       logger,
                        final String       msg,
                        RbpsRepository   repository ) {

    	String message	=  "[ currentThread: " + Thread.currentThread().getName() + "] " + msg;
    	
        if ( ! shouldLog() ) {

            return;
        }

        if ( logger != null ) {

            logger.debug( message );
        }

//        if ( logToStdout && logger.isDebugEnabled() ) {
//            System.out.println( message );
//        }
//
//        if ( accumulateLogMessages ) {
//
//            getLogMessages().add(  msg  );
//        }

        addToRepository( message, repository );
    }


    private static List<String> getLogMessages() {

        if ( logMessages == null ) {

            logMessages = new ArrayList<String>();
        }

        return logMessages;
    }


    private static final void addToRepository( final String    msg, RbpsRepository   repository  ) {

        if ( repository == null ) {

            return;
        }

        repository.addJournalStr( msg + "\n" );
    }


    public boolean shouldLog() {

        return logit && globalLogit;
    }


//  public void logCorporateIdsForKids( final List<CorporateDependentId> children ) {
//
//      System.out.println( "num corporate kids/spouse: " + children.size() );
//
//      int index = 0;
//      for ( CorporateDependentId    child : children ) {
//
//          logCorporateIdInfo( index, child );
//          index++;
//      }
//  }
//
//
//  private void logCorporateIdInfo( final int index, final CorporateDependentId child ) {
//
//      log( logger, "" + index + " child/spouse: " + child.toString().replace( ",", ",\n\t") );
//  }


  public void logCorporateChildren( final RbpsRepository    repository ) {


      if ( ! shouldLog() ) {

          return;
      }

      int counter = 0;

      String        output = "\n";
      output += String.format( "number of corporate children: %d for >%s, %s<\n",
                               repository.getChildren().size(),
                               repository.getVeteran().getLastName(),
                               repository.getVeteran().getFirstName() );

      for ( CorporateDependent dependent : repository.getChildren() ) {

          String msg = String.format( "corporate child (%d) >%s, %s    pid %d/ssn %s<\n",
                                      counter,
                                      dependent.getLastName(),
                                      dependent.getFirstName(),
                                      dependent.getParticipantId(),
                                      dependent.getSocialSecurityNumber() );

          output += msg;
          counter++;
      }

      if ( repository.getSpouse() != null ) {

          output += String.format( "corporate spouse: >%s, %s<\n",
                                   repository.getSpouse().getLastName(),
                                   repository.getSpouse().getFirstName() );
      }
      else {

          output += "corporate spouse: none";
      }

      log( output, PLUS_ONE, repository );
  }


  public void logKids( final RbpsRepository   repository ) {

      if ( ! shouldLog() ) {

          return;
      }

      Veteran   veteran = repository.getVeteran();
      String    output  = "";

      output += "\nfile number:        " + veteran.getFileNumber();
      output += "\nparticpant id:      " + veteran.getCorpParticipantId();
      output += "\nnumber of XOM kids: " + veteran.getChildren().size();
      output += "\nhas spouse:         " + (veteran.getCurrentMarriage() != null);

      int index = 0;
      for ( Child child : repository.getVeteran().getChildren() ) {

          output += String.format( "\nchild(%d) %s, %s",
                                   index++,
                                   child.getLastName(),
                                   child.getFirstName() );
      }

      if ( veteran.getCurrentMarriage() != null ) {

          Spouse spouse = veteran.getCurrentMarriage().getMarriedTo();
          output += String.format( "\nspouse: %s, %s",
                                   spouse.getLastName(),
                                   spouse.getFirstName() );
      }

      log( output, PLUS_ONE, repository );
  }






    public static void setGlobalLogit( final boolean      logit ) {

        globalLogit = logit;
    }
    
    /* No longer needed
    public static void setRepository( final RbpsRepository    repo ) {
        repository = repo;
    }
    */
    
    public void setLogit( final boolean logit ) {

        this.logit = logit;
    }
}
