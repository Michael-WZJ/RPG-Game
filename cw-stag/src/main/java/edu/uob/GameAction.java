package edu.uob;

public abstract class GameAction
{
    private String username;

    abstract public String execute(GameServer server) throws STAGException;

    public void setUsername(String user) {
        this.username = user;
    }

    public String getUsername() {
        return username;
    }

}
