package me.principit;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class TasksWrapper {

    @XmlElement(name="task", type = Task.class)
    List<Task> tasks = new ArrayList<Task>();

    public TasksWrapper() {
    }

    public TasksWrapper(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
