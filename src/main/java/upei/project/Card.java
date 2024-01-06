package upei.project;

public abstract class Card {
    private String name;
    private Action action;
    private int parameter;

    public Card(String name, Action action, int parameter) {
        this.name = name;
        this.action = action;
        this.parameter = parameter;
    }

    public String getName() {
        return name;
    }

    public Action getAction() {
        return action;
    }

    public int getParameter() {
        return parameter;
    }
}
