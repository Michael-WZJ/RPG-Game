package edu.uob;

public abstract class GameEntity
{
    String name;
    String description;

    public GameEntity(String name, String description)
    {
        this.name = name;
        this.description = description;
    }

    // wwzzjj
    public abstract void accept(Vistor v);

    public abstract String getType();

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description.toLowerCase();
    }
}
