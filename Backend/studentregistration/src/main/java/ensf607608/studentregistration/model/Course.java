package ensf607608.studentregistration.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Course {
    private String name;
    private Date startDate;
    private Date endDate;
    private List<Course> preReqs;
    private List<Section> sectionList;

    public Course() {
    }

    public Course(String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sectionList = new ArrayList<Section>();
        this.preReqs = new ArrayList<Course>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public List<Course> getPreReqs() {
        return preReqs;
    }

    public void setPreReqs(List<Course> preReqs) {
        this.preReqs = preReqs;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        Course c = (Course) o;
        boolean result = (c.getName().compareTo(this.name) == 0);
        return result;
    }

}
