package de.aelpecyem.elementaristics.entity.protoplasm.tasks;

import de.aelpecyem.elementaristics.entity.protoplasm.tasks.execs.ProtoplasmGoToTask;
import de.aelpecyem.elementaristics.entity.protoplasm.tasks.execs.ProtoplasmTask;
import de.aelpecyem.elementaristics.entity.protoplasm.tasks.execs.ProtoplasmWaitTask;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProtoplasmTaskInit {
    public static Map<String, ProtoplasmTask> taskMap = new LinkedHashMap<>();

    public static void init(){
        registerTask(new ProtoplasmGoToTask());
        registerTask(new ProtoplasmWaitTask());
    }

    public static ProtoplasmTask getTaskByName(String name){
        return taskMap.get(name);
    }
    public static ProtoplasmTask getTask(String... taskParts){
        ProtoplasmTask task = taskMap.get(taskParts[0]);
        if (task != null) {
            return task.applyAttributes(taskParts);
        }
        return null;
    }

    protected static void registerTask(ProtoplasmTask task){
        taskMap.put(task.getName(), task);
    }

    public static List<ProtoplasmTask> getTasksFromString(String taskString){
        List<ProtoplasmTask> taskList = new ArrayList<>();
        for (String tasks : taskString.split(";")) {
            String[] taskParts = tasks.split(",");
            if (ProtoplasmTaskInit.getTask(taskParts) != null)
            taskList.add(ProtoplasmTaskInit.getTask(taskParts));
        }
        return taskList;
    }
}
