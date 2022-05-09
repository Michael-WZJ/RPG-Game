package edu.uob;

public abstract class GameAction
{
    private String username;

    abstract public String execute(GameServer server);


    public void setUsername(String user) {
        this.username = user;
    }

    public String getUsername() {
        return username;
    }

}
