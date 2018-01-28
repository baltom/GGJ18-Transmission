package com.transmission.trans_mission.gui.manager;

import com.transmission.trans_mission.gui.containers.Dialog;
import com.transmission.trans_mission.gui.containers.TileSet;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DialogManager {

    private Canvas parent;
    private Point2D dialogPos;
    private Point2D dialogSize;
    private TileSet tileSet;

    private Dialog currentDialog;
    private boolean shouldDraw;
    private boolean hasRequested;
    private Random random;

    private HashMap<String, Set<String>> itemDialog = new HashMap<>();

    public DialogManager(Canvas parent, TileSet tileSet) {
        initDialogSize(parent);
        this.tileSet = tileSet;
        this.random = new Random();
        initDialog();
    }

    public void initDialogSize(Canvas parent) {
        this.parent = parent;
        dialogSize = new Point2D(parent.getWidth(), 150);
        dialogPos = new Point2D(0, parent.getHeight() - dialogSize.getY());
    }

    private void initDialog() {
        addDialogToItem("TOILET_DOOR_MALE", "Wow! that place smells like shit!");
        addDialogToItem("TOILET_DOOR_MALE", "Do not want to go in there. Smells terrible");
        addDialogToItem("TOILET_DOOR_MALE", "WTF!?! how can something smell so bad!");
        addDialogToItem("TRAIN_DOOR", "I can't leave now! i have to find out what has happened here");
        addDialogToItem("TRAIN_DOOR", "What a beautiful door!");
        addDialogToItem("TRAIN_DOOR", "Hmmm... nice door...");
        addDialogToItem("MIRROR", "Wow! who's that handsome man?");
        addDialogToItem("MIRROR", "Bob was right! My hair does look awesome!");
        addDialogToItem("MIRROR", "This orange shirt does suit me really well");
        addDialogToItem("SINK", "Well someone has not clean this! it's full of stains");
        addDialogToItem("SINK", "Hmm, i guess i could wash my hands. Haven't done that in a while");
        addDialogToItem("SINK", "Maybe some nice cold water would be good?");
        addDialogToItem("KNIFE", "Wow! i got a knife just like that!");
        addDialogToItem("KNIFE", "Wonder where i put my knife, I knew i did bring one");
        addDialogToItem("BLOOD", "Shit! that's a lot of blood");
        addDialogToItem("BLOOD", "Well i guess someone has to clean their dress!");
        addDialogToItem("BLOOD", "I wonder what that other black thing is");
    }

    private void addDialogToItem(String event, String dialog) {
        Set<String> dialogs = itemDialog.getOrDefault(event, new HashSet<>());
        dialogs.add(dialog);
        itemDialog.put(event, dialogs);
    }


    public Dialog getDialog() {
        hasRequested = true;
        return currentDialog;
    }

    public boolean shouldDrawDialog() {
        return shouldDraw;
    }

    public boolean isWithinDialogBounds(double x, double y) {
        return dialogPos.getX() < x && dialogPos.getY() < y &&
                dialogPos.getX() + dialogSize.getX() > x &&
                dialogPos.getY() + dialogSize.getY() > y;
    }

    public void clickDialog(MouseEvent mouseEvent) {
        if (hasRequested) {
            hasRequested = false;
            shouldDraw = false;
        }
    }

    public void displayDialog(int scene) {
        shouldDraw = true;
        currentDialog = new Dialog(tileSet.getTile(0), Color.BLACK, Color.RED, dialogSize, dialogPos, "Is that a hair?");
    }

    public void removeDialog() {
        shouldDraw = false;
        hasRequested = false;
        currentDialog = null;
    }

    public void event(String event) {
        shouldDraw = true;
        createDialog(event);
    }

    private void createDialog(String event) {
        switch (event) {
            case "SAM":
            case "ROBIN":
            case "ALEX":
            case "JAMIE":
                handleCharacterDialog(event);
                break;
            default:
                findDialog(event);
                break;
        }
        //currentDialog = new Dialog(tileSet.getTile(0), Color.BLACK, Color.RED, dialogSize, dialogPos, event);
    }

    private void handleCharacterDialog(String event) {

    }

    private void findDialog(String event) {
        Set<String> strings = itemDialog.get(event);
        if (strings != null) {
            int size = strings.size();
            int item = random.nextInt(size); // In real life, the Random object should be rather more shared than this
            int i = 0;
            String dialog = "";
            for(String obj : strings)
            {
                if (i == item) {
                    dialog = obj;
                    break;
                }
                i++;
            }
            currentDialog = new Dialog(tileSet.getTile(0), Color.BLACK, Color.RED, dialogSize, dialogPos, dialog);
        }
    }
}
