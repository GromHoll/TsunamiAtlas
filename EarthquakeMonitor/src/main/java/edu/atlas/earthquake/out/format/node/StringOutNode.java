package edu.atlas.earthquake.out.format.node;

import edu.atlas.earthquake.entity.Earthquake;

public class StringOutNode implements OutNode{

    private final String outString;

    public StringOutNode(String outString) {
        this.outString = outString;
    }

    @Override
    public String getOut(Earthquake earthquake) {
        return outString;
    }
}
