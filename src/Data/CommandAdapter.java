package Data;

import Data.Command;
import Data.RECT;
import FileIO.EZFileRead;
import logic.Control;

import java.awt.*;
import java.util.ArrayList;

import static Main.Main.mouseCoordinates;


public class CommandAdapter {
    private ArrayList<Command> commandList;
    private int cursor;
    private enum Commands {
        SHOW_SPRITE("SHOW_SPRITE"),
        TEXT("TEXT"),
        RECT("RECT");

        private String cmd;
        Commands(String cmd) {
            this.cmd = cmd;
        }
        public String getCmd() {
            return cmd;
        }
    }

    public CommandAdapter() {
        cursor = 0;
        commandList = new ArrayList<>();
        String[] rawCommands;
        String scriptFile = "scripts/scripts.txt";
        EZFileRead fileReader = new EZFileRead(scriptFile);
        int numLines = fileReader.getNumLines();
        rawCommands = new String[numLines];
        for(int i=0; i<numLines; i++) {
            String rawCommand = fileReader.getLine(i);
            if(!(rawCommand.equals("")) && rawCommand.charAt(0) != '#')
                commandList.add(new Command(rawCommand));
        }
    }

    public void loopCmdList(Control ctrl) {
        while(cursor != commandList.size()) {
            nextCmd(ctrl);
            cursor ++;
        }
        cursor = 0;
    }
    public void nextCmd(Control ctrl) {
        Command cmd = commandList.get(cursor);
        if(cmd.isCommand(Commands.SHOW_SPRITE.getCmd()) && cmd.getNumParams() == 3) {
            showSprite(cmd, ctrl);
        } else if(cmd.isCommand(Commands.TEXT.getCmd()) && cmd.getNumParams() == 3) {
            text(cmd, ctrl);
        } else if (cmd.isCommand(Commands.RECT.getCmd()) && cmd.getNumParams() == 6) {
            rect(cmd, ctrl);
        }
        cursor = (cursor + 1) % commandList.size();
    }

    private boolean isNextCommand() {
        if(cursor == commandList.size() - 1)
            return false;
        else
            return true;
    }

    private void showSprite(Command cmd, Control ctrl) {
        int x = Integer.parseInt(cmd.getParmByIndex(0));
        int y = Integer.parseInt(cmd.getParmByIndex(1));
        String tag = cmd.getParmByIndex(2);
        ctrl.addSpriteToFrontBuffer(x, y, tag);
    }

    private void text(Command cmd, Control ctrl) {
        int x = Integer.parseInt(cmd.getParmByIndex(0));
        int y = Integer.parseInt(cmd.getParmByIndex(1));
        String display = cmd.getParmByIndex(2);
        ctrl.drawString(x, y, display, Color.WHITE);
    }

    private void rect(Command cmd, Control ctrl) {
        String text;
        int x = Integer.parseInt(cmd.getParmByIndex(0));
        int width = Integer.parseInt(cmd.getParmByIndex(1));
        int y = Integer.parseInt(cmd.getParmByIndex(2));
        int height = Integer.parseInt(cmd.getParmByIndex(3));
        String tag = cmd.getParmByIndex(4);
        String hoverText = cmd.getParmByIndex(5);

        RECT rect = new RECT(x, x + width, y, y + height, tag, hoverText);
        if(rect.isCollision((int)mouseCoordinates.getX(), (int)mouseCoordinates.getY())) {
            text = rect.getHoverLabel();
        } else {
            text = "";
        }

        ctrl.drawString(100, 100, text, Color.GREEN);
    }
}
