/*
 * Education.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.coreframework.xom;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;



public class Education implements Serializable {

    private static final long serialVersionUID = -1609242040801710487L;

    private String                 courseName;
    private String                 subjectName;
    private EducationType          educationType;
    private EducationLevelType     educationLevelType;
    private School                 school;
    private Date                   officialCourseStartDate;
    private Date                   courseStudentStartDate; // This should be the begin date for last term
    private Date                   expectedGraduationDate;
    private Date                   courseEndDate;
    private int                    sessionsPerWeek;
    private int                    hoursPerWeek;


    public Date getOfficialCourseStartDate() {
        return officialCourseStartDate;
    }

    public void setOfficialCourseStartDate(final Date officialCourseStartDate) {
        this.officialCourseStartDate = officialCourseStartDate;
    }

    public Date getExpectedGraduationDate() {
        return expectedGraduationDate;
    }

    public void setExpectedGraduationDate(final Date expectedGraduationDate) {
        this.expectedGraduationDate = expectedGraduationDate;
    }

    public Date getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(final Date courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(final String courseName) {
        this.courseName = courseName;
    }

    public EducationType getEducationType() {
        return educationType;
    }

    public void setEducationType(final EducationType educationType) {
        this.educationType = educationType;
    }

    public EducationLevelType getEducationLevelType() {
        return educationLevelType;
    }

    public void setEducationLevelType(final EducationLevelType educationLevelType) {
        this.educationLevelType = educationLevelType;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(final School school) {
        this.school = school;
    }

    public int getSessionsPerWeek() {
        return sessionsPerWeek;
    }

    public void setSessionsPerWeek(final int sessionsPerWeek) {
        this.sessionsPerWeek = sessionsPerWeek;
    }

    public int getHoursPerWeek() {
        return hoursPerWeek;
    }

    public void setHoursPerWeek(final int hoursPerWeek) {
        this.hoursPerWeek = hoursPerWeek;
    }

    public void setCourseStudentStartDate(final Date courseStudentStartDate) {
        this.courseStudentStartDate = courseStudentStartDate;
    }

    public Date getCourseStudentStartDate() {
        return courseStudentStartDate;
    }

    public void setSubjectName(final String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("Course Name",                  courseName)
                .append("Subject Name",                 subjectName)
                .append("Education Type",               educationType)
                .append("Education Level Type",         educationLevelType)
                .append("School",                       school)
                .append("Official Course Start Date",   officialCourseStartDate)
                .append("Course Student Start Date",    courseStudentStartDate)
                .append("Graduation Date",              expectedGraduationDate)
                .append("Course End Date",              courseEndDate)
                .append("Hours per Week",               hoursPerWeek)
                .append("Sessions per Week",            sessionsPerWeek)
                .toString();
    }
}
