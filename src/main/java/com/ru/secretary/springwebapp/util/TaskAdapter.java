package com.ru.secretary.springwebapp.util;

import com.ru.secretary.springwebapp.domain.Task;

import java.util.Date;

public class TaskAdapter extends Task {
    private boolean allDay;
    private String description;
    private boolean disabled = false;
    private Date endDate;
    private Date startDate;
    private String reccurenceException;
    private String reccurenceRule;
    private String text;
    private boolean visible = true;

}
