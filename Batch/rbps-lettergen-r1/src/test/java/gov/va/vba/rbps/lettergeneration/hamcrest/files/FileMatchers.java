/*
 * FileMatchers.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.hamcrest.files;


import static org.hamcrest.CoreMatchers.equalTo;

import java.io.File;
import java.io.IOException;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.internal.matchers.TypeSafeMatcher;


/**
 *      This allows us to work with files in the hamcrest matchers in junit.
 *      we can do things such as see if a path is a directory, if it exists,
 *      is the directory empty, etc.
 *
 *      From James Richardson
 *      http://www.time4tea.net/wiki/display/MAIN/Testing+Files+with+Hamcrest
 */
public class FileMatchers {

    public static Matcher<File> isDirectory() {
        return new TypeSafeMatcher<File>() {
            File fileTested;

            @Override
            public boolean matchesSafely(final File item) {
                fileTested = item;
                return item.isDirectory();
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(" that ");
                description.appendValue(fileTested);
                description.appendText("is a directory");
            }
        };
    }


    public static Matcher<File> isDirectoryEmpty() {
        return new TypeSafeMatcher<File>() {

            File dirTested;


            @Override
            public boolean matchesSafely( final File item ) {
                dirTested = item;
                return item.list().length == 0;
            }


            @Override
            public void describeTo( final Description description ) {
                description.appendText( " that directory " );
                description.appendValue( dirTested );
                description.appendText( "is empty" );
            }
        };
    }


    public static Matcher<File> exists() {
        return new TypeSafeMatcher<File>() {
            File fileTested;

            @Override
            public boolean matchesSafely(final File item) {
                fileTested = item;
                return item.exists();
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(" that file ");
                description.appendValue(fileTested);
                description.appendText(" exists");
            }
        };
    }

    public static Matcher<File> isFile() {
        return new TypeSafeMatcher<File>() {
            File fileTested;
            @Override
            public boolean matchesSafely(final File item) {
                fileTested = item;
                return item.isFile();
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(" that ");
                description.appendValue(fileTested);
                description.appendText("is a file");
            }
        };
    }

    public static Matcher<File> readable() {
        return new TypeSafeMatcher<File>() {
            File fileTested;
            @Override
            public boolean matchesSafely(final File item) {
                fileTested = item;
                return item.canRead();
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(" that file ");
                description.appendValue(fileTested);
                description.appendText("is readable");
            }
        };
    }

    public static Matcher<File> writable() {
        return new TypeSafeMatcher<File>() {
            File fileTested;
            @Override
            public boolean matchesSafely(final File item) {
                fileTested = item;
                return item.canWrite();
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(" that file ");
                description.appendValue(fileTested);
                description.appendText("is writable");
            }
        };
    }

    public static Matcher<File> sized(final long size) {
        return sized(equalTo(size));
    }

    public static Matcher<File> sized(final Matcher<Long> size) {
        return new TypeSafeMatcher<File>() {
            File fileTested;
            long length;
            @Override
            public boolean matchesSafely(final File item) {
                fileTested = item;
                length = item.length();
                return size.matches(length);
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(" that file ");
                description.appendValue(fileTested);
                description.appendText(" is sized ");
                description.appendDescriptionOf(size);
                description.appendText(", not " + length);
            }
        };
    }

    public static Matcher<File> named(final Matcher<String> name) {
        return new TypeSafeMatcher<File>() {
            File fileTested;
            @Override
            public boolean matchesSafely(final File item) {
                fileTested = item;
                return name.matches(item.getName());
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText(" that file ");
                description.appendValue(fileTested);
                description.appendText(" is named");
                description.appendDescriptionOf(name);
                description.appendText(" not " );
                description.appendValue(fileTested.getName());
            }
        };
    }

    public static Matcher<File> withCanonicalPath(final Matcher<String> path) {
        return new TypeSafeMatcher<File>() {
            @Override
            public boolean matchesSafely(final File item) {
                try {
                    return path.matches(item.getCanonicalPath());
                }
                catch (IOException e) {
                    return false;
                }
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("with canonical path '");
                description.appendDescriptionOf(path);
                description.appendText("'");
            }
        };
    }

    public static Matcher<File> withAbsolutePath(final Matcher<String> path) {
        return new TypeSafeMatcher<File>() {
//            File fileTested;
            @Override
            public boolean matchesSafely(final File item) {
//                fileTested = item;
                return path.matches(item.getAbsolutePath());
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("with absolute path '");
                description.appendDescriptionOf(path);
                description.appendText("'");
            }
        };
    }
}
