package com.example.semesterexam.manage;

import com.example.semesterexam.tool.Action;
import com.example.semesterexam.tool.MultiAction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class ActionsManagement {
    HashMap<String, Action> actions = new HashMap<>();

    public void addAction(String name, String path) throws IOException {
        Action newAction;
        if (path.endsWith(".png") || path.endsWith(".jpg")) {
            newAction = new Action(path);
        } else {
            newAction = new MultiAction(path);
        }
        actions.put(name, newAction);
    }

    public Action getAction(String actionName) {
         return actions.get(actionName);
    }

    public ActionsManagement() {

    }

    public ActionsManagement(String allActionsFromFile) throws IOException {
        loadAllActionsFrom(allActionsFromFile);
    }

    public void loadAllActionsFrom(String allActionsFromFile) throws IOException {
        File file = new File(allActionsFromFile);
        Scanner scanner = new Scanner(file);
        String pack = "";
        while (scanner.hasNext()) {
            String s= scanner.next();
            if (s.startsWith("@")) {
                pack = s.replace("@","");
                continue;
            }
            else {
                String path = scanner.next();
                addAction(pack + s, path.trim());
            }
        }
    }

    public void loadAllActionsFolder(String folder) throws IOException {
        File file = new File(folder);
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        });
        assert files != null;
        for (File f : files) {
            loadAllActionsFrom(f.toString());
        }
    }

    public void printAllActions() {
        for (String s : actions.keySet()) {
            System.out.println(s);
        }
    }
}
