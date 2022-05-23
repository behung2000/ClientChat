package BLL;

import java.util.StringTokenizer;

public class CheckInput {
    public CheckInput(){

    }

    public boolean checkTextField(String text){
        StringTokenizer stringTokenizer = new StringTokenizer(text, "!@#");
        if(stringTokenizer.countTokens()>1) return false;
        if(text.indexOf("!@#")!=-1) return false;
        return true;
    }
}
