package lexer;


public interface Item {
    public boolean equals(String s);
    public String toString();
    public ItemString toItemString();
    public String getTipo();

}
