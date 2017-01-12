package me.principit;

import com.sun.jmx.remote.internal.Unmarshal;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class TaskService {
    public static String toXmlString(List<Task> tasks) {
        try {
            Marshaller m = JAXBContext.newInstance(Task.class, TasksWrapper.class).createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter sw = new StringWriter();
            m.marshal(new TasksWrapper(tasks), sw);
            return sw.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static List<Task> fromXmlString(String string) {
        try {
            Unmarshaller u = JAXBContext.newInstance(Task.class, TasksWrapper.class).createUnmarshaller();
            StringReader sr = new StringReader(string);
            return ((TasksWrapper) u.unmarshal(sr)).getTasks();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}
