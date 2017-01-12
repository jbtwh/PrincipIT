package me.principit;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "task")
@XmlAccessorType(XmlAccessType.FIELD)
public class Task {
    @XmlElement(name = "name", required = true, nillable = false)
    private String name;
    @XmlTransient
    private String pid;
    @XmlElement(name = "memory", required = true, nillable = false)
    private Long memory;

    public Task() {
    }

    public Task(String name, String pid, Long memory) {
        this.name = name;
        this.pid = pid;
        this.memory = memory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Long getMemory() {
        return memory;
    }

    public void setMemory(Long memory) {
        this.memory = memory;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", memory=" + memory +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (!name.equals(task.name)) return false;
        return memory.equals(task.memory);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + memory.hashCode();
        return result;
    }
}