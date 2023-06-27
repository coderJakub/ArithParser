import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

class Rechner extends Parser{
    Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
    
    int Start()throws Exception{
        ht.clear();
        if(!(match('l') && match('e') && match('t')))throw new Exception("let fehlt am Anfang");
        if(!match(' '))throw new Exception("Leerzeichen nach let fehlt");
        V();
        if(!match(' '))throw new Exception("Leerzeichen vor in fehlt");
        if(!(match('i') && match('n')))throw new Exception("in nach Variablendeklaration fehlt");
        if(!match(' '))throw new Exception("Leerzeichen nach in fehlt");
        int res=E();
        return res;
    }
    void V() throws Exception{
        do{
            V_();
        }while (match(','));
    }
    void V_() throws Exception{
        String x=I();
        if(!match('='))throw new Exception("= fehlt bei Variablendeklaration");
        int i= D();
        if((ht.get(x))!=null)throw new Exception("doppelte Deklartion einer Variable");
        ht.put(x,i);
    }
    String I()throws Exception{
        String I="";
        if(next()<'a' || next()>'z')throw new Exception("Falsches Format der Variable");
        I+=next();
        match(next());

        if(next()>='0' && next()<='9')I+=D();
        return I;
    }
    int D()throws Exception{
        int res=0;
        do{
            res+=10*res+N();
        }while(next()>='0' && next()<='9');
        return res;
    }

    int N()throws Exception{
        if(next()<'0' || next()>'9')throw new Exception("Eine Zahl wurde falsch eingegeben");
        int i=Integer.parseInt(next()+"");
        match(next());
        return i; 
    }
    int E()throws Exception{
        int res = T();
        char c= next();
        while(c=='+' || c=='-'){
            match(c);
            if(c=='+')res+=T();
            if(c=='-')res-=T();
            c=next();
        }
        return res;
    }
    int T()throws Exception{
        int res=F();
        char c= next();
        while(c=='*' || c=='/'){
            match(c);
            if(c=='*')res*=F();
            if(c=='/')res/=F();
            c=next();
        }
        return res;
    }
    int F()throws Exception{
        int res;
        if(next()=='('){
            match('(');
            res=E();
            if(!match(')'))throw new Exception("Geschlossene Klammer fehlt");
            return res;
        }
        else if(next()>='a' && next()<='z'){
            String i=I();
            Integer j = ht.get(i);
            if(j==null)throw new Exception("Es wurde eine unbekannte Variable verwendet");
            res = j;
            return res;
        }
        else if(next()>='0' && next()<='9'){
            return D();
        }
        else throw new Exception("unbekanntes Zeichen in Rechnung");
    }
    public static void main(String[] args) throws IOException{
        Rechner parser = new Rechner();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            System.out.print("Ausdruck: ");
            String input = in.readLine();
            if(input.equals("q"))break;
            try{
                System.out.println(parser.parse(input));
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}