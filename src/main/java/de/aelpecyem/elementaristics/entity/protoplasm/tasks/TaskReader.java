package de.aelpecyem.elementaristics.entity.protoplasm.tasks;

import java.util.ArrayList;
import java.util.List;

public class TaskReader {
    public static void getTasksFromString(String taskString){
        List<String> taskList = new ArrayList<>();
        //String testString = "pos:1341441;wait:1000;doADo:100:2000;";
        for (String tasks : taskString.split(";")) {
            String[] taskParts = tasks.split(":");
            System.out.println(taskParts[0]);//task
            for (int i = 1; i < taskParts.length; i++) {
                System.out.println("\t" + taskParts[i]); //task options
            }
        }
    }
}
