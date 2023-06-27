abstract class Parser {
    private String input;
    private int pos;
    int parse(String input)throws Exception{
        this.input = input + '#';
        pos = 0;
        int res = Start();
        if(!match('#'))throw new Exception("Falsche Syntax bei #");
        return res;
    }

    abstract int Start() throws Exception;

    char next(){
        return input.charAt(pos);
    }
    boolean match(char c){
        if(next() == c){
            pos++;
            return true;
        }
        else return false;
    }
}
