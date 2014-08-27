package edu.atlas.earthquake.out.format.node;

import edu.atlas.earthquake.entity.Earthquake;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StringOutNode implements OutNode{

    private final String outString;

    @Override
    public String getOut(Earthquake earthquake) {
        return outString;
    }
}
