package ounana.projetVideo;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

public class SousTitrageVideoIterator implements Iterator<String> {
    private BufferedReader br;
    private String line;

        public SousTitrageVideoIterator( BufferedReader aBR ) {
            (br = aBR).getClass();
            advance();
        }

        public boolean hasNext() {
            return line != null;
        }

        public String next() {
            String retval = line;
            advance();
            return retval;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove not supported on BufferedReader iteration.");
        }

        private void advance() {
            try {
                line = br.readLine();
            }
            catch (IOException e) { 
                System.out.println("No such line");
            }
            
        }
}

