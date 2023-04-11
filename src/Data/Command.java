package Data;

import java.util.Locale;
import java.util.StringTokenizer;

public class Command {
    private String cmd;
    private String[] params;

    public Command(String raw) {
        StringTokenizer tokenizer = new StringTokenizer(raw, ":");
        if(tokenizer.countTokens() < 2)
            return;
        cmd = tokenizer.nextToken().trim();
        String rawParms = tokenizer.nextToken().trim();
        StringTokenizer paramsTokenizer = new StringTokenizer(rawParms, ",");
        params = new String[paramsTokenizer.countTokens()];
        for(int i=0; i < params.length; i++) {
            params[i] = paramsTokenizer.nextToken().trim();
        }
    }

    public boolean isCommand(String input) {
        boolean isCommand = false;
        if(cmd != null)
            isCommand = cmd.toLowerCase().equals(input.toLowerCase());

        return isCommand;
    }

    public int getNumParams() {
        if(params == null)
            return 0;
        return params.length;
    }

    public String getParmByIndex(int index) {
        if (params == null)
            return null;
        if(index >= params.length)
            return null;
        if(index < 0)
            return null;
        return params[index];
    }

    public String getCmd() {
        return cmd;
    }

}
